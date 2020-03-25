# -*- coding: utf-8 -*-
# Copyright (c) 2020-present, Wuba, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


import numpy as np
import torch


def preprocess(data, **kwargs):
    if isinstance(data, np.ndarray):
        data = torch.from_numpy(data)
    return data


def postprocess(data, **kwargs):
    return format_res(data)


def run_model(model, x, **kwargs):
    return model(x)


def format_res(x):
    if isinstance(x, np.ndarray):
        return x.tolist()
    elif isinstance(x, dict):
        for i in x:
            x[i] = format_res(x[i])
        return x
    elif isinstance(x, torch.Tensor):
        return x.cpu().numpy().tolist()
    elif isinstance(x, list):
        for i in range(len(x)):
            x[i] = format_res(x[i])
        return x

