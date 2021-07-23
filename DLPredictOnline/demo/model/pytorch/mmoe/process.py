# coding: UTF-8
'''
model train and predict process
'''
import numpy as np
import torch
from sklearn import metrics
from util import to_categorical

objective_num = 2

def output_label_transform(outputs,labels):
    output_list = [outputs[0],outputs[1]]
    label_list = [labels[0],labels[1]]
    for i in range(len(output_list)):
        output_list[i] = output_list[i][label_list[i] != -1]
        label_list[i] = label_list[i][label_list[i] != -1]
    return output_list, label_list


def cal_auc(pred,true,is_torch=True,is_softmax=False,isbin = True):
    pred = pred[true != -1]
    true = true[true != -1]
    if is_torch:
        true = true.data.cpu().numpy()
        pred = pred.data.cpu().numpy()
    if is_softmax:
        pred = np.argmax(pred, axis=1)
    if isbin:
        true = to_categorical(true,2)
    auc = metrics.roc_auc_score(true,pred)
    return auc

def train_step(config, model, train_iter=None, dev_iter=None, test_iter=None):
    model.train()
    optimizer = torch.optim.Adam(model.parameters(), lr=config.learning_rate)

    total_batch = 0
    dev_best = float('-inf')
    last_improve = 0
    flag = False
    history_loss = [[] for i in range(objective_num)]
    for epoch in range(config.num_epochs):
        print('Epoch [{}/{}]'.format(epoch + 1, config.num_epochs))
        for i, (trains, labels) in enumerate(train_iter):
            loss = 0
            outputs = model(trains)
            output_list,label_list = output_label_transform(outputs,labels)

            model.zero_grad()

            loss_list = [config.loss_fn(output,label) for output,label in zip(output_list,label_list)]
            for i in range(len(loss_list)):
                history_loss[i].append(float(loss_list[i]))

            loss_list_use = [loss_list[0], loss_list[1]]

            loss = loss_list_use[0]+loss_list_use[1]
            loss.backward()
            optimizer.step()

            if total_batch % 1 == 0:

                train_auc_list = [round(100*cal_auc(output,label,isbin=False),2) for output,label in zip(output_list,label_list)]

                dev_loss_list,dev_auc_list = evaluate_step(config,model, dev_iter)

                dev_auc_list_use = [dev_auc_list[0], dev_auc_list[1]]
                dev_auc = 0
                for i in range(len(dev_auc_list_use)):
                    dev_auc+=dev_auc_list_use[i]
                if dev_auc > dev_best:
                    dev_best = dev_auc
                    torch.save(model.state_dict(), config.save_path)
                    improve = '*'
                    last_improve = total_batch
                else:
                    improve = ''

                print(f'Iter:{total_batch},Train AUC:{train_auc_list},Dev AUC:{dev_auc_list}{improve}')
                model.train()
            total_batch += 1
            if total_batch - last_improve > config.require_improvement:
                print("No optimization for a long time, auto-stopping...")
                flag = True
                break

        if flag:
            break

    test_step(config,model, test_iter)



def test_step(config,model, test_iter):
    model.load_state_dict(torch.load(config.save_path))
    model.eval()

    test_loss_list, test_auc_list = evaluate_step(config, model, test_iter)
    print(f'Test AUC:{test_auc_list}')


def evaluate_step(config,model, data_iter, test=False):
    model.eval()
    output_list_all = [[] for i in range(objective_num)]
    label_list_all = [[] for i in range(objective_num)]
    with torch.no_grad():
        for validates, labels in data_iter:
            outputs = model(validates)
            output_list,label_list = output_label_transform(outputs,labels)
            for i in range(len(output_list)):
                output_list_all[i].extend(output_list[i].cpu().tolist())
                label_list_all[i].extend(label_list[i].cpu().tolist())
    dev_loss_list = [config.loss_fn(torch.FloatTensor(output).to(config.device),torch.FloatTensor(label).to(config.device)) for output, label in zip(output_list_all, label_list_all)]
    dev_auc_list =[round(100 * cal_auc(np.array(output),np.array(label), is_torch = False, isbin=False), 2) for output, label in zip(output_list_all, label_list_all)]

    return dev_loss_list,dev_auc_list
