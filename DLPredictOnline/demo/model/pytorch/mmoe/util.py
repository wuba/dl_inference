'''
data prerocess
'''

import pandas as pd
import numpy as np
import pickle
import torch
from sklearn.model_selection import train_test_split
from sklearn.utils import shuffle
from torch.nn import functional as F


def to_categorical(labels,n_class=2):
    batch = labels.shape[0]
    result = np.zeros((batch,n_class))
    for i in range(batch):
        if n_class == 1:
            result[i] = labels[i]
        else:
            result[i][labels[i]] = 1
    return result

def generate_max_min_array(data):
    res = []
    resfile = open("./maxmin.pkl","wb")
    res.append(data.max())
    res.append(data.min())
    pickle.dump(res,resfile)

def load_max_min_array():
    resfile = open("./maxmin.pkl", "rb")
    res = pickle.load(resfile)
    maxarray = res[0]
    minarray = res[1]
    return maxarray,minarray

def deal_data(label_cols, data,isTrain=False):
    label = []
    for lal in label_cols:
        label.append(to_categorical(data[lal].values, n_class=1))
    data = data.drop(label_cols, axis=1)
    data = data.astype('float')
    if isTrain:
        generate_max_min_array(data)
    maxarray,minarray = load_max_min_array()
    data = (data - minarray) / (maxarray - minarray)
    data = data.fillna(0)
    data[data > 1] = 1
    data[data < 0] = 0
    return data.values, label

def deal_data_test(data):
    data = data.astype('float')
    maxarray, minarray = load_max_min_array()
    data = (data - minarray) / (maxarray - minarray)
    data = data.fillna(0)
    data[data > 1] = 1
    data[data < 0] = 0
    return data.values

def transform2content_zhaopin(data, label):
    content = []
    for i in range(len(data)):
        content.append([data[i], label[0][i], label[1][i]])
    return content


def to_str(x):
    if isinstance(x,str):
        xlist = x.split(':')
        return xlist[1]
    else:
        return x

def generate_train_valid_test_set_zhaopin(config,path):
    tmpdf = pd.read_csv(path,sep='\t',header=None)
    tuijian = tmpdf.applymap(to_str)
    tuijian = shuffle(tuijian)
    tuijian.drop([5,6,7,36,37,69,70],axis=1,inplace = True)
    datafordeep = tuijian.fillna(0)
    datafordeep.columns = ['click', 'resume'] + [i for i in range(0, tuijian.shape[1] - 2)]
    datafordeep['resume'] = datafordeep[['click', 'resume']].apply(
        lambda x: -1 if int(x[0] == 0) else 1 if int(x[1] == 1) else 0, axis=1)

    traindata, tmpdata= train_test_split(datafordeep, test_size=0.4,random_state=0)
    devdata, testdata = train_test_split(tmpdata, test_size=0.5, random_state=0)

    train_data, train_label = deal_data(config.label_columns,traindata,True)
    dev_data, dev_label = deal_data(config.label_columns,devdata,False)
    test_data, test_label = deal_data(config.label_columns,testdata,False)

    config.num_feature = train_data.shape[1]
    trainset = transform2content_zhaopin(train_data,train_label)
    devset = transform2content_zhaopin(dev_data,dev_label)
    testset = transform2content_zhaopin(test_data,test_label)
    return trainset,devset,testset

def ai_split(config):
    data_path = config.data_path
    trainset,devset,testset = generate_train_valid_test_set_zhaopin(config,data_path)
    return trainset, testset, devset

def generate_test_set(config):
    test_df = pd.read_csv(config.test_path, sep='\t', header=None)
    print(test_df.shape)

    tuijian = test_df.applymap(to_str)
    print(tuijian.head(10))
    print(tuijian.columns)

    tuijian.drop([3, 32, 33, 65, 66], axis=1, inplace=True)
    datafordeep = tuijian.fillna(0)
    datafordeep.columns = [i for i in range(0, datafordeep.shape[1])]

    test_data = deal_data_test(datafordeep)
    config.num_feature = test_data.shape[1]
    print(test_data.shape)
    return test_data

class Dataiterater(object):
    def __init__(self,batches,batch_size,device):
        self.batch_size = batch_size
        self.batches = batches
        self.n_batches = len(batches) // batch_size

        self.residue =False
        if len(batches)%batch_size != 0:
            self.residue = True
        self.index = 0
        self.device = device
    def _to_n_tensor(self, datas):

        x = torch.FloatTensor([_[0] for _ in datas]).to(self.device)
        y1 = torch.FloatTensor([_[1] for _ in datas]).to(self.device)
        y2 = torch.FloatTensor([_[2] for _ in datas]).to(self.device)
        return x, (y1,y2)

    def __next__(self):
        if self.residue and self.index == self.n_batches:
            batches = self.batches[self.index * self.batch_size: len(self.batches)]
            self.index += 1
            batches = self._to_n_tensor(batches)

            return batches

        elif self.index >= self.n_batches:
            self.index = 0
            raise StopIteration
        else:
            batches = self.batches[self.index * self.batch_size: (self.index + 1) * self.batch_size]
            self.index += 1
            batches = self._to_n_tensor(batches)
            return batches

    def __iter__(self):
        return self

    def __len__(self):
        if self.residue:
            return self.n_batches + 1
        else:
            return self.n_batches


def build_iterator(dataset, config):
    iter = Dataiterater(dataset, config.batch_size, config.device)
    return iter


def loss_fn(name):
    if name == 'multiclass':
        return F.cross_entropy
    elif name == 'binary':
        return F.binary_cross_entropy

