#!/bin/bash

DOCKER_USERNAME="mahmoudaboueleneen"
REPO_NAME="amazon-replica"

SERVICES_DIR="../services"
APIGATEWAY_DIR="../apigateway"

# Check if Docker is installed
if ! command -v docker &> /dev/null
then
    echo "Docker is not installed. Please install Docker first."
    exit 1
fi

# Function to build, tag, and push Docker image
build_and_push_image() {
    SERVICE_PATH=$1
    IMAGE_TAG=$2

    cd "$SERVICE_PATH" || exit 1

    echo "Building Docker image for $IMAGE_TAG..."
    docker build -t "$DOCKER_USERNAME/$REPO_NAME:$IMAGE_TAG" .

    if [ $? -eq 0 ]; then
        echo "Pushing $DOCKER_USERNAME/$REPO_NAME:$IMAGE_TAG to Docker Hub..."
        docker push "$DOCKER_USERNAME/$REPO_NAME:$IMAGE_TAG"
    else
        echo "Failed to build Docker image for $IMAGE_TAG."
    fi

    cd - > /dev/null || exit 1

    echo "Done with $IMAGE_TAG."
}

# Process API Gateway
echo "Processing API Gateway..."
build_and_push_image "$APIGATEWAY_DIR" "apigateway"

# Loop through services
for SERVICE_PATH in "$SERVICES_DIR"/*/
do
    BASENAME=$(basename "$SERVICE_PATH")
    IMAGE_TAG="${BASENAME}-service"

    echo "Processing $IMAGE_TAG..."
    build_and_push_image "$SERVICE_PATH" "$IMAGE_TAG"
done

echo "All services and API Gateway processed."
