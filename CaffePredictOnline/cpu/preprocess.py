#!/usr/bin/env python
# -*- coding: utf-8 -*-
import caffe
from PIL import Image
import base64
import io
import numpy as np

def init_caffe_transformer(net):
    """
    初始化caffe transformer，比如设置图片归一化的均值等
    param: net, caffe预测网络
    return: transformer
    """
    transformer = caffe.io.Transformer({'data': net.blobs['data'].data.shape})
    transformer.set_mean('data', np.array([104, 117, 123]))
    transformer.set_transpose('data', (2, 0, 1))

    return transformer

def preprocess_image(image):
    """
    预处理图片，比如将base64 string 转码成图片数组
    param: image, 调用接口传进来的图片数据
    return: im, 传入caffe模型进行预测的图片数据
    """
    imgdata = base64.b64decode(image)
    stream = io.BytesIO(imgdata)
    img = Image.open(stream)
    im = img.convert("RGB")
    im = np.asarray(im, dtype="int32")
    new_im = np.array([im, im])

    return new_im, 2


