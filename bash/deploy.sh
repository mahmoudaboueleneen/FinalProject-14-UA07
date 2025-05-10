#!/bin/bash

BASE_DIR="$(dirname "$(realpath "$0")")/../k8s"

SERVICE_DIRS=("../rabbitmq" "merchants" "notifications" "search" "transactions" "users")

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

# Check the status of all resources
echo "Checking the status of the resources..."
kubectl get all --all-namespaces

echo "All Kubernetes manifests applied successfully!"
