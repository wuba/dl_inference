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
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.ByteString;
import org.tensorflow.example.*;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Tensorflow WideAndDeep 模型示例
 * @author 58
 * 模型文件位于 demo/model/tensorflow/wideAndDeep
 */

public class TensorflowWideAndDeep {

    private static final String MODEL_NAME = "wideAndDeep-2390";
    private static final String INPUT_NAME = "inputs";
    private static final String OUTPUT_NAME = "scores";

    private enum FeatureType {
        // int 类型
        INT_TYPE,
        // float 类型
        FLOAT_TYPE,
        // string 类型
        STRING_TYPE
    }

    private Map<String, Feature> buildFeature(FeatureType featureType, String featureName, Map<String, Feature> inputFeatures, String value) {

        Feature f = null;
        if (FeatureType.INT_TYPE == featureType) {
            f = Feature.newBuilder().setInt64List(Int64List.newBuilder().addValue(Long.parseLong(value))).build();
        } else if (FeatureType.FLOAT_TYPE == featureType) {
            f = Feature.newBuilder().setFloatList(FloatList.newBuilder().addValue(Float.parseFloat(value))).build();
        } else if (FeatureType.STRING_TYPE == featureType) {
            f = Feature.newBuilder().setBytesList(BytesList.newBuilder().addValue(ByteString.copyFromUtf8(value))).build();
        }

        inputFeatures.put(featureName, f);

        return inputFeatures;
    }

    public Predict.PredictRequest getRequest(List<String> testDataArrayList){

        List<ByteString> inputStrs = testDataArrayList.stream().map(o -> {

            String[] elems = o.toString().split("\\|", -1);
            Map<String, Feature> inputFeatures = new HashMap(1000);

            buildFeature(FeatureType.INT_TYPE, "week", inputFeatures, elems[0]);
            buildFeature(FeatureType.INT_TYPE, "hour", inputFeatures, elems[1]);
            buildFeature(FeatureType.INT_TYPE, "page_id", inputFeatures, "0");
            buildFeature(FeatureType.INT_TYPE, "pos_in_page", inputFeatures, "0");
            buildFeature(FeatureType.INT_TYPE, "city_id", inputFeatures, elems[2]);
            buildFeature(FeatureType.INT_TYPE, "cate", inputFeatures, elems[3]);
            buildFeature(FeatureType.STRING_TYPE, "region", inputFeatures, elems[4]);
            buildFeature(FeatureType.STRING_TYPE, "shangquan", inputFeatures, elems[5]);
            buildFeature(FeatureType.INT_TYPE, "areaId", inputFeatures, elems[6]);
            buildFeature(FeatureType.INT_TYPE, "priceId", inputFeatures, elems[7]);
            buildFeature(FeatureType.INT_TYPE, "pAreaId", inputFeatures, elems[8]);
            buildFeature(FeatureType.INT_TYPE, "pPriceId", inputFeatures, elems[9]);

            buildFeature(FeatureType.FLOAT_TYPE, "cateCount", inputFeatures, elems[10]);
            buildFeature(FeatureType.FLOAT_TYPE, "regionCount", inputFeatures, elems[11]);
            buildFeature(FeatureType.FLOAT_TYPE, "shangquanCount", inputFeatures, elems[12]);
            buildFeature(FeatureType.FLOAT_TYPE, "areaCount", inputFeatures, elems[13]);
            buildFeature(FeatureType.FLOAT_TYPE, "priceCount", inputFeatures, elems[14]);

            buildFeature(FeatureType.FLOAT_TYPE, "uDc3", inputFeatures, elems[15]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDc7", inputFeatures, elems[16]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDc15", inputFeatures, elems[17]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDt3", inputFeatures, elems[18]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDt7", inputFeatures, elems[19]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDt15", inputFeatures, elems[20]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDm3", inputFeatures, elems[21]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDm7", inputFeatures, elems[22]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDm15", inputFeatures, elems[23]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDct3", inputFeatures, elems[24]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDct7", inputFeatures, elems[25]);
            buildFeature(FeatureType.FLOAT_TYPE, "uDct15", inputFeatures, elems[26]);

            buildFeature(FeatureType.FLOAT_TYPE, "proDc3", inputFeatures, elems[27]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDc7", inputFeatures, elems[28]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDc15", inputFeatures, elems[29]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDt3", inputFeatures, elems[30]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDt7", inputFeatures, elems[31]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDt15", inputFeatures, elems[32]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDm3", inputFeatures, elems[33]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDm7", inputFeatures, elems[34]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDm15", inputFeatures, elems[35]);
            buildFeature(FeatureType.FLOAT_TYPE, "proCtr3", inputFeatures, elems[36]);
            buildFeature(FeatureType.FLOAT_TYPE, "proCtr7", inputFeatures, elems[37]);
            buildFeature(FeatureType.FLOAT_TYPE, "proCtr15", inputFeatures, elems[38]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDct3", inputFeatures, elems[39]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDct7", inputFeatures, elems[40]);
            buildFeature(FeatureType.FLOAT_TYPE, "proDct15", inputFeatures, elems[41]);
            buildFeature(FeatureType.FLOAT_TYPE, "proCtrt3", inputFeatures, elems[42]);
            buildFeature(FeatureType.FLOAT_TYPE, "proCtrt7", inputFeatures, elems[43]);
            buildFeature(FeatureType.FLOAT_TYPE, "proCtrt15", inputFeatures, elems[44]);

            buildFeature(FeatureType.INT_TYPE, "shangquanRank", inputFeatures, elems[45]);

            buildFeature(FeatureType.FLOAT_TYPE, "xzl_l1", inputFeatures, elems[46]);
            buildFeature(FeatureType.FLOAT_TYPE, "xzl_l2", inputFeatures, elems[47]);
            buildFeature(FeatureType.FLOAT_TYPE, "xzl_l3", inputFeatures, elems[48]);
            buildFeature(FeatureType.FLOAT_TYPE, "xzl_l4", inputFeatures, elems[49]);
            buildFeature(FeatureType.FLOAT_TYPE, "xzl_l5", inputFeatures, elems[50]);
            buildFeature(FeatureType.FLOAT_TYPE, "xzl_l6", inputFeatures, elems[51]);
            buildFeature(FeatureType.FLOAT_TYPE, "xzl_l7", inputFeatures, elems[52]);
            buildFeature(FeatureType.FLOAT_TYPE, "sp_l1", inputFeatures, elems[53]);
            buildFeature(FeatureType.FLOAT_TYPE, "sp_l2", inputFeatures, elems[54]);
            buildFeature(FeatureType.FLOAT_TYPE, "sp_l3", inputFeatures, elems[55]);
            buildFeature(FeatureType.FLOAT_TYPE, "sp_l4", inputFeatures, elems[56]);
            buildFeature(FeatureType.FLOAT_TYPE, "sp_l5", inputFeatures, elems[57]);
            buildFeature(FeatureType.INT_TYPE, "sp_l6", inputFeatures, elems[58]);
            buildFeature(FeatureType.FLOAT_TYPE, "other_sp", inputFeatures, elems[59]);
            buildFeature(FeatureType.FLOAT_TYPE, "other_ax", inputFeatures, elems[60]);
            buildFeature(FeatureType.FLOAT_TYPE, "other_qj", inputFeatures, elems[61]);
            buildFeature(FeatureType.FLOAT_TYPE, "price", inputFeatures, elems[62]);
            buildFeature(FeatureType.FLOAT_TYPE, "area", inputFeatures, elems[63]);

            buildFeature(FeatureType.INT_TYPE, "sample_age", inputFeatures, "0");


            Features featuresSerializeToString = Features.newBuilder().putAllFeature(inputFeatures).build();
            ByteString inputStr = Example.newBuilder().setFeatures(featuresSerializeToString).build().toByteString();
            return inputStr;
        }).collect(Collectors.toList());

        TensorShapeProto.Builder tensorShapeBuilder = TensorShapeProto.newBuilder();

        tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(testDataArrayList.size()));
        TensorShapeProto shape = tensorShapeBuilder.build();
        TensorProto proto = TensorProto.newBuilder()
            .setDtype(DataType.DT_STRING)
            .setTensorShape(shape)
            .addAllStringVal(inputStrs)
            .build();

