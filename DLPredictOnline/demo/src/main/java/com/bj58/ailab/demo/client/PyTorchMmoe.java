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

import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import com.bj58.ailab.dlpredictonline.grpc.consts.Common;
import com.bj58.ailab.dlpredictonline.grpc.pytorch.PredictionProtos;
import com.google.protobuf.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * PyTorch MMoE模型
 * @author 58
 * 模型文件位于 demo/model/pytorch/mmoe
 **/
public class PyTorchMmoe {
    /**
     * 读取图片
     */
    public static List<Double> double2list(double[] data){
        List<Double> list = new ArrayList<Double>();
        for (double datum : data) {
            list.add(datum);
        }

        String maxmin = "2053.0:1.0,1.0:0.0,23.0:0.0,9630403162665740.0:-1.0,99993679115306.0:4207568451.0,-1.0:-1.0,8.0:-1.0,999.0:-1.0,9.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,1.0:0.0,9.0:-1.0,3887.0:0.0,1888.0:0.0,25.0:2.0,1.0:0.0,1.0:0.0,119839.0:-1.0,11856.0:580.0,13221.0:-1.0,33053.0:-1.0,35959.0:0.0,4529.0:0.0,1.0:0.0,3.0:0.0,1.0:0.0,3.0:0.0,6.0:-1.0,9.0:-1.0,8.0:-1.0,1.0:-1.0,4.0:-1.0,1.0:-1.0,1.0:-1.0,24.0:-1.0,4.0:-1.0,21.0:-1.0,1.0:-1.0,25.0:-1.0,13.0:-1.0,40.0:-1.0,1.0:-1.0,1.0:-1.0,1.0:-1.0,1.0:-1.0,4318.0:-1.0,3821.0:-1.0,100.0:-1.0,14888.0:0.0,124.0:0.0,2291.0:0.0";
        String[] maxminArray = maxmin.split(",");
        Map<Integer,Double> maxMap = new HashMap<Integer, Double>();
        Map<Integer,Double> minMap = new HashMap<Integer, Double>();
        for(int i = 0;i<maxminArray.length;i++) {
            String[] curMinMaxArray = maxminArray[i].split(":");
            maxMap.put(i, Double.parseDouble(curMinMaxArray[0]));
            minMap.put(i, Double.parseDouble(curMinMaxArray[1]));
        }
        list = normlization(list, maxMap, minMap);
        return list;
    }

    public static List<Double> normlization(List<Double> data, Map<Integer,Double> maxMap, Map<Integer,Double> minMap){
        List<Double> list = new ArrayList<Double>();
        for(int i = 0; i < data.size(); ++i){
            Double num = data.get(i);
            if(maxMap.get(i).equals(minMap.get(i))){
                list.add(0.0);
            }
            else{
                num = (num - minMap.get(i)) / (maxMap.get(i) - minMap.get(i));
                if(num > 1.0){
                    num = 1.0;
                }
                else if(num < 0.0){
                    num = 0.0;
                }
                list.add(num);
            }
        }
        return list;
    }
    public PredictionProtos.SeldonMessage getRequest(double[] input) {
        PredictionProtos.Tensor.Builder tensorBuilder = PredictionProtos.Tensor.newBuilder();

        int featureShape = 62;
        List<Integer> shape = new ArrayList<Integer>();
        List<Double> data = new ArrayList<Double>();

        int batch = 1;
        data.addAll(double2list(input));

        shape.add(batch);
        shape.add(featureShape);

        tensorBuilder.addAllShape(shape);
        tensorBuilder.addAllValues(data);

        PredictionProtos.Tensor tensor = tensorBuilder.build();
        PredictionProtos.DefaultData.Builder dataBuilder = PredictionProtos.DefaultData.newBuilder();
        dataBuilder.setTensor(tensor);
        PredictionProtos.DefaultData defaultData = dataBuilder.build();

        Map<String, Value> tagsMap = new HashMap<>(1);
        Value taskId = Value.newBuilder().setNumberValue(39).build();
        tagsMap.put(Common.TASK_ID_KEY, taskId);

        PredictionProtos.Meta meta = PredictionProtos.Meta.newBuilder().putAllTags(tagsMap).build();

        PredictionProtos.SeldonMessage request = PredictionProtos.SeldonMessage
            .newBuilder()
            .setMeta(meta)
            .setData(defaultData).build();

        return request;
    }

    public void printResult(PredictionProtos.SeldonMessage response){
        if (response != null) {
            System.out.println(response);
        }else{
            System.out.println("response is null");
        }
    }

    public static void client(WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub){
        double[] input = {2.0,1.0,11.0,-1.0,54588001812481.0,-1.0,1.0,20.0,6.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,1.0,29.0,20.0,8.0,0.0,0.0,-1.0,622.0,4038.0,-1.0,5901.0,497.0,0.234131,0.080856,0.277037,0.084918,4.0,6.0,4.0,0.0,0.0,0.0,0.0,4.0,1.0,1.0,0.0,3.0,0.0,1.0,0.0,0.0,0.0,0.0,199.0,199.0,76.0,0.0,0.0,0.0};

        PyTorchMmoe pyTorchMmoe = new PyTorchMmoe();
        PredictionProtos.SeldonMessage request = pyTorchMmoe.getRequest(input);
        System.out.println(request);
        PredictionProtos.SeldonMessage response = blockingStub.withDeadlineAfter(10000, TimeUnit.MILLISECONDS).pytorchPredict(request);
        pyTorchMmoe.printResult(response);
    }
    
}
