# demo示例
提供完整服务部署、模型部署、模型示例、客户端等一系列流程  

## Tensorflow Demo
### Tensorflow 模型部署
Tensorflow模型部署步骤如下：
1. 下载[待部署的Tensorflow模型文件](model/tensorflow/textcnn)到本地/model/textcnn  
   模型路径：TESTDATA=/model/textcnn
2. 运行容器，启动tensorflow-serving，绑定到宿主机8501端口上，命令：
     docker run -t --rm -p 8501:8501 -p 8500:8500 -v "$TESTDATA:/models/textcnn-69" -e MODEL_NAME=textcnn-69 tensorflow/serving:1.14.0 &  
3. -e MODEL_NAME=textcnn-69 表示模型名称，其中69表示此次测试的任务id，由用户自己指定  
4. -v /models/textcnn-69 容器内部目录必须符合/models/$MODEL_NAME 规范
5. -p 8501:8501 http端口 表示将宿主机端口8051绑定到容器8051端口上，这样可以通过物理机8051端口可以直接访问容器中服务
6. -p 8500:8500 grpc端口  

模型启动日志如下所示：
![模型启动日志](tensorflow-serving start log.png)  

示例模型结构如下所示：  
```
// 命令：saved_model_cli show --dir 1/ --all
MetaGraphDef with tag-set: 'serve' contains the following SignatureDefs:

signature_def['prediction']:
  The given SavedModel SignatureDef contains the following input(s):
    inputs['label'] tensor_info:
        dtype: DT_STRING
        shape: (-1)
        name: label:0
    inputs['sentence'] tensor_info:
        dtype: DT_STRING
        shape: (-1, -1)
        name: sentence:0
  The given SavedModel SignatureDef contains the following output(s):
    outputs['output'] tensor_info:
        dtype: DT_STRING
        shape: (-1, 3)
        name: inference/label_out:0
    outputs['score'] tensor_info:
        dtype: DT_FLOAT
        shape: (-1, 3)
        name: inference/output:0
  Method name is: tensorflow/serving/predict
```

### 服务部署
部署Tensorflow模型后部署服务，以部署在./opensources 目录下为例，部署步骤如下：
1. maven install 打包服务端代码，在target目录下找到dlpredictonline-0.0.1-SNAPSHOT.jar，上传服务器./opensources目录下
2. 在target/lib为全部依赖包，将文件夹上传到服务器./opensources目录下
3. ./opensources目录创建config目录，将配置文件nodefile.txt、config.properties上传到./opensources/config目录下
4. 配置nodefile.txt文件内容，将部署Tensorflow模型服务器IP+port（8500，注意这里使用grpc端口）+调用模型超时信息配置在文件中，如下所示：  
   69=100,$ip:8500  
5. 服务启动脚本如下所示：  

```bash
dir=lib
libs=""
for file in $dir/*; do
    libs=$libs:$file
done

libs=dlpredictonline-0.0.1-SNAPSHOT.jar:$libs
## 参数，服务启动端口50050
java -classpath $libs com.bj58.ailab.dlpredictonline.components.WPAIDLPredictOnlineGRPCService 50050
```

服务部署目录结构如下所示：  

```
.
├── cmd.sh
├── config
│   ├── config.properties
│   └── nodefile.txt
├── dlpredictonline-0.0.1-SNAPSHOT.jar
└── lib
    ├── animal-sniffer-annotations-1.17.jar
    ├── checker-compat-qual-2.5.2.jar
    ├── error_prone_annotations-2.2.0.jar
    ├── fastjson-1.2.53.jar
    ├── grpc-context-1.17.1.jar
    ├── grpc-core-1.17.1.jar
    ├── grpc-netty-1.17.1.jar
    ├── grpc-protobuf-1.17.1.jar
    ├── grpc-protobuf-lite-1.17.1.jar
    ├── grpc-stub-1.17.1.jar
    ├── gson-2.7.jar
    ├── guava-26.0-android.jar
    ├── hamcrest-core-1.3.jar
    ├── j2objc-annotations-1.1.jar
    ├── jsr305-3.0.2.jar
    ├── log4j-1.2.17.jar
    ├── log4j-api-2.3.jar
    ├── log4j-core-2.3.jar
    ├── log4j-slf4j-impl-2.3.jar
    ├── netty-buffer-4.1.30.Final.jar
    ├── netty-codec-4.1.30.Final.jar
    ├── netty-codec-http2-4.1.30.Final.jar
    ├── netty-codec-http-4.1.30.Final.jar
    ├── netty-codec-socks-4.1.30.Final.jar
    ├── netty-common-4.1.30.Final.jar
    ├── netty-handler-4.1.30.Final.jar
    ├── netty-handler-proxy-4.1.30.Final.jar
    ├── netty-resolver-4.1.30.Final.jar
    ├── netty-transport-4.1.30.Final.jar
    ├── opencensus-api-0.17.0.jar
    ├── opencensus-contrib-grpc-metrics-0.17.0.jar
    ├── protobuf-java-3.3.1.jar
    ├── proto-google-common-protos-1.0.0.jar
    ├── slf4j-api-1.7.12.jar
    ├── slf4j-log4j12-1.7.21.jar
    └── tensorflow-client-1.4-1.jar
```

### 运行客户端
可直接在IDEA上运行[client](src/main/java/com/bj58/ailab/demo/client/WpaiClient.java)，传入服务部署ip port即可  
参考 [TensorflowTextCnn实现](src/main/java/com/bj58/ailab/demo/client/TensorflowTextCnn.java)    
结果如下所示：
```
// sentence="你打错了"
score:
key=__label__hangup, value=0.9999962
key=__label__suggest, value=2.047564E-6
key=__label__chat, value=1.6384198E-6
// sentence="不用了谢谢"
score:
key=__label__negate, value=0.9999424
key=__label__offer, value=1.6348207E-5
key=__label__thankyou, value=1.5074686E-5
```

## PyTorch Demo
### PyTorch 模型部署

PyTorch模型部署步骤如下：

1. 下载[待部署的PyTorch模型文件](model/pytorch/mnist)到本地/model/deps  
   下载[startPredict.sh](../../PyTorchPredictOnline/startPredict.sh), [gpu/predictor.py](../../PyTorchPredictOnline/gpu/predictor.py), [localtime](../../PyTorchPredictOnline/DockerImages/deps/localtime)到本地/model/deps
   下载[Dockerfile_gpu](../../PyTorchPredictOnline/DockerImages/Dockerfile_gpu)到本地/model,并重命名为Dockerfile
2. 创建镜像，命令：
   docker build -t pytorch-online:lastest .
3. 运行容器，启动pytorch-online，绑定到宿主机8866端口上，命令：
   docker run -dt -p 8866:8866 pytorch-online:lastest &
4. -p 8866:8866 grpc端口