# 深度学习在线推理服务
深度学习在线推理服务，提供统一的RPC服务调用接口，支持TensorFlow和PyTorch模型的多实例部署，支持GPU和CPU版本，实现一个模型多个实例提供服务时的负载均衡策略，能有效解决深度学习不同模型输入输出类型不一致导致接口无法统一的问题，同时能保证线上服务关注的服务性能指标。优化深度学习模型线上部署流程，减少算法人员模型部署及上线成本。本发明包含对外统一调用RPC服务、TensorFlow模型部署、PyTorch模型部署三个技术模块，模块间调用关系如下图所示。

![模块调用关系](modules.png)

深度学习在线推理服务包括 对外统一调用GRPC服务，tensorflow-serving模型部署和pytorch模型部署 3个部分  
1. [wpaidlpredictonline](./wpaidlpredictonline)：对外统一调用GRPC服务  
2. [PyTorchPredictOnline](./PyTorchPredictOnline)：pytorch模型部署，基于Seldon实现，将PyTorch模型加载提供gRPC服务调用，模型推理中引入前后预处理程序，支持用户在执行Pytorch模型预测前后进行相关数据的处理；同时开放模型调用，用户可以根据业务及模型的特点进行模型调用独立定制。  
3. TensorFlow模型部署：采用TensorFlow-Serving框架部署，部署方式支持物理机、Dokcer容器，分GPU和CPU版本。后面将详细介绍Tensorflow模型部署方式    

## 对外统一调用GRPC服务
[对外统一调用服务](./wpaidlpredictonline)   
基于gRPC框架实现，作为整个深度学习在线推理服务的入口，承担在线推理的流量接入，提供TensorFlow、PyTorch两种模型通用调用接口，接收在线预测请求，与后端部署的TensorFlow或PyTorch模型实例进行交互，实现负载均衡策略。   
接口定义如下：
```$xslt
service WpaiDLPredictOnlineService {
    rpc Predict(PredictRequest) returns (PredictResponse);
    rpc PytorchPredict(PytorchPredictRequest) returns (PytorchPredictResponse);
}
```
## Tensorflow模型部署
服务采用Tensorflow-Serving来部署Tensorflow模型，部署方式支持docker、物理机部署，但是由于物理机部署环境安装复杂，强烈建议使用docker来部署环境  
下面介绍容器化运行Tensorflow-Serving服务  
### 部署环境
采用容器化部署方式，需要Docker环境  
需要获取镜像，服务器需要开通外网权限  
### 部署方式
使用docker来部署环境，下面介绍docker环境部署方式：  
1. 从[docker hub镜像仓库](https://hub.docker.com/r/tensorflow/serving)获取镜像，以Tensorflow-Serving 1.14版本为例，docker pull tensorflow/serving:1.14.0
2. 配置模型路径，TESTDATA="$(pwd)/testdata"，可以使用demo中的[cnn模型](./wpaidlpredictonline/demo/model/tensorflow/textcnn)  
3. 运行模型，docker run -t --rm -p 8501:8501 
            -v "$TESTDATA/saved_model_half_plus_two_cpu:/models/half_plus_two" 
            -e MODEL_NAME=half_plus_two-69 
            tensorflow/serving:1.14.0 &
4. 将服务器IP 及端口 8501 配置到 GRPC服务中nodefile.txt配置文件即可  
5. [官方提供测试模型](https://github.com/tensorflow/serving/tree/master/tensorflow_serving/servables/tensorflow/testdata)，可下载部署使用  
  
## Pytorch模型部署
参考[PyTorchPredictOnline项目](./PyTorchPredictOnline)中相关说明

## 贡献指引
本次开源只是dl_inference贡献社区的一小步，我们真挚地希望开发者向我们提出宝贵的意见和建议。
您可以挑选以下方式向我提交反馈建议和问题：  

1. 在https://github.com/wuba/dl_inference 提交PR或者lssue。
2. 邮件发送至 ailab-opensource@58.com