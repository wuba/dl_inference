/**
 * Copyright (c) 2020-present, Wuba, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.bj58.ailab.demo.client;

import com.bj58.ailab.demo.utils.CommonUtil;
import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict.PredictRequest;
import tensorflow.serving.Predict.PredictResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Tensorflow sptm 模型示例
 * @author 58
 * 模型文件位于 demo/model/tensorflow/sptm
 **/
public class TensorflowResnet50 {
    private final static String modelName = "resnet50-345";
    private final static String signatureName = "a_signature";
    private final static String OUTPUTS_KEY_OUTPUT = "outputs";
    private final static String TEST_DATA = "/DLPredictOnline/demo/model/tensorflow/resnet50/test_data";


    public static void tensorflowClient(WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub) {
        TensorflowResnet50 tensorflowSptm = new TensorflowResnet50();

        long spendSum = 0;
        PredictRequest request = tensorflowSptm.getRequest();
        PredictResponse response = null;
        try {
            long start = System.currentTimeMillis();
            response = blockingStub.predict(request);
            long end = System.currentTimeMillis();
            spendSum += end - start;
            System.out.println("Spend "+spendSum+" ms.");
            tensorflowSptm.printResult(response);
        } catch (Exception e) {
            System.err.println("blockingStub.predict error, msg=" + e.getMessage());
        }
    }

    public PredictRequest getRequest() {

        TensorProto inputProto = getImagekProto();
        PredictRequest.Builder predictRequestBuilder = PredictRequest.newBuilder();
        Model.ModelSpec modelSpec = Model.ModelSpec.newBuilder().setName(modelName).setSignatureName(signatureName).build();
        predictRequestBuilder.setModelSpec(modelSpec);
        predictRequestBuilder.putInputs("inputs", inputProto);

        return predictRequestBuilder.build();
    }

    private TensorProto getImagekProto() {

        List inputs = loadData();

        TensorShapeProto.Builder tensorShapeBuilder = TensorShapeProto.newBuilder();
        tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1L));
        tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(224));
        tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(224));
        tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(3));
        TensorProto.Builder tensorProtoBuilder = TensorProto.newBuilder();
        tensorProtoBuilder.setDtype(DataType.DT_FLOAT);
        tensorProtoBuilder.setTensorShape(tensorShapeBuilder.build());
        tensorProtoBuilder.addAllFloatVal(inputs);
        return tensorProtoBuilder.build();
    }

    private List loadData() {
        List data = new ArrayList();
        String testFile = TEST_DATA;
        if (CommonUtil.checkSystemIsWin()){
            testFile.replaceAll("/", "\\\\");
        }
        File file = new File(testFile);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                String[] v = tempString.split(",");
                for (int i = 0; i < v.length; i++) {
                    data.add(Float.parseFloat(v[i]));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return data;
    }

    public void printResult(PredictResponse response) {
        if (response == null) {
            System.out.println("response is null");
            return;
        }
        Map<String, TensorProto> outputs = response.getOutputsMap();
        if (outputs == null || outputs.isEmpty()) {
            System.out.println("outputsMap is null");
            return;
        }
        if (outputs.containsKey(OUTPUTS_KEY_OUTPUT)) {
            TensorProto tensorProto = outputs.get(OUTPUTS_KEY_OUTPUT);
            List floats = tensorProto.getFloatValList();
            for (int j = 0; j < floats.size(); j++) {
                System.out.println(floats.get(j));
            }

        } else {
            System.out.println("response not containsKey");
        }
    }
}
