#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "Use: $0 <dockerhub_user> <image_name>"
    exit 1
fi

USER=$1
IMAGE=$2

docker compose publish "$USER/$IMAGE-compose:latest"

if [ $? -eq 0 ]; then
    echo "Published correctly"
else
    echo "Error while publishing"
fi