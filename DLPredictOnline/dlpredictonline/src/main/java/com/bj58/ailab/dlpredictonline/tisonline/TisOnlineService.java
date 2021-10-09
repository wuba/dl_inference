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

package com.bj58.ailab.dlpredictonline.tisonline;

import com.bj58.ailab.dlpredictonline.config.Config;
import com.bj58.ailab.dlpredictonline.entity.BlockingStubEntity;
import com.bj58.ailab.dlpredictonline.tensorflowserving.TensorflowServingBlockingStub;
import com.bj58.ailab.dlpredictonline.grpc.tis.GRPCInferenceServiceGrpc;
import com.bj58.ailab.dlpredictonline.util.MachineUtil;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bj58.ailab.dlpredictonline.grpc.tis.GrpcService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

/**
 * tis深度学习框架
 * @author 58
 */
public class TisOnlineService {

    private static Logger logger = LoggerFactory.getLogger(TisOnlineService.class);

    public static GrpcService.ModelInferResponse predict(int taskId, GrpcService.ModelInferRequest request) {
        if (taskId <= 0) {
            logger.info("ip={}, taskId={}, predict taskId invalid", MachineUtil.getHostIp(), taskId);
            return null;
        }
        if (request == null) {
            logger.info("ip={}, taskId={}, predict request is null", MachineUtil.getHostIp(), taskId);
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
            GRPCInferenceServiceGrpc.GRPCInferenceServiceBlockingStub blockingStub = GRPCInferenceServiceGrpc.newBlockingStub(channel);
            int stubTimeout = TensorflowServingBlockingStub.getTimeoutByTaskid(String.valueOf(taskId));
            try {
                long beginTime = System.currentTimeMillis();
                GrpcService.ModelInferResponse response = blockingStub.withDeadlineAfter(stubTimeout, TimeUnit.MILLISECONDS).modelInfer(request);
                long endTime = System.currentTimeMillis();
                TensorflowServingBlockingStub.updateWeight(blockingStubEntity, true);
                int spend = (int)(endTime - beginTime);
                logger.info("ip={}, pod ip={}, taskId={}, predict request tis-online spend {} ms",
                    MachineUtil.getHostIp(), blockingStubEntity.getIp(), taskId, spend);
                return response;
            } catch (Exception e) {
                TensorflowServingBlockingStub.updateWeight(blockingStubEntity, false);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                logger.error("ip={}, taskId={}, predict request tis-online error, msg={}, exception={}",
                    MachineUtil.getHostIp(), taskId, e.getMessage(), sw.toString().replaceAll("\n", " "));
                pw.close();
            }
        }
        return null;
    }
}
