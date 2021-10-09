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


package com.bj58.ailab.dlpredictonline.components;

import com.bj58.ailab.dlpredictonline.grpc.pytorch.PredictionProtos;
import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import com.bj58.ailab.dlpredictonline.init.WpaiPredictOnlineInit;
import com.bj58.ailab.dlpredictonline.pytorchonline.PytorchOnlineService;
import com.bj58.ailab.dlpredictonline.tensorflowserving.TensorflowServingService;
import com.bj58.ailab.dlpredictonline.config.Configurations;
import com.bj58.ailab.dlpredictonline.grpc.tis.GrpcService;
import com.bj58.ailab.dlpredictonline.tisonline.TisOnlineService;
import com.bj58.ailab.dlpredictonline.util.CommonUtils;
import com.google.protobuf.Value;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tensorflow.serving.Predict;

import java.io.IOException;

import static com.bj58.ailab.dlpredictonline.grpc.consts.Common.TASK_ID_KEY;

/**
 * @Description GRPC服务端
 * @author 58
 **/
public class WPAIDLPredictOnlineGRPCService {

    private static Logger logger = LoggerFactory.getLogger(WPAIDLPredictOnlineGRPCService.class);
    private Server server;
    public void start(int port) throws IOException {

        int messageSize = Configurations.getProperty("predict.grpc.maxMessageSize", 1024 * 1024 * 10, false);
        /* The port on which the server should run */
        server = ServerBuilder.forPort(port)
            // 限制服务最大接收 10M 数据
            .maxInboundMessageSize(messageSize)
            .addService(new WPAIDLPredictOnlineGRPCServiceImpl())
            .build()
            .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                logger.error("*** shutting down gRPC server since JVM is shutting down");
                WPAIDLPredictOnlineGRPCService.this.stop();
                logger.error("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * block 一直到退出程序
     * @throws InterruptedException
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = -1;
        if (args.length >= 1){
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e){
                logger.error("Integer.parseInt(args[0]) error, args[0]={}", args[0]);
                System.exit(1);
            }
        }
        // 初始化操作
        WpaiPredictOnlineInit.init();
        final WPAIDLPredictOnlineGRPCService server = new WPAIDLPredictOnlineGRPCService();
        server.start(port);
        server.blockUntilShutdown();
    }

    /**
     * 实现 定义一个实现服务接口的类
     */
    private static class WPAIDLPredictOnlineGRPCServiceImpl extends WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceImplBase {

        @Override
        public void predict(Predict.PredictRequest predictRequest, StreamObserver<Predict.PredictResponse> responseObserver) {
            try {
                long start = System.currentTimeMillis();
                String modelName = predictRequest.getModelSpec().getName();
                int taskId = CommonUtils.extractTaskIdFromModelName(modelName);
                Predict.PredictResponse predictResponse = TensorflowServingService
                    .predict(taskId, predictRequest);
                responseObserver.onNext(predictResponse);
                responseObserver.onCompleted();
                int spend = (int) (System.currentTimeMillis() - start);
                logger.info("predict taskId={} predict spend {} ms", taskId, spend);
            }catch (Exception e){
                logger.error("predict error, msg={}", e.getMessage());
            }
        }

        @Override
        public void pytorchPredict(PredictionProtos.SeldonMessage request,
            io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage> responseObserver){
            try {
                long start = System.currentTimeMillis();
                Map<String, Value> tagsMap = request.getMeta().getTagsMap();
                if (!tagsMap.containsKey(TASK_ID_KEY)) {
                    responseObserver.onNext(null);
                    responseObserver.onCompleted();
                    logger.error("request meta does not contain field {}", TASK_ID_KEY);
                    return;
                }
                Value taskIdValue = tagsMap.get(TASK_ID_KEY);
                int taskId = (int) taskIdValue.getNumberValue();
                PredictionProtos.SeldonMessage response = PytorchOnlineService
                    .predict(taskId, request);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                int spend = (int) (System.currentTimeMillis() - start);
                logger.info("pytorchPredict taskId={} predict spend {} ms", taskId, spend);
            }catch (Exception e){
                logger.error("pytorchPredict error, msg={}", e.getMessage());
            }
        }

        @Override
        public void caffePredict(
            PredictionProtos.SeldonMessage request,
            io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage> responseObserver) {
            try {
                long start = System.currentTimeMillis();
                Map<String, Value> tagsMap = request.getMeta().getTagsMap();
                if (!tagsMap.containsKey(TASK_ID_KEY)) {
                    responseObserver.onNext(null);
                    responseObserver.onCompleted();
                    logger.error("request meta does not contain field {}", TASK_ID_KEY);
                    return;
                }
                Value taskIdValue = tagsMap.get(TASK_ID_KEY);
                int taskId = (int) taskIdValue.getNumberValue();
                PredictionProtos.SeldonMessage response = PytorchOnlineService
                    .predict(taskId, request);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                int spend = (int) (System.currentTimeMillis() - start);
                logger.info("caffePredict taskId={} predict spend {} ms", taskId, spend);
            }catch (Exception e){
                logger.error("caffePredict error, msg={}", e.getMessage());
            }
        }

        @Override
        public void tisPredict(GrpcService.ModelInferRequest request,
                               StreamObserver<GrpcService.ModelInferResponse> responseObserver) {
            try {
                long start = System.currentTimeMillis();
                String modelName = request.getModelName();
                int taskId = CommonUtils.extractTaskIdFromModelName(modelName);
                GrpcService.ModelInferResponse response = TisOnlineService
                    .predict(taskId, request);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                int spend = (int) (System.currentTimeMillis() - start);
                logger.info("predict taskId={} predict spend {} ms", taskId, spend);
            }catch (Exception e){
                logger.error("predict error, msg={}", e.getMessage());
            }
        }
    }

}
