package com.cloudplatform.integration.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Внутрішній контракт інтеграції замовлень зі складською системою.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OrderIntegrationServiceGrpc {

  private OrderIntegrationServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ecommerce.integration.v1.OrderIntegrationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.ReserveInventoryRequest,
      com.cloudplatform.integration.v1.OperationResponse> getReserveInventoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReserveInventory",
      requestType = com.cloudplatform.integration.v1.ReserveInventoryRequest.class,
      responseType = com.cloudplatform.integration.v1.OperationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.ReserveInventoryRequest,
      com.cloudplatform.integration.v1.OperationResponse> getReserveInventoryMethod() {
    io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.ReserveInventoryRequest, com.cloudplatform.integration.v1.OperationResponse> getReserveInventoryMethod;
    if ((getReserveInventoryMethod = OrderIntegrationServiceGrpc.getReserveInventoryMethod) == null) {
      synchronized (OrderIntegrationServiceGrpc.class) {
        if ((getReserveInventoryMethod = OrderIntegrationServiceGrpc.getReserveInventoryMethod) == null) {
          OrderIntegrationServiceGrpc.getReserveInventoryMethod = getReserveInventoryMethod =
              io.grpc.MethodDescriptor.<com.cloudplatform.integration.v1.ReserveInventoryRequest, com.cloudplatform.integration.v1.OperationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReserveInventory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudplatform.integration.v1.ReserveInventoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudplatform.integration.v1.OperationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OrderIntegrationServiceMethodDescriptorSupplier("ReserveInventory"))
              .build();
        }
      }
    }
    return getReserveInventoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.GetOperationRequest,
      com.cloudplatform.integration.v1.OperationResponse> getGetOperationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOperation",
      requestType = com.cloudplatform.integration.v1.GetOperationRequest.class,
      responseType = com.cloudplatform.integration.v1.OperationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.GetOperationRequest,
      com.cloudplatform.integration.v1.OperationResponse> getGetOperationMethod() {
    io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.GetOperationRequest, com.cloudplatform.integration.v1.OperationResponse> getGetOperationMethod;
    if ((getGetOperationMethod = OrderIntegrationServiceGrpc.getGetOperationMethod) == null) {
      synchronized (OrderIntegrationServiceGrpc.class) {
        if ((getGetOperationMethod = OrderIntegrationServiceGrpc.getGetOperationMethod) == null) {
          OrderIntegrationServiceGrpc.getGetOperationMethod = getGetOperationMethod =
              io.grpc.MethodDescriptor.<com.cloudplatform.integration.v1.GetOperationRequest, com.cloudplatform.integration.v1.OperationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOperation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudplatform.integration.v1.GetOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudplatform.integration.v1.OperationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OrderIntegrationServiceMethodDescriptorSupplier("GetOperation"))
              .build();
        }
      }
    }
    return getGetOperationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.WatchOperationRequest,
      com.cloudplatform.integration.v1.OperationStatusEvent> getWatchOperationStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "WatchOperationStatus",
      requestType = com.cloudplatform.integration.v1.WatchOperationRequest.class,
      responseType = com.cloudplatform.integration.v1.OperationStatusEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.WatchOperationRequest,
      com.cloudplatform.integration.v1.OperationStatusEvent> getWatchOperationStatusMethod() {
    io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.WatchOperationRequest, com.cloudplatform.integration.v1.OperationStatusEvent> getWatchOperationStatusMethod;
    if ((getWatchOperationStatusMethod = OrderIntegrationServiceGrpc.getWatchOperationStatusMethod) == null) {
      synchronized (OrderIntegrationServiceGrpc.class) {
        if ((getWatchOperationStatusMethod = OrderIntegrationServiceGrpc.getWatchOperationStatusMethod) == null) {
          OrderIntegrationServiceGrpc.getWatchOperationStatusMethod = getWatchOperationStatusMethod =
              io.grpc.MethodDescriptor.<com.cloudplatform.integration.v1.WatchOperationRequest, com.cloudplatform.integration.v1.OperationStatusEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "WatchOperationStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudplatform.integration.v1.WatchOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudplatform.integration.v1.OperationStatusEvent.getDefaultInstance()))
              .setSchemaDescriptor(new OrderIntegrationServiceMethodDescriptorSupplier("WatchOperationStatus"))
              .build();
        }
      }
    }
    return getWatchOperationStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.CancelOperationRequest,
      com.cloudplatform.integration.v1.OperationResponse> getCancelOperationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CancelOperation",
      requestType = com.cloudplatform.integration.v1.CancelOperationRequest.class,
      responseType = com.cloudplatform.integration.v1.OperationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.CancelOperationRequest,
      com.cloudplatform.integration.v1.OperationResponse> getCancelOperationMethod() {
    io.grpc.MethodDescriptor<com.cloudplatform.integration.v1.CancelOperationRequest, com.cloudplatform.integration.v1.OperationResponse> getCancelOperationMethod;
    if ((getCancelOperationMethod = OrderIntegrationServiceGrpc.getCancelOperationMethod) == null) {
      synchronized (OrderIntegrationServiceGrpc.class) {
        if ((getCancelOperationMethod = OrderIntegrationServiceGrpc.getCancelOperationMethod) == null) {
          OrderIntegrationServiceGrpc.getCancelOperationMethod = getCancelOperationMethod =
              io.grpc.MethodDescriptor.<com.cloudplatform.integration.v1.CancelOperationRequest, com.cloudplatform.integration.v1.OperationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CancelOperation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudplatform.integration.v1.CancelOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudplatform.integration.v1.OperationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OrderIntegrationServiceMethodDescriptorSupplier("CancelOperation"))
              .build();
        }
      }
    }
    return getCancelOperationMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OrderIntegrationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderIntegrationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderIntegrationServiceStub>() {
        @java.lang.Override
        public OrderIntegrationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderIntegrationServiceStub(channel, callOptions);
        }
      };
    return OrderIntegrationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OrderIntegrationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderIntegrationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderIntegrationServiceBlockingStub>() {
        @java.lang.Override
        public OrderIntegrationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderIntegrationServiceBlockingStub(channel, callOptions);
        }
      };
    return OrderIntegrationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OrderIntegrationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderIntegrationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderIntegrationServiceFutureStub>() {
        @java.lang.Override
        public OrderIntegrationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderIntegrationServiceFutureStub(channel, callOptions);
        }
      };
    return OrderIntegrationServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Внутрішній контракт інтеграції замовлень зі складською системою.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Unary: резервування товарів на складі.
     * </pre>
     */
    default void reserveInventory(com.cloudplatform.integration.v1.ReserveInventoryRequest request,
        io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReserveInventoryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Unary: отримання поточного стану операції.
     * </pre>
     */
    default void getOperation(com.cloudplatform.integration.v1.GetOperationRequest request,
        io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOperationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server Streaming: спостереження за змінами стану операції.
     * </pre>
     */
    default void watchOperationStatus(com.cloudplatform.integration.v1.WatchOperationRequest request,
        io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationStatusEvent> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWatchOperationStatusMethod(), responseObserver);
    }

    /**
     * <pre>
     * Додатковий RPC для скасування операції.
     * </pre>
     */
    default void cancelOperation(com.cloudplatform.integration.v1.CancelOperationRequest request,
        io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCancelOperationMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service OrderIntegrationService.
   * <pre>
   * Внутрішній контракт інтеграції замовлень зі складською системою.
   * </pre>
   */
  public static abstract class OrderIntegrationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return OrderIntegrationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service OrderIntegrationService.
   * <pre>
   * Внутрішній контракт інтеграції замовлень зі складською системою.
   * </pre>
   */
  public static final class OrderIntegrationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<OrderIntegrationServiceStub> {
    private OrderIntegrationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderIntegrationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderIntegrationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: резервування товарів на складі.
     * </pre>
     */
    public void reserveInventory(com.cloudplatform.integration.v1.ReserveInventoryRequest request,
        io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReserveInventoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Unary: отримання поточного стану операції.
     * </pre>
     */
    public void getOperation(com.cloudplatform.integration.v1.GetOperationRequest request,
        io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOperationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server Streaming: спостереження за змінами стану операції.
     * </pre>
     */
    public void watchOperationStatus(com.cloudplatform.integration.v1.WatchOperationRequest request,
        io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationStatusEvent> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getWatchOperationStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Додатковий RPC для скасування операції.
     * </pre>
     */
    public void cancelOperation(com.cloudplatform.integration.v1.CancelOperationRequest request,
        io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCancelOperationMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service OrderIntegrationService.
   * <pre>
   * Внутрішній контракт інтеграції замовлень зі складською системою.
   * </pre>
   */
  public static final class OrderIntegrationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<OrderIntegrationServiceBlockingStub> {
    private OrderIntegrationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderIntegrationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderIntegrationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: резервування товарів на складі.
     * </pre>
     */
    public com.cloudplatform.integration.v1.OperationResponse reserveInventory(com.cloudplatform.integration.v1.ReserveInventoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReserveInventoryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Unary: отримання поточного стану операції.
     * </pre>
     */
    public com.cloudplatform.integration.v1.OperationResponse getOperation(com.cloudplatform.integration.v1.GetOperationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOperationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server Streaming: спостереження за змінами стану операції.
     * </pre>
     */
    public java.util.Iterator<com.cloudplatform.integration.v1.OperationStatusEvent> watchOperationStatus(
        com.cloudplatform.integration.v1.WatchOperationRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getWatchOperationStatusMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Додатковий RPC для скасування операції.
     * </pre>
     */
    public com.cloudplatform.integration.v1.OperationResponse cancelOperation(com.cloudplatform.integration.v1.CancelOperationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCancelOperationMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service OrderIntegrationService.
   * <pre>
   * Внутрішній контракт інтеграції замовлень зі складською системою.
   * </pre>
   */
  public static final class OrderIntegrationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<OrderIntegrationServiceFutureStub> {
    private OrderIntegrationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderIntegrationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderIntegrationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: резервування товарів на складі.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cloudplatform.integration.v1.OperationResponse> reserveInventory(
        com.cloudplatform.integration.v1.ReserveInventoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReserveInventoryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Unary: отримання поточного стану операції.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cloudplatform.integration.v1.OperationResponse> getOperation(
        com.cloudplatform.integration.v1.GetOperationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOperationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Додатковий RPC для скасування операції.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cloudplatform.integration.v1.OperationResponse> cancelOperation(
        com.cloudplatform.integration.v1.CancelOperationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCancelOperationMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RESERVE_INVENTORY = 0;
  private static final int METHODID_GET_OPERATION = 1;
  private static final int METHODID_WATCH_OPERATION_STATUS = 2;
  private static final int METHODID_CANCEL_OPERATION = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RESERVE_INVENTORY:
          serviceImpl.reserveInventory((com.cloudplatform.integration.v1.ReserveInventoryRequest) request,
              (io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse>) responseObserver);
          break;
        case METHODID_GET_OPERATION:
          serviceImpl.getOperation((com.cloudplatform.integration.v1.GetOperationRequest) request,
              (io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse>) responseObserver);
          break;
        case METHODID_WATCH_OPERATION_STATUS:
          serviceImpl.watchOperationStatus((com.cloudplatform.integration.v1.WatchOperationRequest) request,
              (io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationStatusEvent>) responseObserver);
          break;
        case METHODID_CANCEL_OPERATION:
          serviceImpl.cancelOperation((com.cloudplatform.integration.v1.CancelOperationRequest) request,
              (io.grpc.stub.StreamObserver<com.cloudplatform.integration.v1.OperationResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getReserveInventoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.cloudplatform.integration.v1.ReserveInventoryRequest,
              com.cloudplatform.integration.v1.OperationResponse>(
                service, METHODID_RESERVE_INVENTORY)))
        .addMethod(
          getGetOperationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.cloudplatform.integration.v1.GetOperationRequest,
              com.cloudplatform.integration.v1.OperationResponse>(
                service, METHODID_GET_OPERATION)))
        .addMethod(
          getWatchOperationStatusMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.cloudplatform.integration.v1.WatchOperationRequest,
              com.cloudplatform.integration.v1.OperationStatusEvent>(
                service, METHODID_WATCH_OPERATION_STATUS)))
        .addMethod(
          getCancelOperationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.cloudplatform.integration.v1.CancelOperationRequest,
              com.cloudplatform.integration.v1.OperationResponse>(
                service, METHODID_CANCEL_OPERATION)))
        .build();
  }

  private static abstract class OrderIntegrationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OrderIntegrationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cloudplatform.integration.v1.OrderIntegrationProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OrderIntegrationService");
    }
  }

  private static final class OrderIntegrationServiceFileDescriptorSupplier
      extends OrderIntegrationServiceBaseDescriptorSupplier {
    OrderIntegrationServiceFileDescriptorSupplier() {}
  }

  private static final class OrderIntegrationServiceMethodDescriptorSupplier
      extends OrderIntegrationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    OrderIntegrationServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OrderIntegrationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OrderIntegrationServiceFileDescriptorSupplier())
              .addMethod(getReserveInventoryMethod())
              .addMethod(getGetOperationMethod())
              .addMethod(getWatchOperationStatusMethod())
              .addMethod(getCancelOperationMethod())
              .build();
        }
      }
    }
    return result;
  }
}
