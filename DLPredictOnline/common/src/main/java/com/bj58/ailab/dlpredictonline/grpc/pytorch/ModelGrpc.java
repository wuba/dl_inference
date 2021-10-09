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


package com.bj58.ailab.dlpredictonline.grpc.pytorch;

import com.bj58.ailab.dlpredictonline.grpc.pytorch.PredictionProtos.Feedback;
import com.bj58.ailab.dlpredictonline.grpc.pytorch.PredictionProtos.SeldonMessage;
import io.grpc.stub.ClientCalls;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * @author 58
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: prediction.proto")
public class ModelGrpc {
    private ModelGrpc() {}

    public static final String SERVICE_NAME = "seldon.protos.Model";

    /**
     *   Static method descriptors that strictly reflect the proto.
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<SeldonMessage,
        SeldonMessage> METHOD_PREDICT =
        io.grpc.MethodDescriptor.create(
            io.grpc.MethodDescriptor.MethodType.UNARY,
            generateFullMethodName(
                "seldon.protos.Model", "Predict"),
            io.grpc.protobuf.ProtoUtils.marshaller(PredictionProtos.SeldonMessage.getDefaultInstance()),
            io.grpc.protobuf.ProtoUtils.marshaller(PredictionProtos.SeldonMessage.getDefaultInstance()));
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static final io.grpc.MethodDescriptor<Feedback,
        SeldonMessage> METHOD_SEND_FEEDBACK =
        io.grpc.MethodDescriptor.create(
            io.grpc.MethodDescriptor.MethodType.UNARY,
            generateFullMethodName(
                "seldon.protos.Model", "SendFeedback"),
            io.grpc.protobuf.ProtoUtils.marshaller(PredictionProtos.Feedback.getDefaultInstance()),
            io.grpc.protobuf.ProtoUtils.marshaller(PredictionProtos.SeldonMessage.getDefaultInstance()));

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static ModelStub newStub(io.grpc.Channel channel) {
        return new ModelStub(channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static ModelBlockingStub newBlockingStub(
        io.grpc.Channel channel) {
        return new ModelBlockingStub(channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
     */
    public static ModelFutureStub newFutureStub(
        io.grpc.Channel channel) {
        return new ModelFutureStub(channel);
    }

    /**
     */
    public static abstract class ModelImplBase implements io.grpc.BindableService {

        /**
         */
        public void predict(SeldonMessage request,
            io.grpc.stub.StreamObserver<SeldonMessage> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_PREDICT, responseObserver);
        }

        /**
         */
        public void sendFeedback(Feedback request,
            io.grpc.stub.StreamObserver<SeldonMessage> responseObserver) {
            asyncUnimplementedUnaryCall(METHOD_SEND_FEEDBACK, responseObserver);
        }

        @Override public io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                .addMethod(
                    METHOD_PREDICT,
                    asyncUnaryCall(
                        new MethodHandlers<
                            SeldonMessage,
                            SeldonMessage>(
                            this, METHODID_PREDICT)))
                .addMethod(
                    METHOD_SEND_FEEDBACK,
                    asyncUnaryCall(
                        new MethodHandlers<
                            Feedback,
                            SeldonMessage>(
                            this, METHODID_SEND_FEEDBACK)))
                .build();
        }
    }

    /**
     */
    public static final class ModelStub extends io.grpc.stub.AbstractStub<ModelStub> {
        private ModelStub(io.grpc.Channel channel) {
            super(channel);
        }

        private ModelStub(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected ModelStub build(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            return new ModelStub(channel, callOptions);
        }

        /**
         */
        public void predict(SeldonMessage request,
            io.grpc.stub.StreamObserver<SeldonMessage> responseObserver) {
            ClientCalls.asyncUnaryCall(
                getChannel().newCall(METHOD_PREDICT, getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void sendFeedback(Feedback request,
            io.grpc.stub.StreamObserver<SeldonMessage> responseObserver) {
            ClientCalls.asyncUnaryCall(
                getChannel().newCall(METHOD_SEND_FEEDBACK, getCallOptions()), request, responseObserver);
        }
    }

    /**
     */
    public static final class ModelBlockingStub extends io.grpc.stub.AbstractStub<ModelBlockingStub> {
        private ModelBlockingStub(io.grpc.Channel channel) {
            super(channel);
        }

        private ModelBlockingStub(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected ModelBlockingStub build(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            return new ModelBlockingStub(channel, callOptions);
        }

        /**
         */
        public SeldonMessage predict(SeldonMessage request) {
            return blockingUnaryCall(
                getChannel(), METHOD_PREDICT, getCallOptions(), request);
        }

        /**
         */
        public SeldonMessage sendFeedback(Feedback request) {
            return blockingUnaryCall(
                getChannel(), METHOD_SEND_FEEDBACK, getCallOptions(), request);
        }
    }

    /**
     */
    public static final class ModelFutureStub extends io.grpc.stub.AbstractStub<ModelFutureStub> {
        private ModelFutureStub(io.grpc.Channel channel) {
            super(channel);
        }

        private ModelFutureStub(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected ModelFutureStub build(io.grpc.Channel channel,
            io.grpc.CallOptions callOptions) {
            return new ModelFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<SeldonMessage> predict(
            SeldonMessage request) {
            return futureUnaryCall(
                getChannel().newCall(METHOD_PREDICT, getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<SeldonMessage> sendFeedback(
            Feedback request) {
            return futureUnaryCall(
                getChannel().newCall(METHOD_SEND_FEEDBACK, getCallOptions()), request);
        }
    }

    private static final int METHODID_PREDICT = 0;
    private static final int METHODID_SEND_FEEDBACK = 1;

    private static class MethodHandlers<Req, Resp> implements
        io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
        io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
        io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
        io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final ModelImplBase serviceImpl;
        private final int methodId;

        public MethodHandlers(ModelImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_PREDICT:
                    serviceImpl.predict((SeldonMessage) request,
                        (io.grpc.stub.StreamObserver<SeldonMessage>) responseObserver);
                    break;
                case METHODID_SEND_FEEDBACK:
                    serviceImpl.sendFeedback((Feedback) request,
                        (io.grpc.stub.StreamObserver<SeldonMessage>) responseObserver);
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

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        return new io.grpc.ServiceDescriptor(SERVICE_NAME,
            METHOD_PREDICT,
            METHOD_SEND_FEEDBACK);
    }

}
