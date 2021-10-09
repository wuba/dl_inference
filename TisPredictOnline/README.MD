
<center><h2>TIS镜像启动程序</h2></center>

## 模型转换镜像

- 模型文件：训练好的模型文件，savedmodel.pb 或 model.pth

- 模型元数据描述文件，命名为config.txt(以resnet50为例)：

```python
{
    "batch_size": 0,
    "input": [
        {
            "name": "image",
            "data_type": "float",
            "dims": [-1, 224, 224, 3],
            "node_name": "input_1:0"
        }
    ],
    "output": [
        {
            "name": "probs",
            "data_type": "float",
            "dims": [-1, 19],
            "node_name": "dense_1/Softmax:0"
        }
    ]
}

# savedmodel模型元数据可由saved_model_cli 命令工具查看；pth模型由于没有name概念，可设置为单字母名称，如 i, o等

```

### docker启动

生成镜像：  
```bash
cd DockerImage
docker build -t  tis-model-convert:lastest .
```
启动镜像：  
```bash
cd 模型所在路径
docker run  -v `pwd`:/workspace/source_model -e SOURCE_MODEL_PATH=/workspace/source_model -e TARGET_MODEL_PATH=/workspace/source_model -e MODEL_NAME=tensorflow-666 -e MODEL_TYPE=tensorflow tis-model-convert:lastest

# SOURCE_MODEL_PATH 原始模型所在路径，也是模型描述文件所在路径
# TARGET_MODEL_PATH 生成TensorRT模型所在路径
# MODEL_NAME 模型名称
# MODEL_TYPE 模型类型 （tensorflow or pytorch）
```

## 模型部署镜像
### docker部署

拉取镜像：  
```bash
docker pull nvcr.io/nvidia/tritonserver:20.08-py3
```

启动镜像：  
```bash
docker run  -v ${TARGET_MODEL_PATH}:/workspace  -p 8001:8001  nvcr.io/nvidia/tritonserver:20.08-py3
 /opt/tritonserver/bin/tritonserver --model-repository=/workspace
 
#  TARGET_MODEL_PATH即为模型转换服务中环境变量值，默认使用8001端口且此端口固定
```

### 模型调用接口
#### 接口定义
```protobuf
rpc TisPredict(TisPredictRequest) returns (TisPredictResponse) {};
```