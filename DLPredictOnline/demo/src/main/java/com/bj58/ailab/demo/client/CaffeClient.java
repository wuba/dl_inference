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

import com.bj58.ailab.dlpredictonline.entity.PredictionProtos;
import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import sun.misc.BASE64Encoder;
import com.google.protobuf.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Caffe 房屋图像分类模型示例
 * @author 58
 * 模型文件位于 demo/model/caffe
 **/
public class CaffeClient {

    /**
     * 读取文件
     */
    public static byte[] readFile(String file) throws Exception {
        byte[] data;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
        } finally {
            in.close();
        }
        return data;
    }

    public PredictionProtos.SeldonMessage getRequest() {

        String picPath = "demo/src/main/resources/data";

        // 测试图片放入数据list
        File file = new File(picPath);
        File[] files = file.listFiles();
        List<Object> dataList = new ArrayList<Object>();
        for (File f : files) {
            if(f.getName().endsWith(".jpg")) {
                try {
                    byte[] src = readFile(f.getAbsolutePath());
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
        tagsMap.put("taskid", taskId);

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
        CaffeClient caffeClient = new CaffeClient();
        PredictionProtos.SeldonMessage request = caffeClient.getRequest();
        PredictionProtos.SeldonMessage response = blockingStub.withDeadlineAfter(10000, TimeUnit.MILLISECONDS).caffePredict(request);
        caffeClient.printResult(response);
    }
}
