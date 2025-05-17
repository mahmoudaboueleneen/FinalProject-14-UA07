#!/bin/bash

# Resolve script directory and base k8s path
BASE_DIR="$(dirname "$(realpath "$0")")/../k8s"

SERVICE_DIRS=("../rabbitmq" "merchants" "notifications" "search" "transactions" "users" "../observability/grafana" "../observability/prometheus" "../observability/loki" "../observability/tempo" "../observability/promtail")

# Helper: resolve service path reliably
resolve_service_path() {
  local svc_rel="$1"
  local path="$BASE_DIR/services/$svc_rel"
  # realpath handles cleanup of ../ segments
  if SERVICE_PATH=$(realpath "$path" 2>/dev/null); then
    echo "$SERVICE_PATH"
  else
    echo "$path"
  fi
}

# Apply ConfigMaps first (if any)
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying ConfigMaps for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*configmap*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply Secrets
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying Secrets for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*secret*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply ServiceAccounts
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying ServiceAccounts for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*serviceaccount*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply Roles
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying Roles for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*role*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply RoleBindings
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying RoleBindings for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*rolebinding*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply ClusterRoles
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying ClusterRoles for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*clusterrole*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply ClusterRoleBindings
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying ClusterRoleBindings for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*clusterrolebinding*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply StatefulSets
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying StatefulSets for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*statefulset*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply Deployments
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying Deployments for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*deployment*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply DaemonSets
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying DaemonSets for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*daemonset*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply Services
for SERVICE in "${SERVICE_DIRS[@]}"; do
    SERVICE_PATH=$(resolve_service_path "$SERVICE")
    echo "Applying Services for $SERVICE..."
    for FILE in "$SERVICE_PATH"/*service*.yaml; do
        if [[ -f "$FILE" ]]; then
            echo "Applying $FILE..."
            kubectl apply -f "$FILE"
        fi
    done
done

# Apply the apigateway manifests
APIGW_DIR="$BASE_DIR/apigateway"
echo "Applying Kubernetes manifests for apigateway..."
for FILE in "$APIGW_DIR"/*.yaml; do
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
  LOGS=$(kubectl logs "$POD" -c stripe-cli || true)
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

# Restart the transactions deployment after applying the new secret
kubectl rollout restart deployment transactions-deployment

# Check the status of all resources
echo "Checking the status of the resources..."
kubectl get all --all-namespaces

echo "All Kubernetes manifests applied successfully!"
