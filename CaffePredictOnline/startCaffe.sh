#!/bin/bash
#
# caffe在线预测镜像启动脚本

seldon-core-microservice CaffeModel GRPC --service-type MODEL --persistence 0

