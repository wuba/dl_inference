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

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.text.MessageFormat;

import com.bj58.ailab.demo.utils.CommonUtil;
import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict;
import tensorflow.serving.Predict.PredictRequest;
import tensorflow.serving.Predict.PredictResponse;

/**
 * Tensorflow sptm 模型示例
 * @author 58
 * 模型文件位于 demo/model/tensorflow/sptm
 **/
public class TensorflowSptm {
    /**
     * 数据文件
     */
    public static String DATA_FILE_NAME = "test.txt";
    /**
     * 词典文件
     */
    private static String VOCAB_FILE_NAME = "vocab";
    private static final String OUTPUTS_KEY_OUTPUT = "labels";
    private static final String OUTPUTS_KEY_SCORE = "probs";
    /**
     * 加载完成的词典
     */
    private Map<String, Integer> vocab;

    /**
     * 取预测结果的前TOP_K个输出
     */
    private static final Integer TOP_K = 3;
    /**
     * 最大句子长度
     */
    private static final Integer MAX_LEN = 100;
    private static final Charset CHARSET = Charset.forName("utf-8");

    private int inputShape0 = 1;
    private int inputShape1 = MAX_LEN;
    private int batchShape = 1;

    public TensorflowSptm() {
        if (CommonUtil.checkSystemIsWin()){
            DATA_FILE_NAME = "demo\\model\\tensorflow\\sptm\\test.txt";
            VOCAB_FILE_NAME = "demo\\model\\tensorflow\\sptm\\vocab";
        }
        try {
            vocab = loadVocab(VOCAB_FILE_NAME);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public PredictRequest getRequest(String sentence){
        // 不同样例sentence不一样
        final String modelName = "lstm-2389";
        final String signatureName = "predict";

        List<Integer> wordIds = new ArrayList<Integer>(MAX_LEN);
        int realLen = 0;
        String[] words = sentence.trim().split(" ");
        for(String word: words) {
            Integer id = vocab.get(word);
            if(id == null) {
                id = vocab.get("<UNK>");
            }
            wordIds.add(id);
            if(++realLen >= MAX_LEN) {
                break;
            }
        }

        // 1.初始化各个PlaceHolder
        // 句子实际长度
        List<Integer> sentLens = new ArrayList<Integer>();
        sentLens.add(realLen);
        // dropout为0
        List<Float> dropouts = new ArrayList<>();
        dropouts.add(0.0f);
        // 2.将数据统一转换为TensorProto格式
        TensorProto topkProto = getTopkProto(TOP_K);
        TensorProto inputsProto = getInputsProto(wordIds);
        TensorProto lenProto = getLenProto(sentLens);
        TensorProto dropoutProto = getDropoutProto(dropouts);
        // 3.使用在保存PB文件时的函数名和参数名对数据进行封装
        PredictRequest.Builder predictRequestBuilder = PredictRequest.newBuilder();
        Model.ModelSpec modelSpec = Model.ModelSpec.newBuilder().setName(modelName).setSignatureName(signatureName).build();
        predictRequestBuilder.setModelSpec(modelSpec);
        predictRequestBuilder.putInputs("top_k", topkProto);
        predictRequestBuilder.putInputs("dropout_rate", dropoutProto);
        predictRequestBuilder.putInputs("input", inputsProto);
        predictRequestBuilder.putInputs("length", lenProto);

        return predictRequestBuilder.build();
    }

    public void printResult(PredictResponse response){
        if (response == null){
            System.out.println("response is null");
            return;
        }
        Map<String, TensorProto> outputs = response.getOutputsMap();
        if (outputs == null || outputs.isEmpty()){
            System.out.println("outputsMap is null");
            return;
        }
        if ((outputs.containsKey(OUTPUTS_KEY_OUTPUT) & outputs.containsKey(OUTPUTS_KEY_SCORE))){
            // 1. 使用在保存PB文件时的变量名获取模型的输出，即类标
            TensorProto labelsTensorProto = outputs.get(OUTPUTS_KEY_OUTPUT);
            List<Integer> labelsVals = labelsTensorProto.getIntValList();

            // 2. 使用在保存PB文件时的变量名获取模型的输出，即评分
            TensorProto probsTensorProto = outputs.get(OUTPUTS_KEY_SCORE);
            List<Float> probsVals = probsTensorProto.getFloatValList();

            // 3. 转换为java的map格式，打印出来
            for(int i = 0; i < labelsVals.size(); i++) {
                System.out.println("key=" + labelsVals.get(i) + ", value=" + probsVals.get(i).floatValue());
            }
            System.out.println();
        }else{
            System.out.println("response not containsKey" + OUTPUTS_KEY_OUTPUT + " and " + OUTPUTS_KEY_SCORE);
        }
    }

    private static boolean isFile(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    private Map<String, Integer> loadVocab(String vocabPath) throws IllegalAccessException {
        if (!isFile(vocabPath)) {
            throw new IllegalAccessException(MessageFormat.format("vocab path [{0}] is not legal!", vocabPath));
        }
        BufferedReader br = null;
        Map<String, Integer> word2id = new HashMap<String, Integer>(3700);
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                vocabPath), CHARSET.name()));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] kvs = line.trim().split(" ");
                if(kvs.length != 2) {
                    continue;
                }
                word2id.put(kvs[0], Integer.parseInt(kvs[1]));
            }
            System.out.println(String.format("Loading %d items from mapping.", word2id.size()));
        } catch (Exception e) {
            System.out.println("exception when reading vocab: " + e.getMessage());
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("exception when closing reader: " + e.getMessage());
                }
            }
        }
        System.out.println(MessageFormat.format("loading vocab success {0}", word2id.size()));
        return word2id;
    }

    public List<String> loadSentences(String dataPath) throws IllegalAccessException {
        if (!isFile(dataPath)) {
            throw new IllegalAccessException(MessageFormat.format("dataPath path [{0}] is not legal!", dataPath));
        }
        BufferedReader br = null;
        List<String> res = new ArrayList<>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                dataPath), CHARSET.name()));
            String line = null;
            while((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                res.add(line.trim());
            }
            System.out.println(String.format("Loading %d items from sentence.", res.size()));
        } catch (Exception e) {
            System.out.println("exception when reading sentence: " + e.getMessage());
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("exception when closing reader: " + e.getMessage());
                }
            }
        }
        System.out.println(MessageFormat.format("loading sentence success {0}", res.size()));
        return res;
    }

    private TensorProto getTopkProto(Integer topK) {
        // length builder content
        TensorProto.Builder lenProtoBuilder = TensorProto.newBuilder();
        lenProtoBuilder.setDtype(DataType.DT_INT32);
        lenProtoBuilder.addIntVal(topK);

        // length builder length
        TensorShapeProto.Builder lenShapeBuilder = TensorShapeProto.newBuilder();

        lenProtoBuilder.setTensorShape(lenShapeBuilder.build());
        return lenProtoBuilder.build();
    }

    private TensorProto getInputsProto(List<Integer> inputs) {
        // sentence builder content
        TensorProto.Builder sentenceProtoBuilder = TensorProto.newBuilder();
        sentenceProtoBuilder.setDtype(DataType.DT_INT32);
        sentenceProtoBuilder.addAllIntVal(inputs);

        // sentence builder shape
        TensorShapeProto.Builder sentenceShapeBuilder = TensorShapeProto.newBuilder();
        sentenceShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(inputShape0));
        sentenceShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(inputShape1));

        sentenceProtoBuilder.setTensorShape(sentenceShapeBuilder.build());
        return sentenceProtoBuilder.build();
    }

    private TensorProto getLenProto(List<Integer> lens) {
        // length builder content
        TensorProto.Builder lenProtoBuilder = TensorProto.newBuilder();
        lenProtoBuilder.setDtype(DataType.DT_INT32);
        for (Integer i: lens) {
            lenProtoBuilder.addIntVal(i);
        }

        // length builder length
        TensorShapeProto.Builder lenShapeBuilder = TensorShapeProto.newBuilder();
        lenShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(batchShape));

        lenProtoBuilder.setTensorShape(lenShapeBuilder.build());
        return lenProtoBuilder.build();
    }

    private TensorProto getDropoutProto(List<Float> dropouts) {
        // length builder content
        TensorProto.Builder lenProtoBuilder = TensorProto.newBuilder();
        lenProtoBuilder.setDtype(DataType.DT_FLOAT);
        for (Float i: dropouts) {
            lenProtoBuilder.addFloatVal(i);
        }

        // length builder length
        TensorShapeProto.Builder lenShapeBuilder = TensorShapeProto.newBuilder();
        lenShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(batchShape));

        lenProtoBuilder.setTensorShape(lenShapeBuilder.build());
        return lenProtoBuilder.build();
    }

    public static void tensorflowClient(WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub){
        TensorflowSptm tensorflowSptm = new TensorflowSptm();
        List<String> sentences = null;
        try {
            sentences = tensorflowSptm.loadSentences(DATA_FILE_NAME);
        } catch (Exception e) {
            System.err.println("load sentence error, msg=" + e.getMessage());
            return;
        }
        long spendSum = 0;
        for (String sentence : sentences) {
            PredictRequest request = tensorflowSptm.getRequest(sentence);
            Predict.PredictResponse response = null;
            try {
                long start = System.currentTimeMillis();
                response = blockingStub.predict(request);

                long end = System.currentTimeMillis();
                spendSum += end - start;

                tensorflowSptm.printResult(response);
            } catch (Exception e) {
                System.err.println("blockingStub.predict error, msg=" + e.getMessage()) ;
            }
        }
        System.out.println("avg spend=" + (spendSum * 1.0 / sentences.size()));
    }
}
