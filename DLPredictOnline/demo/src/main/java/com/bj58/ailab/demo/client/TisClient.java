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
import com.bj58.ailab.demo.utils.FileUtil;
import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import com.bj58.ailab.dlpredictonline.grpc.tis.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.bj58.ailab.demo.utils.CommonUtil.byte2float;

/**
 * tis 图像分类模型示例
 * @author 58
 * 模型文件位于 demo/model/tis
 **/
public class TisClient {

    private static Logger logger = LoggerFactory.getLogger(TisClient.class);

    private static final String OUTPUT_NAME = "dense_1/Softmax:0";
    private static final String INPUT_NAME = "input_1:0";
    private static final String MODEL_NAME = "tensorflow-888";
    private static final String VERSION = "1";
    private static final String ID = "1";

    private static final String TEST_DATA = "DLPredictOnline/demo/model/tis/test_data/test.jpg";

    public GrpcService.ModelInferRequest constructRequest(File file) throws IOException{
        //读取图片数据
        byte[] rawBytes = FileUtil.readImageFile(file);
        //图像数据预处理
        rawBytes = imagePreProcess(rawBytes);
        //推理请求参数
        GrpcService.ModelInferRequest.Builder modelRequestBuilder = GrpcService.ModelInferRequest.newBuilder();
        //推理请求参数中的输入节点信息
        GrpcService.ModelInferRequest.InferInputTensor.Builder inputTensorBuilder = GrpcService.ModelInferRequest.InferInputTensor.newBuilder();
        //推理请求参数中的输出节点信息
        GrpcService.ModelInferRequest.InferRequestedOutputTensor.Builder outputTensorOrBuilder = GrpcService.ModelInferRequest.InferRequestedOutputTensor.newBuilder();

        //输入节点信息中的输入数据
        GrpcService.InferTensorContents.Builder contents = GrpcService.InferTensorContents.newBuilder();

        for(byte ele: rawBytes){
            contents.addFp32Contents(ele);
        }

        inputTensorBuilder.setName(INPUT_NAME).
            setDatatype("FP32").
            addShape(1).
            addShape(224).
            addShape(224).
            addShape(3).
            setContents(contents.build());
        outputTensorOrBuilder.setName(OUTPUT_NAME);
        GrpcService.ModelInferRequest modelInferRequest = modelRequestBuilder.
            setModelName(MODEL_NAME).
            setModelVersion(VERSION).
            setId(ID).
            addInputs(inputTensorBuilder.build()).
            //addOutputs(outputTensorOrBuilder.build()).
            build();
        return modelInferRequest;
    }

    public void printResult(GrpcService.ModelInferResponse response){
        if (response != null) {
            String modelName = response.getModelName();
            String modelVersion = response.getModelVersion();
            String id = response.getId();
            StringBuilder result = new StringBuilder();
            List<Float> pro = null;
            List<GrpcService.ModelInferResponse.InferOutputTensor> responseList = response.getOutputsList();
            int outPutSize = responseList.size();
            for (int i = 0; i < outPutSize; i++){
                GrpcService.ModelInferResponse.InferOutputTensor outputTensor = responseList.get(i);
                byte[] raw = response.getRawOutputContentsList().get(i).toByteArray();
                String tensorName = outputTensor.getName();
                String dataType = outputTensor.getDatatype();
                List<Long> shape = outputTensor.getShapeList();
                GrpcService.InferTensorContents contents = outputTensor.getContents();
                if (OUTPUT_NAME.equalsIgnoreCase(tensorName)){
                    pro = rawContent2FloatList(raw);
                }
            }

            for(int i = 0; i < 5; i++){
                float prod = pro.get(i);
                result.append(prod).append("\n");
            }
            logger.info("tis-output ====== modelName:{}, modelVersion:{}, id:{}", modelName, modelVersion, id);
            logger.info("tis-output ====== result:{}", result.toString());
        }else{
            logger.warn("response is null");
        }
    }

    public static void client(WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub) throws IOException{
        String imagePath = TEST_DATA;
        if (CommonUtil.checkSystemIsWin()){
            imagePath = imagePath.replaceAll("/","\\\\");
        }
        TisClient tisClient = new TisClient();
        File file = new File(imagePath);
        GrpcService.ModelInferRequest request = tisClient.constructRequest(file);
        GrpcService.ModelInferResponse response = blockingStub.withDeadlineAfter(10000, TimeUnit.MILLISECONDS).tisPredict(request);
        tisClient.printResult(response);
    }

    /**
     * 图像预处理操作，可按照业务需求实现，处理后数据需要符合模型输入的shape大小
     * @param raw
     * @return
     */
    protected byte[] imagePreProcess(byte[] raw){
        raw = new byte[3*256*256];
        return raw;
    }

    /**
     * 字节数组转换为浮点型数组
     * @param raw
     * @return
     */
    private List<Float> rawContent2FloatList(byte[] raw){
        if (raw == null || raw.length == 0 || (raw.length % 4) != 0){
            throw new IllegalStateException("output data exception");
        }
        List<Float> pro = new ArrayList<>();
        for (int i = 0; i < raw.length; i += 4){
            pro.add(byte2float(raw, i));
        }
        return pro;
    }

}

