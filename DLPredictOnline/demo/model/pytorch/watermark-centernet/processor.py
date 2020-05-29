import numpy as np
import cv2
import torch
import torch.nn as nn
from myutils.image import get_affine_transform
from models.utils import _gather_feat

input_h = 512
input_w = 512
mean = [0.408, 0.447, 0.470]
std = [0.289, 0.274, 0.278]
down_ratio = 4
scale = 1
num_classes = 22
max_per_image = 100


def pre_process(image, scale, meta=None):
    height, width = image.shape[0:2]
    new_height = int(height * scale)
    new_width = int(width * scale)

    inp_height, inp_width = input_h, input_w
    c = np.array([new_width / 2., new_height / 2.], dtype=np.float32)
    s = max(height, width) * 1.0

    trans_input = get_affine_transform(c, s, 0, [inp_width, inp_height])
    resized_image = cv2.resize(image, (new_width, new_height))

    inp_image = cv2.warpAffine(
        resized_image, trans_input, (inp_width, inp_height),
        flags=cv2.INTER_LINEAR)
    inp_image = ((inp_image / 255. - mean) / std).astype(np.float32)
    images = inp_image.transpose(2, 0, 1).reshape(1, 3, inp_height, inp_width)
    images = torch.from_numpy(images)
    a = torch.rand(1, 3, 512, 512)
    print(images.device)
    meta = {'c': c, 's': s, 'out_height': inp_height // down_ratio, 'out_width': inp_width // down_ratio}
    return a, meta


def merge_outputs(detections):
    results = {}
    for j in range(1, num_classes + 1):
        results[j] = np.concatenate(
            [detection[j] for detection in detections], axis=0).astype(np.float32)
    scores = np.hstack(
        [results[j][:, 4] for j in range(1, num_classes + 1)])
    if len(scores) > max_per_image:
        kth = len(scores) - max_per_image
        thresh = np.partition(scores, kth)[kth]
        for j in range(1, num_classes + 1):
            keep_inds = (results[j][:, 4] >= thresh)
            results[j] = results[j][keep_inds]
    return results


def preprocess(image_bytes, **kwargs):
    image = cv2.imdecode(np.frombuffer(image_bytes, np.uint8), cv2.IMREAD_COLOR)
    height, width = image.shape[0:2]
    new_height = int(height * scale)
    new_width = int(width * scale)

    inp_height, inp_width = input_h, input_w
    c = np.array([new_width / 2., new_height / 2.], dtype=np.float32)
    s = max(height, width) * 1.0

    trans_input = get_affine_transform(c, s, 0, [inp_width, inp_height])
    resized_image = cv2.resize(image, (new_width, new_height))

    inp_image = cv2.warpAffine(
        resized_image, trans_input, (inp_width, inp_height),
        flags=cv2.INTER_LINEAR)
    inp_image = ((inp_image / 255. - mean) / std).astype(np.float32)
    images = inp_image.transpose(2, 0, 1).reshape(1, 3, inp_height, inp_width)
    images = torch.from_numpy(images)
    print(images.device)
    meta = {'c': c, 's': s, 'out_height': inp_height // down_ratio, 'out_width': inp_width // down_ratio}
    return images


def _nms(heat, kernel=3):
    pad = (kernel - 1) // 2

    hmax = nn.functional.max_pool2d(
        heat, (kernel, kernel), stride=1, padding=pad)
    keep = (hmax == heat).float()
    return heat * keep


def _topk(scores, K=40):
    batch, cat, height, width = scores.size()

    topk_scores, topk_inds = torch.topk(scores.view(batch, cat, -1), K)

    topk_inds = topk_inds % (height * width)
    topk_ys = (topk_inds / width).int().float()
    topk_xs = (topk_inds % width).int().float()

    topk_score, topk_ind = torch.topk(topk_scores.view(batch, -1), K)
    topk_clses = (topk_ind / K).int()
    topk_inds = _gather_feat(
        topk_inds.view(batch, -1, 1), topk_ind).view(batch, K)
    topk_ys = _gather_feat(topk_ys.view(batch, -1, 1), topk_ind).view(batch, K)
    topk_xs = _gather_feat(topk_xs.view(batch, -1, 1), topk_ind).view(batch, K)

    return topk_score, topk_inds, topk_clses, topk_ys, topk_xs


def postprocess(output, **kwargs):
    output = output[-1]
    heat = output['hm'].sigmoid_()
    heat = _nms(heat)
    scores, inds, clses, ys, xs = _topk(heat, K=100)
    scores = scores.cpu().detach()
    clses = clses.cpu().detach().numpy()
    res = np.concatenate((scores, clses), axis=1)
    return res