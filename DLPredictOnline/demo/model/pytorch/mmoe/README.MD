#### MMoE模型示例

本代码为使用MMoE模型在WPAI平台完成58同城第二届算法大赛的示例。

**赛题描述**

58同城举办了第二届算法大赛，提供了58招聘实际推荐场景中的数据，参赛者可以基于用户的历史行为数据，利用机器学习算法预测出用户对于不同职位的浏览和投递概率。

详细内容参见[大赛官网](https://tech.58.com/game/introduction?contestId=4&token=58tech)。

**代码结构**

```sh
|-- train.py              主程序，完成模型训练和测试
|-- util.py               数据预处理部分
|-- process.py            模型训练部分
|-- mmoe_pytorch.ckpt     训练好的模型文件
|-- maxmin.pkl            保存好的每个特征的最大值和最小值
|-- Models
		|-- mmoe_pytorch.py   mmoe模型
```

process.py主要是模型训练和评测部分，train_step控制了模型的训练，根据模型在验证集上的表现来决定是否更新模型参数。

mmoe_pytorch.py为模型文件，其中Config类为配置文件，包括训练数据路径，模型文件保存路径，迭代次数，早停，模型超参数(学习率，专家网络个数，隐层神经元个数，批大小等等)；Model类为MMoE网络结构。

util.py主要是数据预处理部分，包括加载数据，数据集的划分以及数据预处理，标签预处理等过程。

**数据**

这里使用的是大赛官方默认数据集，存放在 /workspace/mdata下，代码中直接读取即可。其中train.txt为训练集，包括特征和label；test.txt为测试集，只包括特征，不包含label。

**输入输出描述**

输入为大赛提供的train.txt，test.txt文件，模型会使用train.txt数据进行训练，训练完成后会对test.txt进行预测，结果保存到/workspace/model/submission.csv。

**运行实例**

在WPAI平台选择镜像环境为Pytorch1.8。然后将代码打包，注意直接将该路径下的所有文件打包，不要对文件夹打包。然后在WPAI平台上传代码，点击运行即可。

<img src="https://tva1.sinaimg.cn/large/008i3skNly1gsu5mgo678j31b60cejsw.jpg" alt="image-20210726104329036" style="zoom: 40%;" />

**更多介绍**

参考公众号文章[WPAI中使用MMoE模型完成58同城AI算法大赛](https://mp.weixin.qq.com/s/-lXFEfWuk-mePuFBf_p0Ww)







