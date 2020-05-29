#!/bin/bash

cd /opt
pip install -r /opt/requirements.txt
rm -rf /etc/podinfo
mkdir -p /etc/podinfo
echo seldon.io/grpc-max-message-size=\"10485760\" >> /etc/podinfo/annotations
seldon-core-microservice predictor GRPC --service-type MODEL --persistence 0 --workers 10

