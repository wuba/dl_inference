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


package com.bj58.ailab.dlpredictonline.tensorflowserving;

import com.bj58.ailab.dlpredictonline.config.Config;
import com.bj58.ailab.dlpredictonline.entity.BlockingStubEntity;
import com.bj58.ailab.dlpredictonline.util.MachineUtil;
import io.grpc.ManagedChannel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tensorflow.serving.Predict.PredictRequest;
import tensorflow.serving.Predict.PredictResponse;
import tensorflow.serving.PredictionServiceGrpc;
import tensorflow.serving.PredictionServiceGrpc.PredictionServiceBlockingStub;

/**
 * tensorflow模型请求处理
 * @author 58
 */
public class TensorflowServingService {

    private static Logger logger = LoggerFactory.getLogger(TensorflowServingService.class);

    public static PredictResponse predict(int taskId, PredictRequest request) {
        if (taskId <= 0){
            logger.info("ip={}, taskid={}, predict taskid invalid", MachineUtil.getHostIp(), taskId);
            return null;
        }
        if (request == null){
            logger.info("ip={}, taskid={}, predict request is null", MachineUtil.getHostIp(), taskId);
            return null;
        }
        int tryNum = 1, retry = Config.DEFAULT_RETRY_NUM;
        while(tryNum++ < retry){
            BlockingStubEntity blockingStubEntity = TensorflowServingBlockingStub.getBlockingStubByRandom(taskId);
            if (blockingStubEntity == null){
                logger.info("ip={}, taskid={}, predict blockingStubEntity is null", MachineUtil.getHostIp(), taskId);
                return null;
            }
            ManagedChannel channel = blockingStubEntity.getChannel();
            PredictionServiceBlockingStub blockingStub = PredictionServiceGrpc.newBlockingStub(channel);
            int stubTimeout = TensorflowServingBlockingStub.getTimeoutByTaskid(taskId + "");

            try{
                long beginTime = System.currentTimeMillis();
                PredictResponse response = blockingStub.withDeadlineAfter(stubTimeout, TimeUnit.MILLISECONDS).predict(request);
                long endTime = System.currentTimeMillis();
                int spend = (int)(endTime - beginTime);
                TensorflowServingBlockingStub.updateWeight(blockingStubEntity, true);
                logger.info("ip={}, pod ip={}, taskid={}, predict request tensorflow-serving spend {} ms",
                    MachineUtil.getHostIp(), blockingStubEntity.getIp(), taskId, spend);
                return response;
            }
            catch (Exception e){
                TensorflowServingBlockingStub.updateWeight(blockingStubEntity, false);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                logger.error("ip={}, taskid={}, predict request tensorflow-serving error, msg={}, exception={}",
                    MachineUtil.getHostIp(), taskId, e.getMessage(), sw.toString());

            }
        }
        return null;
    }

}
