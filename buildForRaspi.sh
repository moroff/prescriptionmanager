#! /bin/bash
set -e

# Build ISO image
docker image build --platform=linux/arm64 --tag myhousehold/prescriptionmanager --builder=container --load .
docker save -o images/prescriptionmanager-arm64-iso.tar myhousehold/prescriptionmanager

# Transfer ISO image
rsync --verbose --recursive --include="*-arm64-iso.tar" images pi@printpi-rsync:.

# Load and start docker container on Raspi
ssh pi@printpi-rsync docker load -i images/prescriptionmanager-arm64-iso.tar
ssh pi@printpi-rsync docker run --net pmNet -p 8080:8080 myhousehold/prescriptionmanager