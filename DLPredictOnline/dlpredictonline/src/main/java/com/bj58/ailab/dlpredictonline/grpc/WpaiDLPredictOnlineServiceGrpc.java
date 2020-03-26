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


package com.bj58.ailab.dlpredictonline.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.bj58.ailab.dlpredictonline.entity.PredictionProtos;

/**
 * grpc自动生成文件
 * @author 58
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: wpai.proto")
public final class WpaiDLPredictOnlineServiceGrpc {
    private WpaiDLPredictOnlineServiceGrpc() {}

    public static final String SERVICE_NAME = "com.bj58.ailab.wpai.grpc.WpaiDLPredictOnlineService";

    /**
     *  Static method descriptors that strictly reflect the proto.
     */
    private static volatile io.grpc.MethodDescriptor<tensorflow.serving.Predict.PredictRequest,
        tensorflow.serving.Predict.PredictResponse> getPredictMethod;

    @io.grpc.stub.annotations.RpcMethod(
        fullMethodName = SERVICE_NAME + '/' + "Predict",
        requestType = tensorflow.serving.Predict.PredictRequest.class,
        responseType = tensorflow.serving.Predict.PredictResponse.class,
        methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<tensorflow.serving.Predict.PredictRequest,
        tensorflow.serving.Predict.PredictResponse> getPredictMethod() {
        io.grpc.MethodDescriptor<tensorflow.serving.Predict.PredictRequest, tensorflow.serving.Predict.PredictResponse> getPredictMethod;
        if ((getPredictMethod = WpaiDLPredictOnlineServiceGrpc.getPredictMethod) == null) {
            synchronized (WpaiDLPredictOnlineServiceGrpc.class) {
                if ((getPredictMethod = WpaiDLPredictOnlineServiceGrpc.getPredictMethod) == null) {
                    WpaiDLPredictOnlineServiceGrpc.getPredictMethod = getPredictMethod =
                        io.grpc.MethodDescriptor.<tensorflow.serving.Predict.PredictRequest, tensorflow.serving.Predict.PredictResponse>newBuilder()
                            .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                            .setFullMethodName(generateFullMethodName(
                                "com.bj58.ailab.wpai.grpc.WpaiDLPredictOnlineService", "Predict"))
                            .setSampledToLocalTracing(true)
                            .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                tensorflow.serving.Predict.PredictRequest.getDefaultInstance()))
                            .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                tensorflow.serving.Predict.PredictResponse.getDefaultInstance()))
                            .setSchemaDescriptor(new WpaiDLPredictOnlineServiceMethodDescriptorSupplier("Predict"))
                            .build();
                }
            }
        }
        return getPredictMethod;
    }

    private static volatile io.grpc.MethodDescriptor<PredictionProtos.SeldonMessage,
        PredictionProtos.SeldonMessage> getPytorchPredictMethod;

    @io.grpc.stub.annotations.RpcMethod(
        fullMethodName = SERVICE_NAME + '/' + "PytorchPredict",
        requestType = PredictionProtos.SeldonMessage.class,
        responseType = PredictionProtos.SeldonMessage.class,
        methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<PredictionProtos.SeldonMessage,
        PredictionProtos.SeldonMessage> getPytorchPredictMethod() {
        io.grpc.MethodDescriptor<PredictionProtos.SeldonMessage, PredictionProtos.SeldonMessage> getPytorchPredictMethod;
        if ((getPytorchPredictMethod = WpaiDLPredictOnlineServiceGrpc.getPytorchPredictMethod) == null) {
            synchronized (WpaiDLPredictOnlineServiceGrpc.class) {
                if ((getPytorchPredictMethod = WpaiDLPredictOnlineServiceGrpc.getPytorchPredictMethod) == null) {
                    WpaiDLPredictOnlineServiceGrpc.getPytorchPredictMethod = getPytorchPredictMethod =
                        io.grpc.MethodDescriptor.<PredictionProtos.SeldonMessage, PredictionProtos.SeldonMessage>newBuilder()
                            .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                            .setFullMethodName(generateFullMethodName(
                                "com.bj58.ailab.wpai.grpc.WpaiDLPredictOnlineService", "PytorchPredict"))
                            .setSampledToLocalTracing(true)
                            .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                PredictionProtos.SeldonMessage.getDefaultInstance()))
                            .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                PredictionProtos.SeldonMessage.getDefaultInstance()))
                            .setSchemaDescriptor(new WpaiDLPredictOnlineServiceMethodDescriptorSupplier("PytorchPredict"))
                            .build();
                }
            }
        }
        return getPytorchPredictMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static WpaiDLPredictOnlineServiceStub newStub(io.grpc.Channel channel) {
        return new WpaiDLPredictOnlineServiceStub(channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static WpaiDLPredictOnlineServiceBlockingStub newBlockingStub(
        io.grpc.Channel channel) {
        return new WpaiDLPredictOnlineServiceBlockingStub(channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static WpaiDLPredictOnlineServiceFutureStub newFutureStub(
        io.grpc.Channel channel) {
        return new WpaiDLPredictOnlineServiceFutureStub(channel);
    }

    /**
     */
    public static abstract class WpaiDLPredictOnlineServiceImplBase implements io.grpc.BindableService {

        /**
         */
        public void predict(tensorflow.serving.Predict.PredictRequest request,
            io.grpc.stub.StreamObserver<tensorflow.serving.Predict.PredictResponse> responseObserver) {
            asyncUnimplementedUnaryCall(getPredictMethod(), responseObserver);
        }

        /**
         */
        public void pytorchPredict(PredictionProtos.SeldonMessage request,
            io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage> responseObserver) {
            asyncUnimplementedUnaryCall(getPytorchPredictMethod(), responseObserver);
        }

        @Override public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                .addMethod(
                    getPredictMethod(),
                    asyncUnaryCall(
                        new MethodHandlers<
                            tensorflow.serving.Predict.PredictRequest,
                            tensorflow.serving.Predict.PredictResponse>(
                            this, METHODID_PREDICT)))
                .addMethod(
                    getPytorchPredictMethod(),
                    asyncUnaryCall(
                        new MethodHandlers<
                            PredictionProtos.SeldonMessage,
                            PredictionProtos.SeldonMessage>(
                            this, METHODID_PYTORCH_PREDICT)))
                .build();
        }
    }

    /**
     */
    public static final class WpaiDLPredictOnlineServiceStub extends io.grpc.stub.AbstractStub<WpaiDLPredictOnlineServiceStub> {
        private WpaiDLPredictOnlineServiceStub(io.grpc.Channel channel) {
            super(channel);
        }

        private WpaiDLPredictOnlineServiceStub(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected WpaiDLPredictOnlineServiceStub build(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            return new WpaiDLPredictOnlineServiceStub(channel, callOptions);
        }

        /**
         */
        public void predict(tensorflow.serving.Predict.PredictRequest request,
            io.grpc.stub.StreamObserver<tensorflow.serving.Predict.PredictResponse> responseObserver) {
            asyncUnaryCall(
                getChannel().newCall(getPredictMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void pytorchPredict(PredictionProtos.SeldonMessage request,
            io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage> responseObserver) {
            asyncUnaryCall(
                getChannel().newCall(getPytorchPredictMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     */
    public static final class WpaiDLPredictOnlineServiceBlockingStub extends io.grpc.stub.AbstractStub<WpaiDLPredictOnlineServiceBlockingStub> {
        private WpaiDLPredictOnlineServiceBlockingStub(io.grpc.Channel channel) {
            super(channel);
        }

        private WpaiDLPredictOnlineServiceBlockingStub(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected WpaiDLPredictOnlineServiceBlockingStub build(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            return new WpaiDLPredictOnlineServiceBlockingStub(channel, callOptions);
        }

        /**
         */
        public tensorflow.serving.Predict.PredictResponse predict(tensorflow.serving.Predict.PredictRequest request) {
            return blockingUnaryCall(
                getChannel(), getPredictMethod(), getCallOptions(), request);
        }

        /**
         */
        public PredictionProtos.SeldonMessage pytorchPredict(PredictionProtos.SeldonMessage request) {
            return blockingUnaryCall(
                getChannel(), getPytorchPredictMethod(), getCallOptions(), request);
        }
    }

    /**
     */
    public static final class WpaiDLPredictOnlineServiceFutureStub extends io.grpc.stub.AbstractStub<WpaiDLPredictOnlineServiceFutureStub> {
        private WpaiDLPredictOnlineServiceFutureStub(io.grpc.Channel channel) {
            super(channel);
        }

        private WpaiDLPredictOnlineServiceFutureStub(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected WpaiDLPredictOnlineServiceFutureStub build(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            return new WpaiDLPredictOnlineServiceFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<tensorflow.serving.Predict.PredictResponse> predict(
            tensorflow.serving.Predict.PredictRequest request) {
            return futureUnaryCall(
                getChannel().newCall(getPredictMethod(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<PredictionProtos.SeldonMessage> pytorchPredict(
            PredictionProtos.SeldonMessage request) {
            return futureUnaryCall(
                getChannel().newCall(getPytorchPredictMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_PREDICT = 0;
    private static final int METHODID_PYTORCH_PREDICT = 1;

    private static final class MethodHandlers<Req, Resp> implements
        io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
        io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
        io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
        io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final WpaiDLPredictOnlineServiceImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(WpaiDLPredictOnlineServiceImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_PREDICT:
                    serviceImpl.predict((tensorflow.serving.Predict.PredictRequest) request,
                        (io.grpc.stub.StreamObserver<tensorflow.serving.Predict.PredictResponse>) responseObserver);
                    break;
                case METHODID_PYTORCH_PREDICT:
                    serviceImpl.pytorchPredict((PredictionProtos.SeldonMessage) request,
                        (io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
            io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private static abstract class WpaiDLPredictOnlineServiceBaseDescriptorSupplier
        implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        WpaiDLPredictOnlineServiceBaseDescriptorSupplier() {}

        @Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return WpaiDLPredictOnline.getDescriptor();
        }

        @Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("WpaiDLPredictOnlineService");
        }
    }

    private static final class WpaiDLPredictOnlineServiceFileDescriptorSupplier
        extends WpaiDLPredictOnlineServiceBaseDescriptorSupplier {
        WpaiDLPredictOnlineServiceFileDescriptorSupplier() {}
    }

    private static final class WpaiDLPredictOnlineServiceMethodDescriptorSupplier
        extends WpaiDLPredictOnlineServiceBaseDescriptorSupplier
        implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        WpaiDLPredictOnlineServiceMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (WpaiDLPredictOnlineServiceGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                        .setSchemaDescriptor(new WpaiDLPredictOnlineServiceFileDescriptorSupplier())
                        .addMethod(getPredictMethod())
                        .addMethod(getPytorchPredictMethod())
                        .build();
                }
            }
        }
        return result;
    }
}
