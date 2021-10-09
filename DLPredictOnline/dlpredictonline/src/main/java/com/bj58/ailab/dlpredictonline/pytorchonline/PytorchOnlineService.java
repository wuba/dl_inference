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


package com.bj58.ailab.dlpredictonline.pytorchonline;

import com.bj58.ailab.dlpredictonline.config.Config;
import com.bj58.ailab.dlpredictonline.entity.BlockingStubEntity;
import com.bj58.ailab.dlpredictonline.grpc.pytorch.PredictionProtos.SeldonMessage;
import com.bj58.ailab.dlpredictonline.grpc.pytorch.ModelGrpc;
import com.bj58.ailab.dlpredictonline.grpc.pytorch.ModelGrpc.ModelBlockingStub;
import com.bj58.ailab.dlpredictonline.tensorflowserving.TensorflowServingBlockingStub;
import com.bj58.ailab.dlpredictonline.util.MachineUtil;
import io.grpc.ManagedChannel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * pytorch深度学习框架
 * @author 58
 */
public class PytorchOnlineService {

    private static Logger logger = LoggerFactory.getLogger(PytorchOnlineService.class);

    public static SeldonMessage predict(int taskId, SeldonMessage request) {
        if (taskId <= 0) {
            logger.info("ip={}, taskid={}, predict taskid invalid", MachineUtil.getHostIp(), taskId);
            return null;
        }
        if (request == null) {
            logger.info("ip={}, taskid={}, predict request is null", MachineUtil.getHostIp(), taskId);
            return null;
        }
        int tryNum = 0, retry = Config.DEFAULT_RETRY_NUM;
        while (tryNum++ < retry) {
            BlockingStubEntity blockingStubEntity = TensorflowServingBlockingStub.getBlockingStubByRandom(taskId);
            if (blockingStubEntity == null) {
                logger.info("ip={}, taskid={}, predict blockingStubEntity is null", MachineUtil.getHostIp(), taskId);
                return null;
            }
            ManagedChannel channel = blockingStubEntity.getChannel();
            ModelBlockingStub blockingStub = ModelGrpc.newBlockingStub(channel);
            int stubTimeout = TensorflowServingBlockingStub.getTimeoutByTaskid(taskId + "");
            try {
                long beginTime = System.currentTimeMillis();
                SeldonMessage response = blockingStub.withDeadlineAfter(stubTimeout, TimeUnit.MILLISECONDS).predict(request);
                long endTime = System.currentTimeMillis();
                TensorflowServingBlockingStub.updateWeight(blockingStubEntity, true);
                return response;
            } catch (Exception e) {
                TensorflowServingBlockingStub.updateWeight(blockingStubEntity, false);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                logger.error("ip={}, taskid={}, predict request pytorch-online error, msg={}, exception={}",
                        MachineUtil.getHostIp(), taskId, e.getMessage(), sw.toString().replaceAll("\n", " "));
                pw.close();
            }
        }
        return null;
    }
}
