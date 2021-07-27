'''
run mmoe train and predict process
'''

import torch
import numpy as np
from importlib import import_module
from util import build_iterator, ai_split, generate_test_set
from process import train_step
import pandas as pd

if __name__ == '__main__':
    model_name = 'mmoe_pytorch'
    x = import_module('Models.'+model_name)
    config = x.Config('/workspace/mdata/')
    np.random.seed(1)
    torch.manual_seed(1)
    torch.cuda.manual_seed_all(1)
    torch.backends.cudnn.deterministic = True

    train_set, test_set, dev_set = ai_split(config)
    train_iter = build_iterator(train_set, config)
    dev_iter = build_iterator(dev_set, config)
    test_iter = build_iterator(test_set, config)

    model = x.Model(config).to(config.device)
    print(model.parameters)

    train_step(config,model,train_iter,dev_iter,test_iter)

    test_data = generate_test_set(config)

    model.load_state_dict(torch.load(config.save_path))
    model.eval()
    test_predict = model(torch.Tensor(test_data).to(config.device))
    ctr_pred, cvr_pred = test_predict

    ctr_pred = ctr_pred.cpu().detach().numpy()
    cvr_pred = cvr_pred.cpu().detach().numpy()

    res = pd.DataFrame({'ctr': ctr_pred.flatten(), 'cvr': cvr_pred.flatten()})
    res.to_csv("/workspace/model/submission.csv", header=False, index=False)

