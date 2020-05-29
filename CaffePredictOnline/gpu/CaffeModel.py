# -*- coding:utf-8 -*-

import numpy as np
import caffe
from preprocess import init_caffe_transformer, preprocess_image
from postprocess import	postprocess_result
import traceback

class CaffeModel():

    """Docstring for Classifier. """

    #def __init__(self, MODEL_FILE, PRETRAINED, from_src=False):
    def __init__(self):
        """TODO: initialize the net."""

        caffe.set_mode_gpu()
        MODEL_FILE = "deploy.prototxt"
        PRETRAINED = "online.caffemodel"
        self.net = caffe.Net(MODEL_FILE, PRETRAINED, caffe.TEST)
        self.transformer = init_caffe_transformer(self.net)

    def predict(self, image, feature_names):
        try:
            image, batchsize = preprocess_image(image) 
            data_blob_shape = self.net.blobs['data'].data.shape
            data_blob_shape = list(data_blob_shape)

            self.net.blobs['data'].reshape(batchsize, data_blob_shape[1], data_blob_shape[2], data_blob_shape[3])
            if batchsize > 1:
                self.net.blobs['data'].data[...] = map(lambda x: self.transformer.preprocess('data', x), image)
            else:
                self.net.blobs['data'].data[...] = self.transformer.preprocess('data', image)

            out = self.net.forward()
	
	    ret = postprocess_result(out)
            return np.array(ret)
        except:
            print(traceback.format_exc()) 
