package com.bj58.ailab.demo.client;

import com.google.protobuf.ByteString;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict;
import tensorflow.serving.Predict.PredictRequest;
import tensorflow.serving.Predict.PredictResponse;

/**
 * Tensorflow textcnn 模型示例
 * @author 58
 **/
public class TensorflowTextCnn {

    public static final String EOS      = "<eos>";
    public static final String PAD      = "<pad>";
    public static final String SPACE    = "<space>";
    public static final String defaultIntentLabel = "__label__unknown";

    public static final String OUTPUTS_KEY_OUTPUT = "output";
    public static final String OUTPUTS_KEY_SCORE = "score";

    private static final int seqLen = 72;

    public PredictRequest getRequest(){
        // 不同样例sentence不一样
        String sentence = "不用了谢谢";
        List<String> wordList = getWordList(sentence);
        wordList = padding(wordList, seqLen);
        List<Integer> lenList = new ArrayList<Integer>();
        lenList.add(seqLen);
        List<String> labelList = new ArrayList<String>();
        labelList.add(defaultIntentLabel);

        Charset charset = Charset.forName("UTF-8");

        TensorProto sentenceProto = getSentenceProto(wordList, charset, lenList);
        TensorProto labelProto = getLabelProto(labelList, charset);

        Predict.PredictRequest.Builder predictRequestBuilder = Predict.PredictRequest.newBuilder();
        String modelName = "textcnn-69";
        Model.ModelSpec modelSpec = Model.ModelSpec.newBuilder().setName(modelName).setSignatureName("prediction").build();
        predictRequestBuilder.setModelSpec(modelSpec);

        predictRequestBuilder.putInputs("sentence", sentenceProto);
        predictRequestBuilder.putInputs("label", labelProto);

        return predictRequestBuilder.build();
    }

    public void printResult(PredictResponse response){
        if (response == null){
            System.out.println("response is null");
            return;
        }
        Map<String, TensorProto> outputs = response.getOutputsMap();
        Map<String, TensorProto> outputsMap = response.getOutputsMap();
        if (outputsMap == null || outputsMap.isEmpty()){
            System.out.println("outputsMap is null");
            return;
        }
        if ((outputs.containsKey(OUTPUTS_KEY_OUTPUT) & outputs.containsKey(OUTPUTS_KEY_SCORE))){
            TensorProto outputTensorProto = (TensorProto)outputs.get(OUTPUTS_KEY_OUTPUT);
            List<ByteString> byteString = outputTensorProto.getStringValList();
            List<String> strString = new ArrayList();
            for (ByteString b : byteString) {
                strString.add(b.toString(Charset.forName("UTF-8")));
            }
            System.out.println(OUTPUTS_KEY_SCORE + ":");
            TensorProto scoreTensorProto = (TensorProto)outputs.get(OUTPUTS_KEY_SCORE);
            List<Float> scoreList = scoreTensorProto.getFloatValList();
            for (int i = 0; i < strString.size(); ++i) {
                System.out.println("key=" + strString.get(i) + ", value=" + scoreList.get(i).floatValue());
            }
        }else{
            System.out.println("response not containsKey" + OUTPUTS_KEY_OUTPUT + " and " + OUTPUTS_KEY_SCORE);
        }
    }

    private List<String> getWordList(String question){
        List<String> wList = new ArrayList<>();

        for(int i = 0; i < question.length(); i++)
        {
            wList.add(Character.toString(question.charAt(i)));
        }
        return wList;
    }

    private List<String> padding(List<String> question, Integer maxLen) {
        List<String> newQuestion = new ArrayList<String>();
        Integer qLen = question.size();
        for (Integer i = 0; i < maxLen; ++i) {
            if (i < qLen) {
                newQuestion.add(question.get(i));
            } else if (i.equals(qLen)) {
                newQuestion.add(EOS);
            } else {
                newQuestion.add(PAD);
            }
        }
        return newQuestion;
    }

    private TensorProto getSentenceProto(List<String> words, Charset charset, List<Integer> lens){
        TensorProto.Builder sentenceProtoBuilder = TensorProto.newBuilder();
        sentenceProtoBuilder.setDtype(DataType.DT_STRING);
        for (String w : words) {
            ByteString bytestring = ByteString.copyFrom(w, charset);
            sentenceProtoBuilder.addStringVal(bytestring);
        }

        TensorShapeProto.Builder sentenceShapeBuilder = TensorShapeProto.newBuilder();
        sentenceShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1L));
        sentenceShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize((lens.get(0)).intValue()));

        sentenceProtoBuilder.setTensorShape(sentenceShapeBuilder.build());
        return sentenceProtoBuilder.build();
    }

    private TensorProto getLabelProto(List<String> labels, Charset charset){
        TensorProto.Builder labelsProtoBuilder = TensorProto.newBuilder();
        labelsProtoBuilder.setDtype(DataType.DT_STRING);
        for (String l : labels) {
            ByteString bytestring = ByteString.copyFrom(l, charset);
            labelsProtoBuilder.addStringVal(bytestring);
        }

        TensorShapeProto.Builder labelsShapeBuilder = TensorShapeProto.newBuilder();
        labelsShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1L));

        labelsProtoBuilder.setTensorShape(labelsShapeBuilder.build());
        return labelsProtoBuilder.build();
    }
}
