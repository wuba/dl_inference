#!/bin/bash

pip install -r requirements.txt
mkdir -p /etc/podinfo/annotations
echo seldon.io/grpc-max-message-size=\"10485760\" >> /etc/podinfo/annotations
seldon-core-microservice predictor GRPC --service-type MODEL --persistence 0 --workers 10
