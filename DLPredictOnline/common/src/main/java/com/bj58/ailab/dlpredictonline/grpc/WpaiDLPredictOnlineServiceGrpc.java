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

import com.bj58.ailab.dlpredictonline.grpc.pytorch.PredictionProtos;
import com.bj58.ailab.dlpredictonline.grpc.tis.GrpcService;
import tensorflow.serving.Predict;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * grpc编译自动生成文件
 * @author 58
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: wpai.proto")
public class WpaiDLPredictOnlineServiceGrpc {

  private WpaiDLPredictOnlineServiceGrpc() {}

  public static final String SERVICE_NAME = "com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<Predict.PredictRequest,
      Predict.PredictResponse> METHOD_PREDICT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineService", "Predict"),
          io.grpc.protobuf.ProtoUtils.marshaller(Predict.PredictRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Predict.PredictResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<PredictionProtos.SeldonMessage,
      PredictionProtos.SeldonMessage> METHOD_PYTORCH_PREDICT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineService", "PytorchPredict"),
          io.grpc.protobuf.ProtoUtils.marshaller(PredictionProtos.SeldonMessage.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(PredictionProtos.SeldonMessage.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<PredictionProtos.SeldonMessage,
      PredictionProtos.SeldonMessage> METHOD_CAFFE_PREDICT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineService", "CaffePredict"),
          io.grpc.protobuf.ProtoUtils.marshaller(PredictionProtos.SeldonMessage.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(PredictionProtos.SeldonMessage.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ModelInferRequest,
      GrpcService.ModelInferResponse> METHOD_TIS_PREDICT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineService", "TisPredict"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelInferRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelInferResponse.getDefaultInstance()));

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
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
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
    public void predict(Predict.PredictRequest request,
                        io.grpc.stub.StreamObserver<Predict.PredictResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PREDICT, responseObserver);
    }

    /**
     */
    public void pytorchPredict(PredictionProtos.SeldonMessage request,
                               io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PYTORCH_PREDICT, responseObserver);
    }

    /**
     */
    public void caffePredict(PredictionProtos.SeldonMessage request,
                             io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CAFFE_PREDICT, responseObserver);
    }

    /**
     */
    public void tisPredict(GrpcService.ModelInferRequest request,
                           io.grpc.stub.StreamObserver<GrpcService.ModelInferResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_TIS_PREDICT, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PREDICT,
            asyncUnaryCall(
              new MethodHandlers<
                  Predict.PredictRequest,
                  Predict.PredictResponse>(
                  this, METHODID_PREDICT)))
          .addMethod(
            METHOD_PYTORCH_PREDICT,
            asyncUnaryCall(
              new MethodHandlers<
                PredictionProtos.SeldonMessage,
                PredictionProtos.SeldonMessage>(
                  this, METHODID_PYTORCH_PREDICT)))
          .addMethod(
            METHOD_CAFFE_PREDICT,
            asyncUnaryCall(
              new MethodHandlers<
                PredictionProtos.SeldonMessage,
                PredictionProtos.SeldonMessage>(
                  this, METHODID_CAFFE_PREDICT)))
          .addMethod(
            METHOD_TIS_PREDICT,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ModelInferRequest,
                GrpcService.ModelInferResponse>(
                  this, METHODID_TIS_PREDICT)))
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
    public void predict(Predict.PredictRequest request,
                        io.grpc.stub.StreamObserver<Predict.PredictResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PREDICT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void pytorchPredict(PredictionProtos.SeldonMessage request,
                               io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PYTORCH_PREDICT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void caffePredict(PredictionProtos.SeldonMessage request,
                             io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CAFFE_PREDICT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void tisPredict(GrpcService.ModelInferRequest request,
                           io.grpc.stub.StreamObserver<GrpcService.ModelInferResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TIS_PREDICT, getCallOptions()), request, responseObserver);
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
    public Predict.PredictResponse predict(Predict.PredictRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PREDICT, getCallOptions(), request);
    }

    /**
     */
    public PredictionProtos.SeldonMessage pytorchPredict(PredictionProtos.SeldonMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PYTORCH_PREDICT, getCallOptions(), request);
    }

    /**
     */
    public PredictionProtos.SeldonMessage caffePredict(PredictionProtos.SeldonMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CAFFE_PREDICT, getCallOptions(), request);
    }

    /**
     */
    public GrpcService.ModelInferResponse tisPredict(GrpcService.ModelInferRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TIS_PREDICT, getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<Predict.PredictResponse> predict(
        Predict.PredictRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PREDICT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<PredictionProtos.SeldonMessage> pytorchPredict(
        PredictionProtos.SeldonMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PYTORCH_PREDICT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<PredictionProtos.SeldonMessage> caffePredict(
        PredictionProtos.SeldonMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CAFFE_PREDICT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ModelInferResponse> tisPredict(
        GrpcService.ModelInferRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TIS_PREDICT, getCallOptions()), request);
    }
  }

  private static final int METHODID_PREDICT = 0;
  private static final int METHODID_PYTORCH_PREDICT = 1;
  private static final int METHODID_CAFFE_PREDICT = 2;
  private static final int METHODID_TIS_PREDICT = 3;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WpaiDLPredictOnlineServiceImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(WpaiDLPredictOnlineServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PREDICT:
          serviceImpl.predict((Predict.PredictRequest) request,
              (io.grpc.stub.StreamObserver<Predict.PredictResponse>) responseObserver);
          break;
        case METHODID_PYTORCH_PREDICT:
          serviceImpl.pytorchPredict((PredictionProtos.SeldonMessage) request,
              (io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage>) responseObserver);
          break;
        case METHODID_CAFFE_PREDICT:
          serviceImpl.caffePredict((PredictionProtos.SeldonMessage) request,
              (io.grpc.stub.StreamObserver<PredictionProtos.SeldonMessage>) responseObserver);
          break;
        case METHODID_TIS_PREDICT:
          serviceImpl.tisPredict((GrpcService.ModelInferRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ModelInferResponse>) responseObserver);
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
        METHOD_PYTORCH_PREDICT,
        METHOD_CAFFE_PREDICT,
        METHOD_TIS_PREDICT);
  }

}
