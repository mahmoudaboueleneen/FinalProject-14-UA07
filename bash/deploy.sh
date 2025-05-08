#!/bin/bash

BASE_DIR="$(dirname "$(realpath "$0")")/../k8s"

SERVICE_DIRS=("merchants" "notifications" "search" "transactions" "users")

# Apply manifests for each service directory
for SERVICE in "${SERVICE_DIRS[@]}"; do
    echo "Applying Kubernetes manifests for $SERVICE..."

    # Apply all .yaml files
    for FILE in "$BASE_DIR/services/$SERVICE"/*.yaml; do
        echo "Applying $FILE..."
        kubectl apply -f "$FILE"
    done
done

# Apply the apigateway manifests
echo "Applying Kubernetes manifests for apigateway..."
for FILE in "$BASE_DIR/apigateway"/*.yaml; do
    echo "Applying $FILE..."
    kubectl apply -f "$FILE"
done

echo "Checking the status of the resources..."
kubectl get all --all-namespaces

echo "All Kubernetes manifests applied successfully!"
