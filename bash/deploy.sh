#!/bin/bash

BASE_DIR="$(dirname "$(realpath "$0")")/../k8s"

SERVICE_DIRS=("../rabbitmq" "merchants" "notifications" "search" "transactions" "users" "../observability/grafana" "../observability/prometheus" "../observability/loki" "../observability/tempo" "../observability/promtail")

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

# Apply DaemonSets after Deployments
for SERVICE in "${SERVICE_DIRS[@]}"; do
    echo "Applying DaemonSets for $SERVICE..."
    for FILE in $BASE_DIR/services/$SERVICE/*daemonset*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply Services after DaemonSets
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

# Apply Stripe listener Deployment
kubectl apply -f "$BASE_DIR/services/transactions/stripe-listener.yaml"
kubectl rollout status deployment/stripe-listener

# Grab the Stripe listener pod name
POD=$(kubectl get pods -l app=stripe-listener -o jsonpath='{.items[0].metadata.name}')
if [[ -z "$POD" ]]; then
  echo "ERROR: stripe-listener pod not found" >&2
  exit 1
fi

# Extract webhook secret from Stripe CLI logs
echo "Waiting for Stripe CLI to print webhook secret…"
for i in {1..10}; do
  # Remove --since so we search all logs
  LOGS=$(kubectl logs "$POD" -c stripe-cli || true)
  # Match alphanumeric + underscores
  SECRET=$(grep -o 'whsec_[[:alnum:]_]*' <<<"$LOGS" | head -1)
  if [[ -n "$SECRET" ]]; then
    echo "Found webhook secret: $SECRET"
    break
  fi
  sleep 5
done

if [[ -z "${SECRET:-}" ]]; then
  echo "ERROR: webhook secret not found in logs" >&2
  exit 1
fi

# Read and decode the existing STRIPE_SECRET_KEY from the cluster
OLD_KEY_ENC=$(kubectl get secret stripe-secret -o jsonpath='{.data.STRIPE_SECRET_KEY}')
OLD_KEY=$(printf "%s" "$OLD_KEY_ENC" | base64 --decode)

# Recreate the stripe secret in-memory with both keys, then apply
kubectl create secret generic stripe-secret \
  --from-literal=STRIPE_SECRET_KEY="$OLD_KEY" \
  --from-literal=STRIPE_WEBHOOK_SECRET="$SECRET" \
  --dry-run=client -o yaml | kubectl apply -f -

# Check the status of all resources
echo "Checking the status of the resources..."
kubectl get all --all-namespaces

echo "All Kubernetes manifests applied successfully!"
