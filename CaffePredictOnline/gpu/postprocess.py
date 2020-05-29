#!/usr/bin/env python
# -*- coding: utf-8 -*-

def postprocess_result(out):
    """
    后处理结果，比如需要返回哪个层的结果
    param: out, 网络前向预测的结果
    return: 自定义需要的返回值
    """
    prob = out["prob"].copy()
    rets = []
    for p in prob:
        label = p.argsort()[::-1][0]
        score = p[label]
        rets.append((label, score))
    
    return rets


