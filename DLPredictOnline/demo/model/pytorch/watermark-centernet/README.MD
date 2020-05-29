# watermark-centernet模型示例

client程序 [PyTorchClient](../../../src/main/java/com/bj58/ailab/demo/client/PyTorchClient.java)  

## 数据
[test_data](./data) 样本文件   

## 数据处理函数定义文件
[processor.py](./processor.py): 数据处理函数定义文件，名称固定为processor.py

## 模型文件
[model.pth](http://wos.58cdn.com.cn/nOlKjIhGntU/dlinference/demo_model_pytorch_model.pth): 模型文件，名称固定为model.pth，**模型下载之后需要将模型重名为 model.pth**

## 模型依赖python包指定文件 
[requirements.txt](./requirements.txt):模型依赖python包指定文件，名称固定为requirements.txt

## 模型定义文件
models及myutils文件夹下的文件为模型训练时的定义文件，供PyTorch加载模型文件时使用
