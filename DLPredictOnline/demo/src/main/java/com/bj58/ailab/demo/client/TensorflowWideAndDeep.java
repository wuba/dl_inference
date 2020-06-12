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

import java.io.*;
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

        List<ByteString> inputStrs =
            testDataArrayList.stream()
                .map(
                    o -> {
                        String[] elems = o.toString().split(",", -1);
                        Map<String, Feature> inputFeatures = new HashMap(1000);
                        buildFeature(FeatureType.FLOAT_TYPE, "age", inputFeatures, elems[0]);
                        buildFeature(FeatureType.STRING_TYPE, "workclass", inputFeatures, elems[1]);
                        buildFeature(FeatureType.FLOAT_TYPE, "fnlwgt", inputFeatures, elems[2]);
                        buildFeature(FeatureType.STRING_TYPE, "education", inputFeatures, elems[3]);
                        buildFeature(FeatureType.FLOAT_TYPE, "education_num", inputFeatures, elems[4]);
                        buildFeature(FeatureType.STRING_TYPE, "marital_status", inputFeatures, elems[5]);
                        buildFeature(FeatureType.STRING_TYPE, "occupation", inputFeatures, elems[6]);
                        buildFeature(FeatureType.STRING_TYPE, "relationship", inputFeatures, elems[7]);
                        buildFeature(FeatureType.STRING_TYPE, "race", inputFeatures, elems[8]);
                        buildFeature(FeatureType.STRING_TYPE, "gender", inputFeatures, elems[9]);

                        buildFeature(FeatureType.FLOAT_TYPE, "capital_gain", inputFeatures, elems[10]);
                        buildFeature(FeatureType.FLOAT_TYPE, "capital_loss", inputFeatures, elems[11]);
                        buildFeature(FeatureType.FLOAT_TYPE, "hours_per_week", inputFeatures, elems[12]);
                        buildFeature(FeatureType.STRING_TYPE, "native_country", inputFeatures, elems[13]);

                        Features featuresSerializeToString =
                            Features.newBuilder().putAllFeature(inputFeatures).build();
                        ByteString inputStr =
                            Example.newBuilder()
                                .setFeatures(featuresSerializeToString)
                                .build()
                                .toByteString();
                        return inputStr;
                    })
                .collect(Collectors.toList());

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
        for (int i = 0; i < predict.size(); i = i + step) {
            System.out.println(predict.get(i) + "," + predict.get(i + 1));
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
        String dataFile = "census_input.csv";
        if (CommonUtil.checkSystemIsWin()){
            dataFile = "demo\\model\\tensorflow\\wideAndDeep\\census_input.csv";
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