        // create request builder
        Predict.PredictRequest.Builder predictRequestBuilder = Predict.PredictRequest.newBuilder();
        // add model params
        Model.ModelSpec.Builder modelTensorBuilder = Model.ModelSpec.newBuilder().setName(MODEL_NAME);
        // model signature
        modelTensorBuilder.setSignatureName("serving_default");
        // add info to request builder
        predictRequestBuilder.setModelSpec(modelTensorBuilder.build());

        predictRequestBuilder.putAllInputs(ImmutableMap.of(INPUT_NAME, proto));
        return predictRequestBuilder.build();
    }

    public void printResult(Predict.PredictResponse response){
        TensorProto outputs = response.getOutputsOrDefault(OUTPUT_NAME, null);
        if (outputs == null){
            System.out.println("printResult TensorProto outputs is null");
            return;
        }
        List<Float> predict = outputs.getFloatValList();
        int step = 2;
        for (int i = 1; i < predict.size(); i = i + step) {
            System.out.println(predict.get(i));
        }
    }

    public List<String> getData(String dataFile) throws IOException {
        File f = new File(dataFile);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        List<String> dataList = new ArrayList();
        while (line != null) {
            dataList.add(line);
            line = br.readLine();
        }
        return dataList;
    }

    public static void tensorflowClient(WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub){
        String dataFile = "data.txt";
        if (CommonUtil.checkSystemIsWin()){
            dataFile = "demo\\model\\tensorflow\\wideAndDeep\\data.txt";
        }
        TensorflowWideAndDeep tensorflowWideAndDeep = new TensorflowWideAndDeep();
        List<String> dataList = null;
        try {
            dataList = tensorflowWideAndDeep.getData(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Predict.PredictRequest request = tensorflowWideAndDeep.getRequest(dataList);
        Predict.PredictResponse response = blockingStub.predict(request);
        tensorflowWideAndDeep.printResult(response);
    }
}
