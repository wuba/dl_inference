#### MMoE模型示例

模型文件为Models/mmoe_pytorch_ai.py

**赛题描述**

58同城举办了第二届算法大赛，提供了58招聘实际推荐场景中的数据，参赛者可以基于用户的历史行为数据，利用机器学习算法预测出用户对于不同职位的浏览和投递概率。

详细内容参见[大赛官网](https://tech.58.com/game/introduction?contestId=4&token=58tech)。

**数据**

[大赛数据](https://tech.58.com/game/problemDesc?contestId=4&token=58tech)可在赛题说明下载。这里我们使用大赛官方默认数据集，存放在 /workspace/mdata下，代码中直接读取即可。其中train.txt为训练集，包括特征和label；test.txt为测试集，只包括特征，不包含label。

**输入输出描述**

输入为大赛提供的train.txt，test.txt文件，模型会使用train.txt数据进行训练，训练完成后会对test.txt进行预测，结果保存到/workspace/model/submission.csv。

**模型文件**

模型会保存在/workspace/model/mmoe_pytorch_ai.ckpt下。

**公众号文章**








