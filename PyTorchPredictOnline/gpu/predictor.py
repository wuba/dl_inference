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


import os
import torch
import numpy as np
import traceback
from processor import *


class predictor:
    def __init__(self):
        self.class_names = ["class:{}".format(str(i)) for i in range(10)]
        self.model = torch.load('model.pth', map_location="cuda")
        self.device = torch.device("cuda")
        self.model.to(self.device)
        self.model.eval()
    
    @torch.no_grad()
    def predict(self, x, feature_names, **kwargs):
        try:
            feature_names = feature_names
            x = preprocess(x, **kwargs)
            run_exists = ('run_model' in locals() or 'run_model' in globals()) and isinstance(run_model, FunctionType)
            if run_exists:
                result = run_model(model, x, **kwargs)
            elif "tags" in kwargs["meta"] and len(kwargs["meta"]["tags"]) > 1:
                y = x.to(self.device)
                result = self.model(y, **(kwargs["meta"]["tags"]))
            else:
                y = x.to(self.device)
                result = self.model(y)
            result = postprocess(result, **kwargs)
            return result
        except Exception as e:
            errf = open("error.txt","w")   
            errf.write("there is an error: " + traceback.format_exc())

