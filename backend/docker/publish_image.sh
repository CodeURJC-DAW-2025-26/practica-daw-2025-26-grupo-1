#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "Use: $0 <user> <image_name>"
    exit 1
fi

USER=$1
NAME=$2

docker tag "$NAME" "$USER/$NAME:latest"

docker push "$USER/$NAME:latest"