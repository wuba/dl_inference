

<center><h2>Caffe镜像启动程序</h2></center>
## 介绍

- startCaffe.sh ：在线预测任务启动脚本  

- preprocess.py ：预测模型文件   

- CaffeModel.py postprocess.py ：数据处理函数定义文件,可根据实际需要进行修改，定义规范如下

- requirements.txt ：模型依赖python包指定文件   

```python
# 预处理函数
# 参数：
# x 用户封装的数据，类型包括str，bytes，numpy.array
# 返回值：
# 模型执行的输入数据
def preprocess_image(x):
    return x
# 后处理函数
# 参数：
# x 模型执行后的输出数据，即model(data)所得得结果
# 返回值：
# 处理后的结果数据
def postprocess_result(x):
	return x
# 自定义模型调用函数
# 参数：
# 
# x 模型执行后的输出数据，即model(data)所得得结果
# kwargs 用户添加的自定义参数
# 返回值：
# 模型执行结果数据
def run_model(model, x, **kwargs):
	return model(x)

```

## Caffe模型部署
### docker部署

生成镜像：  
```bash
cd DockerImages
docker build -t caffe-online:lastest .
```
启动镜像：  
```bash
docker run -it -p 8866:8866 caffe-online:lastest /bin/bash
```
将模型文件、模型依赖文件、preprocess.py、postprocess.py、startCaffe.sh文件放在一级目录下  
启动命令:
```bash
sh startCaffe.sh
```

### 模型调用接口
#### 接口定义
```protobuf
rpc Predict(SeldonMessage) returns (SeldonMessage) {};
```
