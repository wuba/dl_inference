/**
 * Copyright (c) 2020-present, Wuba, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bj58.ailab.demo.client;

import com.bj58.ailab.demo.utils.CommonUtil;
import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import com.bj58.ailab.dlpredictonline.grpc.pytorch.PredictionProtos;
import com.google.protobuf.ByteString;
import com.google.protobuf.Value;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.bj58.ailab.demo.utils.FileUtil.readImageFile;
import static com.bj58.ailab.dlpredictonline.grpc.consts.Common.TASK_ID_KEY;

/**
 * PyTorch 图像数字识别模型示例
 * @author 58
 * 模型文件位于 demo/model/pytorch
 **/
public class PyTorchMnist {

    private static final String TEST_DATA = "DLPredictOnline/demo/model/pytorch/mnist/test_data";

    public PredictionProtos.SeldonMessage getRequest(File file) {
        byte[] content = new byte[0];
        try {
            content = readImageFile(file);
            System.out.println("length: " + content.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteString bsdata = ByteString.copyFrom(content);

        Map<String, Value> tagsMap = new HashMap<>(1);
        Value taskId = Value.newBuilder().setNumberValue(39).build();
        tagsMap.put(TASK_ID_KEY, taskId);
        
        PredictionProtos.Meta meta = PredictionProtos.Meta.newBuilder().putAllTags(tagsMap).build();

        PredictionProtos.SeldonMessage request = PredictionProtos.SeldonMessage
            .newBuilder()
            .setMeta(meta)
            .setBinData(bsdata).build();
        
        return request;
    }

    public void printResult(PredictionProtos.SeldonMessage response){
        if (response != null) {
            System.out.println(response.getData().getTensor().getValuesList());
        }else{
            System.out.println("response is null");
        }
    }

    public static void client(WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub){
        String imagePath = TEST_DATA;
        if (CommonUtil.checkSystemIsWin()){
            imagePath = imagePath.replaceAll("/","\\\\");
        }
        PyTorchMnist pyTorchMnist = new PyTorchMnist();
        File dataDir = new File(imagePath);
        for (File file : dataDir.listFiles()) {
            PredictionProtos.SeldonMessage request = pyTorchMnist.getRequest(file);
            PredictionProtos.SeldonMessage response = blockingStub.withDeadlineAfter(10000, TimeUnit.MILLISECONDS).pytorchPredict(request);
            pyTorchMnist.printResult(response);
        }
    }
    
}
