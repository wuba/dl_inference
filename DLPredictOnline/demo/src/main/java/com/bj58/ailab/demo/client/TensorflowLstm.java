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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bj58.ailab.demo.utils.CommonUtil;
import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict.PredictRequest;
import tensorflow.serving.Predict.PredictResponse;

/**
 * Tensorflow LSTM 模型示例
 * @author 58
 * 模型文件位于 demo/model/tensorflow/lstm
 **/
public class TensorflowLstm {

    private static final String OUT_PUT_KEY = "output";

    private static final String TEST_DATA = "DLPredictOnline/demo/model/tensorflow/lstm/test_data";

    private static final String VOCAB_MAX_FILE = "DLPredictOnline/demo/model/tensorflow/lstm/vocab_max";

    public PredictRequest getRequest(ArrayList<Integer> testDataArrayList){
        String modelname = "lstm-maxclass-2365";
        Model.ModelSpec.Builder modelTensorBuilder = Model.ModelSpec.newBuilder().setName(modelname);
        String signatureName = "prediction";
        modelTensorBuilder.setSignatureName(signatureName);

        TensorProto.Builder featuresTensorProtoBuilder = TensorProto.newBuilder();
        TensorProto.Builder featuresLenTensorProtoBuilder = TensorProto.newBuilder();
        TensorProto.Builder dropoutKeepProbProtoBuilder = TensorProto.newBuilder();
        for(int j = 0; j < testDataArrayList.size(); ++j) {
            featuresTensorProtoBuilder.addInt64Val(Long.valueOf(testDataArrayList.get(j)));
        }
        featuresLenTensorProtoBuilder.addInt64Val(testDataArrayList.size());
        dropoutKeepProbProtoBuilder.addFloatVal(1.0f);
        int exampleNum = 1;
        int featureNum = testDataArrayList.size();
        int keepDim = 1;
        TensorShapeProto.Dim exampleNumDim = TensorShapeProto.Dim.newBuilder().setSize(exampleNum).build();
        TensorShapeProto.Dim featureNumDim = TensorShapeProto.Dim.newBuilder().setSize(featureNum).build();
        TensorShapeProto.Dim dropoutKeepPropDim = TensorShapeProto.Dim.newBuilder().setSize(keepDim).build();

        TensorShapeProto featuresShape = TensorShapeProto.newBuilder().addDim(exampleNumDim).addDim(featureNumDim).build();
        TensorShapeProto featuresLenShape = TensorShapeProto.newBuilder().addDim(exampleNumDim).build();
        TensorShapeProto dropoutKeepPropShape = TensorShapeProto.newBuilder().addDim(dropoutKeepPropDim).build();

        featuresTensorProtoBuilder.setDtype(DataType.DT_INT64).setTensorShape(featuresShape);
        featuresLenTensorProtoBuilder.setDtype(DataType.DT_INT64).setTensorShape(featuresLenShape);
        dropoutKeepProbProtoBuilder.setDtype(DataType.DT_FLOAT).setTensorShape(dropoutKeepPropShape);

        TensorProto featuresTensorProto = featuresTensorProtoBuilder.build();
        TensorProto featuresLenTensorProto = featuresLenTensorProtoBuilder.build();
        TensorProto dropoutKeepPropProto = dropoutKeepProbProtoBuilder.build();

        PredictRequest.Builder predictRequestBuilder = PredictRequest.newBuilder().setModelSpec(modelTensorBuilder)
            .putInputs("input", featuresTensorProto)
            .putInputs("sen_len", featuresLenTensorProto)
            .putInputs("dropout_keep_prob", dropoutKeepPropProto);
        return predictRequestBuilder.build();
    }

    public void printResult(PredictResponse response){
        if (response == null){
            System.out.println("response is null");
            return;
        }
        Map<String, TensorProto> resMap = response.getOutputsMap();
        if(!resMap.containsKey(OUT_PUT_KEY)) {
            System.out.println("not has output");
            return;
        }
        List<Float> sampleScoresList = resMap.get("output").getFloatValList();
        System.out.println(sampleScoresList);
    }

    public ArrayList<ArrayList<Integer>>  getResult(String testFile, String vocabMaxFile, int maxLen) throws IOException {
        //load vocab_max_file
        FileReader vocabFr = new FileReader(vocabMaxFile);
        BufferedReader br = new BufferedReader(vocabFr);
        String str = null;
        HashMap<String, Integer> vocabMap = new HashMap<String, Integer>(3700);
        while ((str = br.readLine()) != null) {
            String[] lines = str.split("\t");
            String word = lines[0];
            Integer id = Integer.valueOf(lines[1]);
            vocabMap.put(word, id);
        }
        br.close();
        vocabFr.close();
        ArrayList<ArrayList<Integer>> testDataArrayList = new ArrayList<ArrayList<Integer>>();
        FileReader fr = new FileReader(testFile);
        br = new BufferedReader(fr);
        str = null;
        while((str = br.readLine()) != null) {
            String[] lines = str.split("\t");
            String[] words = lines[2].split(" ");
            ArrayList<Integer> valueList = new ArrayList<Integer>();
            for(int i = 0; i < words.length && i < maxLen; ++i) {
                if (vocabMap.containsKey(words[i])) {
                    valueList.add(vocabMap.get(words[i]));
                }
            }
            int num = valueList.size();
            while(num < maxLen) {
                valueList.add(0);
                ++num;
            }
            testDataArrayList.add(valueList);
        }
        br.close();
        fr.close();
        return testDataArrayList;
    }

    public static void tensorflowClient(WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub){

        String testFile = TEST_DATA;
        String vocabMaxFile = VOCAB_MAX_FILE;
        if (CommonUtil.checkSystemIsWin()){
            testFile = testFile.replaceAll("/", "\\\\");
            vocabMaxFile = vocabMaxFile.replaceAll("/", "\\\\");
        }
        int maxLen = 40;
        TensorflowLstm tensorflowLstm = new TensorflowLstm();
        ArrayList<ArrayList<Integer>> testDataArrayList = new ArrayList<ArrayList<Integer>>();
        try {
            testDataArrayList = tensorflowLstm.getResult(testFile, vocabMaxFile, maxLen);
        } catch (Exception e){
            System.err.println("tensorflowLstm.getResult error, msg=" + e.getMessage());
        }

        TensorflowLstm lstm = new TensorflowLstm();
        long spendSum = 0;
        for(int i = 0; i < testDataArrayList.size(); ++i) {
            ArrayList<Integer> featuresList = testDataArrayList.get(i);
            PredictRequest request = lstm.getRequest(featuresList);

            long start = System.currentTimeMillis();

            PredictResponse response = blockingStub.predict(request);

            long end = System.currentTimeMillis();
            spendSum += end - start;
            lstm.printResult(response);
        }
        System.out.println("avg spend=" + (spendSum * 1.0 / testDataArrayList.size()));
    }

}