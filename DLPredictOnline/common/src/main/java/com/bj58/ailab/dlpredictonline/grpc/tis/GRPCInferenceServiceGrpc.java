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

package com.bj58.ailab.dlpredictonline.grpc.tis;

import io.grpc.stub.ClientCalls;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.*;

/**
 * @author 58
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: grpc_service.proto")
public class GRPCInferenceServiceGrpc {

  private GRPCInferenceServiceGrpc() {}

  public static final String SERVICE_NAME = "inference.GRPCInferenceService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ServerLiveRequest,
      GrpcService.ServerLiveResponse> METHOD_SERVER_LIVE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ServerLive"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ServerLiveRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ServerLiveResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ServerReadyRequest,
      GrpcService.ServerReadyResponse> METHOD_SERVER_READY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ServerReady"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ServerReadyRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ServerReadyResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ModelReadyRequest,
      GrpcService.ModelReadyResponse> METHOD_MODEL_READY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ModelReady"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelReadyRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelReadyResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ServerMetadataRequest,
      GrpcService.ServerMetadataResponse> METHOD_SERVER_METADATA =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ServerMetadata"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ServerMetadataRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ServerMetadataResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ModelMetadataRequest,
      GrpcService.ModelMetadataResponse> METHOD_MODEL_METADATA =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ModelMetadata"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelMetadataRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelMetadataResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ModelInferRequest,
      GrpcService.ModelInferResponse> METHOD_MODEL_INFER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ModelInfer"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelInferRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelInferResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ModelInferRequest,
      GrpcService.ModelStreamInferResponse> METHOD_MODEL_STREAM_INFER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ModelStreamInfer"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelInferRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelStreamInferResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ModelConfigRequest,
      GrpcService.ModelConfigResponse> METHOD_MODEL_CONFIG =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ModelConfig"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelConfigRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelConfigResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.ModelStatisticsRequest,
      GrpcService.ModelStatisticsResponse> METHOD_MODEL_STATISTICS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "ModelStatistics"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelStatisticsRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.ModelStatisticsResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.RepositoryIndexRequest,
      GrpcService.RepositoryIndexResponse> METHOD_REPOSITORY_INDEX =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "RepositoryIndex"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.RepositoryIndexRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.RepositoryIndexResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.RepositoryModelLoadRequest,
      GrpcService.RepositoryModelLoadResponse> METHOD_REPOSITORY_MODEL_LOAD =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "RepositoryModelLoad"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.RepositoryModelLoadRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.RepositoryModelLoadResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.RepositoryModelUnloadRequest,
      GrpcService.RepositoryModelUnloadResponse> METHOD_REPOSITORY_MODEL_UNLOAD =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "RepositoryModelUnload"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.RepositoryModelUnloadRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.RepositoryModelUnloadResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.SystemSharedMemoryStatusRequest,
      GrpcService.SystemSharedMemoryStatusResponse> METHOD_SYSTEM_SHARED_MEMORY_STATUS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "SystemSharedMemoryStatus"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.SystemSharedMemoryStatusRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.SystemSharedMemoryStatusResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.SystemSharedMemoryRegisterRequest,
      GrpcService.SystemSharedMemoryRegisterResponse> METHOD_SYSTEM_SHARED_MEMORY_REGISTER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "SystemSharedMemoryRegister"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.SystemSharedMemoryRegisterRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.SystemSharedMemoryRegisterResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.SystemSharedMemoryUnregisterRequest,
      GrpcService.SystemSharedMemoryUnregisterResponse> METHOD_SYSTEM_SHARED_MEMORY_UNREGISTER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "SystemSharedMemoryUnregister"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.SystemSharedMemoryUnregisterRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.SystemSharedMemoryUnregisterResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.CudaSharedMemoryStatusRequest,
      GrpcService.CudaSharedMemoryStatusResponse> METHOD_CUDA_SHARED_MEMORY_STATUS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "CudaSharedMemoryStatus"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.CudaSharedMemoryStatusRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.CudaSharedMemoryStatusResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.CudaSharedMemoryRegisterRequest,
      GrpcService.CudaSharedMemoryRegisterResponse> METHOD_CUDA_SHARED_MEMORY_REGISTER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "CudaSharedMemoryRegister"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.CudaSharedMemoryRegisterRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.CudaSharedMemoryRegisterResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcService.CudaSharedMemoryUnregisterRequest,
      GrpcService.CudaSharedMemoryUnregisterResponse> METHOD_CUDA_SHARED_MEMORY_UNREGISTER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "inference.GRPCInferenceService", "CudaSharedMemoryUnregister"),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.CudaSharedMemoryUnregisterRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(GrpcService.CudaSharedMemoryUnregisterResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GRPCInferenceServiceStub newStub(io.grpc.Channel channel) {
    return new GRPCInferenceServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GRPCInferenceServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GRPCInferenceServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static GRPCInferenceServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GRPCInferenceServiceFutureStub(channel);
  }

  /**
   * <pre>
   *&#64;&#64;
   *&#64;&#64;.. cpp:var:: service InferenceService
   *&#64;&#64;
   *&#64;&#64;   Inference Server GRPC endpoints.
   *&#64;&#64;
   * </pre>
   */
  public static abstract class GRPCInferenceServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerLive(ServerLiveRequest) returns
     *&#64;&#64;       (ServerLiveResponse)
     *&#64;&#64;
     *&#64;&#64;     Check liveness of the inference server.
     *&#64;&#64;
     * </pre>
     */
    public void serverLive(GrpcService.ServerLiveRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ServerLiveResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SERVER_LIVE, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerReady(ServerReadyRequest) returns
     *&#64;&#64;       (ServerReadyResponse)
     *&#64;&#64;
     *&#64;&#64;     Check readiness of the inference server.
     *&#64;&#64;
     * </pre>
     */
    public void serverReady(GrpcService.ServerReadyRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ServerReadyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SERVER_READY, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelReady(ModelReadyRequest) returns
     *&#64;&#64;       (ModelReadyResponse)
     *&#64;&#64;
     *&#64;&#64;     Check readiness of a model in the inference server.
     *&#64;&#64;
     * </pre>
     */
    public void modelReady(GrpcService.ModelReadyRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelReadyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MODEL_READY, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerMetadata(ServerMetadataRequest) returns
     *&#64;&#64;       (ServerMetadataResponse)
     *&#64;&#64;
     *&#64;&#64;     Get server metadata.
     *&#64;&#64;
     * </pre>
     */
    public void serverMetadata(GrpcService.ServerMetadataRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ServerMetadataResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SERVER_METADATA, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelMetadata(ModelMetadataRequest) returns
     *&#64;&#64;       (ModelMetadataResponse)
     *&#64;&#64;
     *&#64;&#64;     Get model metadata.
     *&#64;&#64;
     * </pre>
     */
    public void modelMetadata(GrpcService.ModelMetadataRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelMetadataResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MODEL_METADATA, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelInfer(ModelInferRequest) returns
     *&#64;&#64;       (ModelInferResponse)
     *&#64;&#64;
     *&#64;&#64;     Perform inference using a specific model.
     *&#64;&#64;
     * </pre>
     */
    public void modelInfer(GrpcService.ModelInferRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelInferResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MODEL_INFER, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelStreamInfer(stream ModelInferRequest) returns
     *&#64;&#64;       (stream ModelStreamInferResponse)
     *&#64;&#64;
     *&#64;&#64;     Perform streaming inference.
     *&#64;&#64;
     * </pre>
     */
    public io.grpc.stub.StreamObserver<GrpcService.ModelInferRequest> modelStreamInfer(
        io.grpc.stub.StreamObserver<GrpcService.ModelStreamInferResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_MODEL_STREAM_INFER, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelConfig(ModelConfigRequest) returns
     *&#64;&#64;       (ModelConfigResponse)
     *&#64;&#64;
     *&#64;&#64;     Get model configuration.
     *&#64;&#64;
     * </pre>
     */
    public void modelConfig(GrpcService.ModelConfigRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelConfigResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MODEL_CONFIG, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelStatistics(
     *&#64;&#64;                     ModelStatisticsRequest)
     *&#64;&#64;                   returns (ModelStatisticsResponse)
     *&#64;&#64;
     *&#64;&#64;     Get the cumulative inference statistics for a model.
     *&#64;&#64;
     * </pre>
     */
    public void modelStatistics(GrpcService.ModelStatisticsRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelStatisticsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MODEL_STATISTICS, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryIndex(RepositoryIndexRequest) returns
     *&#64;&#64;       (RepositoryIndexResponse)
     *&#64;&#64;
     *&#64;&#64;     Get the index of model repository contents.
     *&#64;&#64;
     * </pre>
     */
    public void repositoryIndex(GrpcService.RepositoryIndexRequest request,
        io.grpc.stub.StreamObserver<GrpcService.RepositoryIndexResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REPOSITORY_INDEX, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryModelLoad(RepositoryModelLoadRequest) returns
     *&#64;&#64;       (RepositoryModelLoadResponse)
     *&#64;&#64;
     *&#64;&#64;     Load or reload a model from a repository.
     *&#64;&#64;
     * </pre>
     */
    public void repositoryModelLoad(GrpcService.RepositoryModelLoadRequest request,
        io.grpc.stub.StreamObserver<GrpcService.RepositoryModelLoadResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REPOSITORY_MODEL_LOAD, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryModelUnload(RepositoryModelUnloadRequest)
     *&#64;&#64;       returns (RepositoryModelUnloadResponse)
     *&#64;&#64;
     *&#64;&#64;     Unload a model.
     *&#64;&#64;
     * </pre>
     */
    public void repositoryModelUnload(GrpcService.RepositoryModelUnloadRequest request,
        io.grpc.stub.StreamObserver<GrpcService.RepositoryModelUnloadResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REPOSITORY_MODEL_UNLOAD, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryStatus(
     *&#64;&#64;                     SystemSharedMemoryStatusRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryStatusRespose)
     *&#64;&#64;
     *&#64;&#64;     Get the status of all registered system-shared-memory regions.
     *&#64;&#64;
     * </pre>
     */
    public void systemSharedMemoryStatus(GrpcService.SystemSharedMemoryStatusRequest request,
        io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SYSTEM_SHARED_MEMORY_STATUS, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryRegister(
     *&#64;&#64;                     SystemSharedMemoryRegisterRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryRegisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Register a system-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public void systemSharedMemoryRegister(GrpcService.SystemSharedMemoryRegisterRequest request,
        io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryRegisterResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SYSTEM_SHARED_MEMORY_REGISTER, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryUnregister(
     *&#64;&#64;                     SystemSharedMemoryUnregisterRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryUnregisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Unregister a system-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public void systemSharedMemoryUnregister(GrpcService.SystemSharedMemoryUnregisterRequest request,
        io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryUnregisterResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SYSTEM_SHARED_MEMORY_UNREGISTER, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryStatus(
     *&#64;&#64;                     CudaSharedMemoryStatusRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryStatusRespose)
     *&#64;&#64;
     *&#64;&#64;     Get the status of all registered CUDA-shared-memory regions.
     *&#64;&#64;
     * </pre>
     */
    public void cudaSharedMemoryStatus(GrpcService.CudaSharedMemoryStatusRequest request,
        io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CUDA_SHARED_MEMORY_STATUS, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryRegister(
     *&#64;&#64;                     CudaSharedMemoryRegisterRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryRegisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Register a CUDA-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public void cudaSharedMemoryRegister(GrpcService.CudaSharedMemoryRegisterRequest request,
        io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryRegisterResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CUDA_SHARED_MEMORY_REGISTER, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryUnregister(
     *&#64;&#64;                     CudaSharedMemoryUnregisterRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryUnregisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Unregister a CUDA-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public void cudaSharedMemoryUnregister(GrpcService.CudaSharedMemoryUnregisterRequest request,
        io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryUnregisterResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CUDA_SHARED_MEMORY_UNREGISTER, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SERVER_LIVE,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ServerLiveRequest,
                GrpcService.ServerLiveResponse>(
                  this, METHODID_SERVER_LIVE)))
          .addMethod(
            METHOD_SERVER_READY,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ServerReadyRequest,
                GrpcService.ServerReadyResponse>(
                  this, METHODID_SERVER_READY)))
          .addMethod(
            METHOD_MODEL_READY,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ModelReadyRequest,
                GrpcService.ModelReadyResponse>(
                  this, METHODID_MODEL_READY)))
          .addMethod(
            METHOD_SERVER_METADATA,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ServerMetadataRequest,
                GrpcService.ServerMetadataResponse>(
                  this, METHODID_SERVER_METADATA)))
          .addMethod(
            METHOD_MODEL_METADATA,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ModelMetadataRequest,
                GrpcService.ModelMetadataResponse>(
                  this, METHODID_MODEL_METADATA)))
          .addMethod(
            METHOD_MODEL_INFER,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ModelInferRequest,
                GrpcService.ModelInferResponse>(
                  this, METHODID_MODEL_INFER)))
          .addMethod(
            METHOD_MODEL_STREAM_INFER,
            asyncBidiStreamingCall(
              new MethodHandlers<
                GrpcService.ModelInferRequest,
                GrpcService.ModelStreamInferResponse>(
                  this, METHODID_MODEL_STREAM_INFER)))
          .addMethod(
            METHOD_MODEL_CONFIG,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ModelConfigRequest,
                GrpcService.ModelConfigResponse>(
                  this, METHODID_MODEL_CONFIG)))
          .addMethod(
            METHOD_MODEL_STATISTICS,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.ModelStatisticsRequest,
                GrpcService.ModelStatisticsResponse>(
                  this, METHODID_MODEL_STATISTICS)))
          .addMethod(
            METHOD_REPOSITORY_INDEX,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.RepositoryIndexRequest,
                GrpcService.RepositoryIndexResponse>(
                  this, METHODID_REPOSITORY_INDEX)))
          .addMethod(
            METHOD_REPOSITORY_MODEL_LOAD,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.RepositoryModelLoadRequest,
                GrpcService.RepositoryModelLoadResponse>(
                  this, METHODID_REPOSITORY_MODEL_LOAD)))
          .addMethod(
            METHOD_REPOSITORY_MODEL_UNLOAD,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.RepositoryModelUnloadRequest,
                GrpcService.RepositoryModelUnloadResponse>(
                  this, METHODID_REPOSITORY_MODEL_UNLOAD)))
          .addMethod(
            METHOD_SYSTEM_SHARED_MEMORY_STATUS,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.SystemSharedMemoryStatusRequest,
                GrpcService.SystemSharedMemoryStatusResponse>(
                  this, METHODID_SYSTEM_SHARED_MEMORY_STATUS)))
          .addMethod(
            METHOD_SYSTEM_SHARED_MEMORY_REGISTER,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.SystemSharedMemoryRegisterRequest,
                GrpcService.SystemSharedMemoryRegisterResponse>(
                  this, METHODID_SYSTEM_SHARED_MEMORY_REGISTER)))
          .addMethod(
            METHOD_SYSTEM_SHARED_MEMORY_UNREGISTER,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.SystemSharedMemoryUnregisterRequest,
                GrpcService.SystemSharedMemoryUnregisterResponse>(
                  this, METHODID_SYSTEM_SHARED_MEMORY_UNREGISTER)))
          .addMethod(
            METHOD_CUDA_SHARED_MEMORY_STATUS,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.CudaSharedMemoryStatusRequest,
                GrpcService.CudaSharedMemoryStatusResponse>(
                  this, METHODID_CUDA_SHARED_MEMORY_STATUS)))
          .addMethod(
            METHOD_CUDA_SHARED_MEMORY_REGISTER,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.CudaSharedMemoryRegisterRequest,
                GrpcService.CudaSharedMemoryRegisterResponse>(
                  this, METHODID_CUDA_SHARED_MEMORY_REGISTER)))
          .addMethod(
            METHOD_CUDA_SHARED_MEMORY_UNREGISTER,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcService.CudaSharedMemoryUnregisterRequest,
                GrpcService.CudaSharedMemoryUnregisterResponse>(
                  this, METHODID_CUDA_SHARED_MEMORY_UNREGISTER)))
          .build();
    }
  }

  /**
   * <pre>
   *&#64;&#64;
   *&#64;&#64;.. cpp:var:: service InferenceService
   *&#64;&#64;
   *&#64;&#64;   Inference Server GRPC endpoints.
   *&#64;&#64;
   * </pre>
   */
  public static final class GRPCInferenceServiceStub extends io.grpc.stub.AbstractStub<GRPCInferenceServiceStub> {
    private GRPCInferenceServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GRPCInferenceServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GRPCInferenceServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GRPCInferenceServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerLive(ServerLiveRequest) returns
     *&#64;&#64;       (ServerLiveResponse)
     *&#64;&#64;
     *&#64;&#64;     Check liveness of the inference server.
     *&#64;&#64;
     * </pre>
     */
    public void serverLive(GrpcService.ServerLiveRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ServerLiveResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_SERVER_LIVE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerReady(ServerReadyRequest) returns
     *&#64;&#64;       (ServerReadyResponse)
     *&#64;&#64;
     *&#64;&#64;     Check readiness of the inference server.
     *&#64;&#64;
     * </pre>
     */
    public void serverReady(GrpcService.ServerReadyRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ServerReadyResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_SERVER_READY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelReady(ModelReadyRequest) returns
     *&#64;&#64;       (ModelReadyResponse)
     *&#64;&#64;
     *&#64;&#64;     Check readiness of a model in the inference server.
     *&#64;&#64;
     * </pre>
     */
    public void modelReady(GrpcService.ModelReadyRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelReadyResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_MODEL_READY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerMetadata(ServerMetadataRequest) returns
     *&#64;&#64;       (ServerMetadataResponse)
     *&#64;&#64;
     *&#64;&#64;     Get server metadata.
     *&#64;&#64;
     * </pre>
     */
    public void serverMetadata(GrpcService.ServerMetadataRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ServerMetadataResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_SERVER_METADATA, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelMetadata(ModelMetadataRequest) returns
     *&#64;&#64;       (ModelMetadataResponse)
     *&#64;&#64;
     *&#64;&#64;     Get model metadata.
     *&#64;&#64;
     * </pre>
     */
    public void modelMetadata(GrpcService.ModelMetadataRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelMetadataResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_MODEL_METADATA, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelInfer(ModelInferRequest) returns
     *&#64;&#64;       (ModelInferResponse)
     *&#64;&#64;
     *&#64;&#64;     Perform inference using a specific model.
     *&#64;&#64;
     * </pre>
     */
    public void modelInfer(GrpcService.ModelInferRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelInferResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_MODEL_INFER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelStreamInfer(stream ModelInferRequest) returns
     *&#64;&#64;       (stream ModelStreamInferResponse)
     *&#64;&#64;
     *&#64;&#64;     Perform streaming inference.
     *&#64;&#64;
     * </pre>
     */
    public io.grpc.stub.StreamObserver<GrpcService.ModelInferRequest> modelStreamInfer(
        io.grpc.stub.StreamObserver<GrpcService.ModelStreamInferResponse> responseObserver) {
      return ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(METHOD_MODEL_STREAM_INFER, getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelConfig(ModelConfigRequest) returns
     *&#64;&#64;       (ModelConfigResponse)
     *&#64;&#64;
     *&#64;&#64;     Get model configuration.
     *&#64;&#64;
     * </pre>
     */
    public void modelConfig(GrpcService.ModelConfigRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelConfigResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_MODEL_CONFIG, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelStatistics(
     *&#64;&#64;                     ModelStatisticsRequest)
     *&#64;&#64;                   returns (ModelStatisticsResponse)
     *&#64;&#64;
     *&#64;&#64;     Get the cumulative inference statistics for a model.
     *&#64;&#64;
     * </pre>
     */
    public void modelStatistics(GrpcService.ModelStatisticsRequest request,
        io.grpc.stub.StreamObserver<GrpcService.ModelStatisticsResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_MODEL_STATISTICS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryIndex(RepositoryIndexRequest) returns
     *&#64;&#64;       (RepositoryIndexResponse)
     *&#64;&#64;
     *&#64;&#64;     Get the index of model repository contents.
     *&#64;&#64;
     * </pre>
     */
    public void repositoryIndex(GrpcService.RepositoryIndexRequest request,
        io.grpc.stub.StreamObserver<GrpcService.RepositoryIndexResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_REPOSITORY_INDEX, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryModelLoad(RepositoryModelLoadRequest) returns
     *&#64;&#64;       (RepositoryModelLoadResponse)
     *&#64;&#64;
     *&#64;&#64;     Load or reload a model from a repository.
     *&#64;&#64;
     * </pre>
     */
    public void repositoryModelLoad(GrpcService.RepositoryModelLoadRequest request,
        io.grpc.stub.StreamObserver<GrpcService.RepositoryModelLoadResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_REPOSITORY_MODEL_LOAD, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryModelUnload(RepositoryModelUnloadRequest)
     *&#64;&#64;       returns (RepositoryModelUnloadResponse)
     *&#64;&#64;
     *&#64;&#64;     Unload a model.
     *&#64;&#64;
     * </pre>
     */
    public void repositoryModelUnload(GrpcService.RepositoryModelUnloadRequest request,
        io.grpc.stub.StreamObserver<GrpcService.RepositoryModelUnloadResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_REPOSITORY_MODEL_UNLOAD, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryStatus(
     *&#64;&#64;                     SystemSharedMemoryStatusRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryStatusRespose)
     *&#64;&#64;
     *&#64;&#64;     Get the status of all registered system-shared-memory regions.
     *&#64;&#64;
     * </pre>
     */
    public void systemSharedMemoryStatus(GrpcService.SystemSharedMemoryStatusRequest request,
        io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryStatusResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_SYSTEM_SHARED_MEMORY_STATUS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryRegister(
     *&#64;&#64;                     SystemSharedMemoryRegisterRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryRegisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Register a system-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public void systemSharedMemoryRegister(GrpcService.SystemSharedMemoryRegisterRequest request,
        io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryRegisterResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_SYSTEM_SHARED_MEMORY_REGISTER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryUnregister(
     *&#64;&#64;                     SystemSharedMemoryUnregisterRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryUnregisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Unregister a system-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public void systemSharedMemoryUnregister(GrpcService.SystemSharedMemoryUnregisterRequest request,
        io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryUnregisterResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_SYSTEM_SHARED_MEMORY_UNREGISTER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryStatus(
     *&#64;&#64;                     CudaSharedMemoryStatusRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryStatusRespose)
     *&#64;&#64;
     *&#64;&#64;     Get the status of all registered CUDA-shared-memory regions.
     *&#64;&#64;
     * </pre>
     */
    public void cudaSharedMemoryStatus(GrpcService.CudaSharedMemoryStatusRequest request,
        io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryStatusResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_CUDA_SHARED_MEMORY_STATUS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryRegister(
     *&#64;&#64;                     CudaSharedMemoryRegisterRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryRegisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Register a CUDA-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public void cudaSharedMemoryRegister(GrpcService.CudaSharedMemoryRegisterRequest request,
        io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryRegisterResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_CUDA_SHARED_MEMORY_REGISTER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryUnregister(
     *&#64;&#64;                     CudaSharedMemoryUnregisterRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryUnregisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Unregister a CUDA-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public void cudaSharedMemoryUnregister(GrpcService.CudaSharedMemoryUnregisterRequest request,
        io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryUnregisterResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_CUDA_SHARED_MEMORY_UNREGISTER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *&#64;&#64;
   *&#64;&#64;.. cpp:var:: service InferenceService
   *&#64;&#64;
   *&#64;&#64;   Inference Server GRPC endpoints.
   *&#64;&#64;
   * </pre>
   */
  public static final class GRPCInferenceServiceBlockingStub extends io.grpc.stub.AbstractStub<GRPCInferenceServiceBlockingStub> {
    private GRPCInferenceServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GRPCInferenceServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GRPCInferenceServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GRPCInferenceServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerLive(ServerLiveRequest) returns
     *&#64;&#64;       (ServerLiveResponse)
     *&#64;&#64;
     *&#64;&#64;     Check liveness of the inference server.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.ServerLiveResponse serverLive(GrpcService.ServerLiveRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SERVER_LIVE, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerReady(ServerReadyRequest) returns
     *&#64;&#64;       (ServerReadyResponse)
     *&#64;&#64;
     *&#64;&#64;     Check readiness of the inference server.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.ServerReadyResponse serverReady(GrpcService.ServerReadyRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SERVER_READY, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelReady(ModelReadyRequest) returns
     *&#64;&#64;       (ModelReadyResponse)
     *&#64;&#64;
     *&#64;&#64;     Check readiness of a model in the inference server.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.ModelReadyResponse modelReady(GrpcService.ModelReadyRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MODEL_READY, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerMetadata(ServerMetadataRequest) returns
     *&#64;&#64;       (ServerMetadataResponse)
     *&#64;&#64;
     *&#64;&#64;     Get server metadata.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.ServerMetadataResponse serverMetadata(GrpcService.ServerMetadataRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SERVER_METADATA, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelMetadata(ModelMetadataRequest) returns
     *&#64;&#64;       (ModelMetadataResponse)
     *&#64;&#64;
     *&#64;&#64;     Get model metadata.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.ModelMetadataResponse modelMetadata(GrpcService.ModelMetadataRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MODEL_METADATA, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelInfer(ModelInferRequest) returns
     *&#64;&#64;       (ModelInferResponse)
     *&#64;&#64;
     *&#64;&#64;     Perform inference using a specific model.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.ModelInferResponse modelInfer(GrpcService.ModelInferRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MODEL_INFER, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelConfig(ModelConfigRequest) returns
     *&#64;&#64;       (ModelConfigResponse)
     *&#64;&#64;
     *&#64;&#64;     Get model configuration.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.ModelConfigResponse modelConfig(GrpcService.ModelConfigRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MODEL_CONFIG, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelStatistics(
     *&#64;&#64;                     ModelStatisticsRequest)
     *&#64;&#64;                   returns (ModelStatisticsResponse)
     *&#64;&#64;
     *&#64;&#64;     Get the cumulative inference statistics for a model.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.ModelStatisticsResponse modelStatistics(GrpcService.ModelStatisticsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MODEL_STATISTICS, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryIndex(RepositoryIndexRequest) returns
     *&#64;&#64;       (RepositoryIndexResponse)
     *&#64;&#64;
     *&#64;&#64;     Get the index of model repository contents.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.RepositoryIndexResponse repositoryIndex(GrpcService.RepositoryIndexRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REPOSITORY_INDEX, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryModelLoad(RepositoryModelLoadRequest) returns
     *&#64;&#64;       (RepositoryModelLoadResponse)
     *&#64;&#64;
     *&#64;&#64;     Load or reload a model from a repository.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.RepositoryModelLoadResponse repositoryModelLoad(GrpcService.RepositoryModelLoadRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REPOSITORY_MODEL_LOAD, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryModelUnload(RepositoryModelUnloadRequest)
     *&#64;&#64;       returns (RepositoryModelUnloadResponse)
     *&#64;&#64;
     *&#64;&#64;     Unload a model.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.RepositoryModelUnloadResponse repositoryModelUnload(GrpcService.RepositoryModelUnloadRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REPOSITORY_MODEL_UNLOAD, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryStatus(
     *&#64;&#64;                     SystemSharedMemoryStatusRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryStatusRespose)
     *&#64;&#64;
     *&#64;&#64;     Get the status of all registered system-shared-memory regions.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.SystemSharedMemoryStatusResponse systemSharedMemoryStatus(GrpcService.SystemSharedMemoryStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SYSTEM_SHARED_MEMORY_STATUS, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryRegister(
     *&#64;&#64;                     SystemSharedMemoryRegisterRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryRegisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Register a system-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.SystemSharedMemoryRegisterResponse systemSharedMemoryRegister(GrpcService.SystemSharedMemoryRegisterRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SYSTEM_SHARED_MEMORY_REGISTER, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryUnregister(
     *&#64;&#64;                     SystemSharedMemoryUnregisterRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryUnregisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Unregister a system-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.SystemSharedMemoryUnregisterResponse systemSharedMemoryUnregister(GrpcService.SystemSharedMemoryUnregisterRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SYSTEM_SHARED_MEMORY_UNREGISTER, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryStatus(
     *&#64;&#64;                     CudaSharedMemoryStatusRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryStatusRespose)
     *&#64;&#64;
     *&#64;&#64;     Get the status of all registered CUDA-shared-memory regions.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.CudaSharedMemoryStatusResponse cudaSharedMemoryStatus(GrpcService.CudaSharedMemoryStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CUDA_SHARED_MEMORY_STATUS, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryRegister(
     *&#64;&#64;                     CudaSharedMemoryRegisterRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryRegisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Register a CUDA-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.CudaSharedMemoryRegisterResponse cudaSharedMemoryRegister(GrpcService.CudaSharedMemoryRegisterRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CUDA_SHARED_MEMORY_REGISTER, getCallOptions(), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryUnregister(
     *&#64;&#64;                     CudaSharedMemoryUnregisterRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryUnregisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Unregister a CUDA-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public GrpcService.CudaSharedMemoryUnregisterResponse cudaSharedMemoryUnregister(GrpcService.CudaSharedMemoryUnregisterRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CUDA_SHARED_MEMORY_UNREGISTER, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *&#64;&#64;
   *&#64;&#64;.. cpp:var:: service InferenceService
   *&#64;&#64;
   *&#64;&#64;   Inference Server GRPC endpoints.
   *&#64;&#64;
   * </pre>
   */
  public static final class GRPCInferenceServiceFutureStub extends io.grpc.stub.AbstractStub<GRPCInferenceServiceFutureStub> {
    private GRPCInferenceServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GRPCInferenceServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GRPCInferenceServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GRPCInferenceServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerLive(ServerLiveRequest) returns
     *&#64;&#64;       (ServerLiveResponse)
     *&#64;&#64;
     *&#64;&#64;     Check liveness of the inference server.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ServerLiveResponse> serverLive(
        GrpcService.ServerLiveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SERVER_LIVE, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerReady(ServerReadyRequest) returns
     *&#64;&#64;       (ServerReadyResponse)
     *&#64;&#64;
     *&#64;&#64;     Check readiness of the inference server.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ServerReadyResponse> serverReady(
        GrpcService.ServerReadyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SERVER_READY, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelReady(ModelReadyRequest) returns
     *&#64;&#64;       (ModelReadyResponse)
     *&#64;&#64;
     *&#64;&#64;     Check readiness of a model in the inference server.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ModelReadyResponse> modelReady(
        GrpcService.ModelReadyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MODEL_READY, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ServerMetadata(ServerMetadataRequest) returns
     *&#64;&#64;       (ServerMetadataResponse)
     *&#64;&#64;
     *&#64;&#64;     Get server metadata.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ServerMetadataResponse> serverMetadata(
        GrpcService.ServerMetadataRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SERVER_METADATA, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelMetadata(ModelMetadataRequest) returns
     *&#64;&#64;       (ModelMetadataResponse)
     *&#64;&#64;
     *&#64;&#64;     Get model metadata.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ModelMetadataResponse> modelMetadata(
        GrpcService.ModelMetadataRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MODEL_METADATA, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelInfer(ModelInferRequest) returns
     *&#64;&#64;       (ModelInferResponse)
     *&#64;&#64;
     *&#64;&#64;     Perform inference using a specific model.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ModelInferResponse> modelInfer(
        GrpcService.ModelInferRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MODEL_INFER, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelConfig(ModelConfigRequest) returns
     *&#64;&#64;       (ModelConfigResponse)
     *&#64;&#64;
     *&#64;&#64;     Get model configuration.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ModelConfigResponse> modelConfig(
        GrpcService.ModelConfigRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MODEL_CONFIG, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc ModelStatistics(
     *&#64;&#64;                     ModelStatisticsRequest)
     *&#64;&#64;                   returns (ModelStatisticsResponse)
     *&#64;&#64;
     *&#64;&#64;     Get the cumulative inference statistics for a model.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.ModelStatisticsResponse> modelStatistics(
        GrpcService.ModelStatisticsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MODEL_STATISTICS, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryIndex(RepositoryIndexRequest) returns
     *&#64;&#64;       (RepositoryIndexResponse)
     *&#64;&#64;
     *&#64;&#64;     Get the index of model repository contents.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.RepositoryIndexResponse> repositoryIndex(
        GrpcService.RepositoryIndexRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REPOSITORY_INDEX, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryModelLoad(RepositoryModelLoadRequest) returns
     *&#64;&#64;       (RepositoryModelLoadResponse)
     *&#64;&#64;
     *&#64;&#64;     Load or reload a model from a repository.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.RepositoryModelLoadResponse> repositoryModelLoad(
        GrpcService.RepositoryModelLoadRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REPOSITORY_MODEL_LOAD, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc RepositoryModelUnload(RepositoryModelUnloadRequest)
     *&#64;&#64;       returns (RepositoryModelUnloadResponse)
     *&#64;&#64;
     *&#64;&#64;     Unload a model.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.RepositoryModelUnloadResponse> repositoryModelUnload(
        GrpcService.RepositoryModelUnloadRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REPOSITORY_MODEL_UNLOAD, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryStatus(
     *&#64;&#64;                     SystemSharedMemoryStatusRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryStatusRespose)
     *&#64;&#64;
     *&#64;&#64;     Get the status of all registered system-shared-memory regions.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.SystemSharedMemoryStatusResponse> systemSharedMemoryStatus(
        GrpcService.SystemSharedMemoryStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SYSTEM_SHARED_MEMORY_STATUS, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryRegister(
     *&#64;&#64;                     SystemSharedMemoryRegisterRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryRegisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Register a system-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.SystemSharedMemoryRegisterResponse> systemSharedMemoryRegister(
        GrpcService.SystemSharedMemoryRegisterRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SYSTEM_SHARED_MEMORY_REGISTER, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc SystemSharedMemoryUnregister(
     *&#64;&#64;                     SystemSharedMemoryUnregisterRequest)
     *&#64;&#64;                   returns (SystemSharedMemoryUnregisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Unregister a system-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.SystemSharedMemoryUnregisterResponse> systemSharedMemoryUnregister(
        GrpcService.SystemSharedMemoryUnregisterRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SYSTEM_SHARED_MEMORY_UNREGISTER, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryStatus(
     *&#64;&#64;                     CudaSharedMemoryStatusRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryStatusRespose)
     *&#64;&#64;
     *&#64;&#64;     Get the status of all registered CUDA-shared-memory regions.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.CudaSharedMemoryStatusResponse> cudaSharedMemoryStatus(
        GrpcService.CudaSharedMemoryStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CUDA_SHARED_MEMORY_STATUS, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryRegister(
     *&#64;&#64;                     CudaSharedMemoryRegisterRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryRegisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Register a CUDA-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.CudaSharedMemoryRegisterResponse> cudaSharedMemoryRegister(
        GrpcService.CudaSharedMemoryRegisterRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CUDA_SHARED_MEMORY_REGISTER, getCallOptions()), request);
    }

    /**
     * <pre>
     *&#64;&#64;  .. cpp:var:: rpc CudaSharedMemoryUnregister(
     *&#64;&#64;                     CudaSharedMemoryUnregisterRequest)
     *&#64;&#64;                   returns (CudaSharedMemoryUnregisterResponse)
     *&#64;&#64;
     *&#64;&#64;     Unregister a CUDA-shared-memory region.
     *&#64;&#64;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcService.CudaSharedMemoryUnregisterResponse> cudaSharedMemoryUnregister(
        GrpcService.CudaSharedMemoryUnregisterRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CUDA_SHARED_MEMORY_UNREGISTER, getCallOptions()), request);
    }
  }

  private static final int METHODID_SERVER_LIVE = 0;
  private static final int METHODID_SERVER_READY = 1;
  private static final int METHODID_MODEL_READY = 2;
  private static final int METHODID_SERVER_METADATA = 3;
  private static final int METHODID_MODEL_METADATA = 4;
  private static final int METHODID_MODEL_INFER = 5;
  private static final int METHODID_MODEL_CONFIG = 6;
  private static final int METHODID_MODEL_STATISTICS = 7;
  private static final int METHODID_REPOSITORY_INDEX = 8;
  private static final int METHODID_REPOSITORY_MODEL_LOAD = 9;
  private static final int METHODID_REPOSITORY_MODEL_UNLOAD = 10;
  private static final int METHODID_SYSTEM_SHARED_MEMORY_STATUS = 11;
  private static final int METHODID_SYSTEM_SHARED_MEMORY_REGISTER = 12;
  private static final int METHODID_SYSTEM_SHARED_MEMORY_UNREGISTER = 13;
  private static final int METHODID_CUDA_SHARED_MEMORY_STATUS = 14;
  private static final int METHODID_CUDA_SHARED_MEMORY_REGISTER = 15;
  private static final int METHODID_CUDA_SHARED_MEMORY_UNREGISTER = 16;
  private static final int METHODID_MODEL_STREAM_INFER = 17;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GRPCInferenceServiceImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(GRPCInferenceServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SERVER_LIVE:
          serviceImpl.serverLive((GrpcService.ServerLiveRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ServerLiveResponse>) responseObserver);
          break;
        case METHODID_SERVER_READY:
          serviceImpl.serverReady((GrpcService.ServerReadyRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ServerReadyResponse>) responseObserver);
          break;
        case METHODID_MODEL_READY:
          serviceImpl.modelReady((GrpcService.ModelReadyRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ModelReadyResponse>) responseObserver);
          break;
        case METHODID_SERVER_METADATA:
          serviceImpl.serverMetadata((GrpcService.ServerMetadataRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ServerMetadataResponse>) responseObserver);
          break;
        case METHODID_MODEL_METADATA:
          serviceImpl.modelMetadata((GrpcService.ModelMetadataRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ModelMetadataResponse>) responseObserver);
          break;
        case METHODID_MODEL_INFER:
          serviceImpl.modelInfer((GrpcService.ModelInferRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ModelInferResponse>) responseObserver);
          break;
        case METHODID_MODEL_CONFIG:
          serviceImpl.modelConfig((GrpcService.ModelConfigRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ModelConfigResponse>) responseObserver);
          break;
        case METHODID_MODEL_STATISTICS:
          serviceImpl.modelStatistics((GrpcService.ModelStatisticsRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.ModelStatisticsResponse>) responseObserver);
          break;
        case METHODID_REPOSITORY_INDEX:
          serviceImpl.repositoryIndex((GrpcService.RepositoryIndexRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.RepositoryIndexResponse>) responseObserver);
          break;
        case METHODID_REPOSITORY_MODEL_LOAD:
          serviceImpl.repositoryModelLoad((GrpcService.RepositoryModelLoadRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.RepositoryModelLoadResponse>) responseObserver);
          break;
        case METHODID_REPOSITORY_MODEL_UNLOAD:
          serviceImpl.repositoryModelUnload((GrpcService.RepositoryModelUnloadRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.RepositoryModelUnloadResponse>) responseObserver);
          break;
        case METHODID_SYSTEM_SHARED_MEMORY_STATUS:
          serviceImpl.systemSharedMemoryStatus((GrpcService.SystemSharedMemoryStatusRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryStatusResponse>) responseObserver);
          break;
        case METHODID_SYSTEM_SHARED_MEMORY_REGISTER:
          serviceImpl.systemSharedMemoryRegister((GrpcService.SystemSharedMemoryRegisterRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryRegisterResponse>) responseObserver);
          break;
        case METHODID_SYSTEM_SHARED_MEMORY_UNREGISTER:
          serviceImpl.systemSharedMemoryUnregister((GrpcService.SystemSharedMemoryUnregisterRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.SystemSharedMemoryUnregisterResponse>) responseObserver);
          break;
        case METHODID_CUDA_SHARED_MEMORY_STATUS:
          serviceImpl.cudaSharedMemoryStatus((GrpcService.CudaSharedMemoryStatusRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryStatusResponse>) responseObserver);
          break;
        case METHODID_CUDA_SHARED_MEMORY_REGISTER:
          serviceImpl.cudaSharedMemoryRegister((GrpcService.CudaSharedMemoryRegisterRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryRegisterResponse>) responseObserver);
          break;
        case METHODID_CUDA_SHARED_MEMORY_UNREGISTER:
          serviceImpl.cudaSharedMemoryUnregister((GrpcService.CudaSharedMemoryUnregisterRequest) request,
              (io.grpc.stub.StreamObserver<GrpcService.CudaSharedMemoryUnregisterResponse>) responseObserver);
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
        case METHODID_MODEL_STREAM_INFER:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.modelStreamInfer(
              (io.grpc.stub.StreamObserver<GrpcService.ModelStreamInferResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_SERVER_LIVE,
        METHOD_SERVER_READY,
        METHOD_MODEL_READY,
        METHOD_SERVER_METADATA,
        METHOD_MODEL_METADATA,
        METHOD_MODEL_INFER,
        METHOD_MODEL_STREAM_INFER,
        METHOD_MODEL_CONFIG,
        METHOD_MODEL_STATISTICS,
        METHOD_REPOSITORY_INDEX,
        METHOD_REPOSITORY_MODEL_LOAD,
        METHOD_REPOSITORY_MODEL_UNLOAD,
        METHOD_SYSTEM_SHARED_MEMORY_STATUS,
        METHOD_SYSTEM_SHARED_MEMORY_REGISTER,
        METHOD_SYSTEM_SHARED_MEMORY_UNREGISTER,
        METHOD_CUDA_SHARED_MEMORY_STATUS,
        METHOD_CUDA_SHARED_MEMORY_REGISTER,
        METHOD_CUDA_SHARED_MEMORY_UNREGISTER);
  }

}
