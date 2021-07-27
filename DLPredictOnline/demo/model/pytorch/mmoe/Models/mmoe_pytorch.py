# -*- coding: utf-8 -*-
# @Time:        2021/7/25
# @Author:      sunchangsheng
# @Contact:     sunchangsheng@58.com
# @FileName:    mmoe_pytorch.py
# @Software:    PyCharm

import torch
import torch.nn as nn
from torch.nn import functional as F
import time

class Config(object):
    def __init__(self,data_dir):
        self.data_path = data_dir + 'train.txt'
        self.test_path = data_dir + 'test.txt'
        self.test_label_path = data_dir + 'newtruelabels.txt'

        self.model_name = 'mmoe_pytorch'
        self.save_path = '/workspace/model/' + self.model_name + '.ckpt'
        self.require_improvement = 1000
        self.learning_rate = 5e-3
        self.label_columns = ['click', 'resume']
        self.device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

        self.label_dict = [1,1]
        self.num_feature = 138
        self.num_experts = 6
        self.num_tasks = 2
        self.units= 16
        self.hidden_units= 8

        self.embed_size = 64
        self.batch_size = 20480
        self.expert_hidden = 32
        self.field_size = 0

        self.num_epochs = 1000
        self.loss_fn = F.binary_cross_entropy

class Expert(nn.Module):
    def __init__(self, input_size, output_size, hidden_size):
        super(Expert, self).__init__()
        self.fc1 = nn.Linear(input_size, hidden_size)
        self.fc2 = nn.Linear(hidden_size, output_size)
        self.relu = nn.ReLU()

    def forward(self, x):
        x = self.fc1(x)
        x = self.relu(x)
        y = self.fc2(x)
        return y

class Gate(nn.Module):
    def __init__(self, input_size,expert_size):
        super(Gate, self).__init__()
        self.fc1 = nn.Linear(input_size, expert_size)
        self.softmax = nn.Softmax(dim=1)

    def forward(self,x):
        x = self.fc1(x)
        y = self.softmax(x)
        return y

class Tower(nn.Module):
    def __init__(self, input_size, output_size, hidden_size):
        super(Tower, self).__init__()
        self.fc1 = nn.Linear(input_size, hidden_size)
        self.fc2 = nn.Linear(hidden_size, output_size)
        self.relu = nn.ReLU()
        # self.softmax = nn.Softmax(dim=1)
        self.softmax = nn.Sigmoid()

    def forward(self, x):
        x = self.fc1(x)
        x = self.relu(x)
        x = self.fc2(x)
        y = self.softmax(x)
        return y

class Model(torch.nn.Module):
    def __init__(self, config):
        super(Model, self).__init__()
        self.input_size = config.num_feature
        self.num_experts = config.num_experts
        self.num_tasks = config.num_tasks
        self.experts_out = config.units
        self.experts_hidden = config.expert_hidden
        self.towers_hidden = config.hidden_units
        self.tasks = len(config.label_dict)
        self.softmax = nn.Softmax(dim=1)

        self.experts = nn.ModuleList([Expert(self.input_size, self.experts_out, self.experts_hidden) for i in range(self.num_experts)])
        self.gates = nn.ModuleList([Gate(self.input_size, self.num_experts) for i in range(self.num_tasks)])
        self.towers = nn.ModuleList([Tower(self.experts_out, 1, self.towers_hidden) for i in range(self.tasks)])

    def forward(self, x):
        experts_o = [e(x) for e in self.experts]
        experts_o_tensor = torch.stack(experts_o)
        gates_o = [g(x) for g in self.gates]
        towers_input = [g.t().unsqueeze(2) * experts_o_tensor for g in gates_o]
        towers_input = [torch.sum(ti, dim=0) for ti in towers_input]

        final_output = [t(ti) for t, ti in zip(self.towers, towers_input)]

        return final_output



