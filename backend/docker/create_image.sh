#!/bin/bash

if [ -z "$1" ]; then
    echo "Error: You have to pass the name of the image. Example: ./create_image.sh my-app"
    exit 1
fi

IMAGE_NAME=$1

docker build -t "$IMAGE_NAME" -f Dockerfile ..
echo "Image created"