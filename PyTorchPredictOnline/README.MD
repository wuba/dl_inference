

<center><h2>深度学习在线预测镜像启动程序</h2></center>
## 介绍

- startPredict.sh ：在线预测任务启动脚本  

- predictor.py ：预测模型文件   

- processor.py ：数据处理函数定义文件,可根据实际需要进行修改，定义规范如下

- requirements.txt ：模型依赖python包指定文件   

```python
# 预处理函数
# 参数：
# x 用户封装的数据，类型包括str，bytes，numpy.array
# kwargs 用户添加的自定义参数
# 返回值：
# 模型执行的输入数据
def preprocess(x, **kwargs):
    return x
# 后处理函数
# 参数：
# x 模型执行后的输出数据，即model(data)所得得结果
# kwargs 用户添加的自定义参数
# 返回值：
# 处理后的结果数据
def postprocess(x, **kwargs):
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

- 模型依赖文件 

   模型定义文件和其他参数文件，在线预测通过 torch.load(‘model.pth’) 方式加载模型文件，因此需要相应的模型定义文件。 

- 模型文件：训练好的模型文件，model.pth

## PyTorch模型部署
### docker部署

生成镜像：  
```bash
cd DockerImages
docker build -t pytorch-online:lastest .
```
启动镜像：  
```bash
docker run -it -p 8866:8866 pytorch-online:lastest /bin/bash
```
将模型文件、模型依赖文件、processor.py、predictor.py、startPredict.sh文件放在一级目录下  
启动命令:
```bash
sh startPredict.sh
```

### 模型调用接口
#### 接口定义
```protobuf
rpc Predict(SeldonMessage) returns (SeldonMessage) {};
```