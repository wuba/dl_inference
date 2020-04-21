# 深度学习在线推理服务
## 服务启动
### 服务部署
下述文件放在同一目录下即可
```$xslt
打包文件：dlpredictonline-0.0.1-SNAPSHOT.jar
依赖包(maven install之后target/lib目录)：libs
配置文件：参数配置，config/config.properties, 
         推理节点信息配置，config/nodefile.txt
```
### 运行环境
java > 1.8
### 服务启动
入口：WPAIDLPredictOnlineGRPCService  
参数：port，服务启动端口  
java：1.8  
启动命令如下所示：
```bash
dir=lib
libs=""
for file in $dir/*; do
    libs=$libs:$file
done

libs=dlpredictonline-0.0.1-SNAPSHOT.jar:$libs
java -classpath $libs com.bj58.ailab.dlpredictonline.components.WPAIDLPredictOnlineGRPCService 50050
```
### 配置文件说明
resources/config.properties 参数配置，示例如下所示：
```properties
## GRPC连接最大包大小
predict.grpc.maxMessageSize=10485760
## 后端节点配置文件
predict.online.node.file=config/nodefile.txt
```
resources/nodefile.txt 后端节点配置，可动态修改配置文件 
```properties
## 配置说明，每行包括如下参数
## taskid=timeout,ip:port,ip:port,...
100=100,172.11.71.188:8888,172.11.71.188:8866
111=1000,172.11.71.189:8899,172.11.71.189:8877
69=100,172.11.71.187:8866
29=1000,172.11.174.4:8866
```
timeout：超时时间配置，单位ms
## 模型部署
此项目不涉及后端模型部署方案，需要用户部署模型节点，支持Tensorflow、Pytorch两种框架模型  
### Tensorflow部署
#### docker部署
镜像地址：https://hub.docker.com/r/tensorflow/serving  
下载镜像运行模型，将ip port配置在nodefile.txt配置文件中即可  
运行命令参考：https://github.com/tensorflow/serving/blob/master/tensorflow_serving/g3doc/docker.md  
强烈建议使用此方式  
使用demo/model/tensorflow/cnn模型部署，使用8501端口 为例：
1. 配置模型路径：TESTDATA="demo/model/tensorflow/cnn"
2. 运行模型，docker run -t --rm -p 8501:8501 -p 8500:8500 -v "$TESTDATA:/models/textcnn-69" -e MODEL_NAME=textcnn-69 tensorflow/serving:1.14.0 &

#### 物理机部署
支持物理机部署，但是物理机环境部署复杂，强烈建议使用docker环境部署    
## 模型调用接口
### Tensorflow模型
#### 接口定义  
```protobuf
## GRPC proto定义
rpc Predict(PredictRequest) returns (PredictResponse);
```
#### Java程序依赖Jar包  
```xml
<dependency>
  <groupId>com.yesup.oss</groupId>
  <artifactId>tensorflow-client</artifactId>
  <version>1.4-1</version>
</dependency>
```
#### 客户端示例
[客户端请求示例代码](../demo/src/main/java/com/bj58/ailab/demo/client)
## GRPC服务代码生成
[GRPC服务代码生成](./src/main/java/com/bj58/ailab/dlpredictonline/grpc)