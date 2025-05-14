#!/bin/bash

BASE_DIR="$(dirname "$(realpath "$0")")/../k8s"

SERVICE_DIRS=("../rabbitmq" "merchants" "notifications" "search" "transactions" "users" "../observability/grafana" "../observability/prometheus" "../observability/loki" "../observability/tempo")

# Apply ConfigMaps first (if any) before StatefulSets or Secrets
for SERVICE in "${SERVICE_DIRS[@]}"; do
    echo "Applying ConfigMaps for $SERVICE..."
    for FILE in $BASE_DIR/services/$SERVICE/*configmap*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply Secrets after ConfigMaps
for SERVICE in "${SERVICE_DIRS[@]}"; do
    echo "Applying Secrets for $SERVICE..."
    for FILE in $BASE_DIR/services/$SERVICE/*secret*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply StatefulSets after Secrets
for SERVICE in "${SERVICE_DIRS[@]}"; do
    echo "Applying StatefulSets for $SERVICE..."
    for FILE in $BASE_DIR/services/$SERVICE/*statefulset*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply Deployments after StatefulSets
for SERVICE in "${SERVICE_DIRS[@]}"; do
    echo "Applying Deployments for $SERVICE..."
    for FILE in $BASE_DIR/services/$SERVICE/*deployment*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply Services after Deployments
for SERVICE in "${SERVICE_DIRS[@]}"; do
    echo "Applying Services for $SERVICE..."
    for FILE in $BASE_DIR/services/$SERVICE/*service*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply the apigateway manifests
echo "Applying Kubernetes manifests for apigateway..."
for FILE in $BASE_DIR/apigateway/*.yaml; do
    if [[ -f "$FILE" ]]; then
        echo "Applying $FILE..."
        kubectl apply -f "$FILE"
    fi
done

# Apply the Stripe Listener Job manifest
echo "Applying the Stripe Listener Job manifest..."
kubectl apply -f "$BASE_DIR/stripe-listener-job.yaml"

# Wait for the Job to complete
echo "Waiting for the Stripe listener job to complete..."
kubectl wait --for=condition=complete --timeout=5m job/stripe-listener-job

# Get the pod name associated with the Stripe listener job
POD_NAME=$(kubectl get pods --selector=job-name=stripe-listener-job -o=jsonpath='{.items[0].metadata.name}')

# Check if the pod name is found
if [[ -z "$POD_NAME" ]]; then
  echo "Error: Pod associated with the job not found!"
  exit 1
fi

# Attempt to capture the webhook secret from the pod logs
echo "Fetching logs from pod: $POD_NAME"
WEBHOOK_SECRET=""
for attempt in {1..5}; do
    # Grep the webhook secret from the logs based on the exact output pattern
    WEBHOOK_SECRET=$(kubectl logs "$POD_NAME" | grep -o 'Your webhook signing secret is whsec_[^ ]*' | awk '{print $NF}' | head -n 1)
    if [[ -n "$WEBHOOK_SECRET" ]]; then
        echo "Webhook secret found: $WEBHOOK_SECRET"
        break
    else
        echo "Attempt $attempt: Webhook secret not found, retrying in 5 seconds..."
        sleep 5
    fi
done

# Check if the webhook secret was found after retries
if [ -z "$WEBHOOK_SECRET" ]; then
  echo "Error: Webhook secret not found in logs!"
  exit 1
fi

# Update the Kubernetes Secret with the new webhook secret
echo "Updating the Kubernetes secret with the new webhook secret..."
kubectl patch secret stripe-secret \
  -p "{\"data\":{\"STRIPE_WEBHOOK_SECRET\":\"$(echo -n "$WEBHOOK_SECRET" | base64)\"}}"

# Check the status of all resources
echo "Checking the status of the resources..."
kubectl get all --all-namespaces

echo "All Kubernetes manifests applied successfully!"
