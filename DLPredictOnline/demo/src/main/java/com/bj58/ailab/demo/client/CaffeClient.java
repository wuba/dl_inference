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
import sun.misc.BASE64Encoder;
import com.google.protobuf.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.bj58.ailab.demo.utils.FileUtil.readImageFile;
import static com.bj58.ailab.dlpredictonline.grpc.consts.Common.TASK_ID_KEY;

/**
 * Caffe 房屋图像分类模型示例
 * @author 58
 * 模型文件位于 demo/model/caffe
 **/
public class CaffeClient {

    private static final String TEST_DATA = "DLPredictOnline/demo/model/caffe/test_data";

    public PredictionProtos.SeldonMessage getRequest(File file) {

        File[] files = file.listFiles();
        List<Object> dataList = new ArrayList<Object>();
        for (File f : files) {
            if(f.getName().endsWith(".jpg")) {
                try {
                    byte[] src = readImageFile(f);
                    BASE64Encoder encoder = new BASE64Encoder();
                    dataList.add(encoder.encode(src));
                } catch (Exception e){
                    System.out.println("读取文件失败：" + e);
                }
            }
        }

        String data = (String)dataList.get(0);

        Map<String, Value> tagsMap = new HashMap<>(1);
        Value taskId = Value.newBuilder().setNumberValue(89).build();
        tagsMap.put(TASK_ID_KEY, taskId);

        PredictionProtos.Meta meta = PredictionProtos.Meta.newBuilder().putAllTags(tagsMap).build();
        PredictionProtos.SeldonMessage request = PredictionProtos.SeldonMessage
            .newBuilder()
            .setMeta(meta)
            .setStrData(data)
            .build();

        return request;
    }


    public void printResult(PredictionProtos.SeldonMessage response){
        if (response == null) {
            System.out.println("response is null");
        }else{
            System.out.println(response.getData());
        }
    }

    public static void client(WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub) {
        String imagePath = TEST_DATA;
        if (CommonUtil.checkSystemIsWin()){
            imagePath = imagePath.replaceAll("/","\\\\");
        }
        File dataDir = new File(imagePath);
        CaffeClient caffeClient = new CaffeClient();
        PredictionProtos.SeldonMessage request = caffeClient.getRequest(dataDir);
        PredictionProtos.SeldonMessage response = blockingStub.withDeadlineAfter(10000, TimeUnit.MILLISECONDS).caffePredict(request);
        caffeClient.printResult(response);
    }
}
