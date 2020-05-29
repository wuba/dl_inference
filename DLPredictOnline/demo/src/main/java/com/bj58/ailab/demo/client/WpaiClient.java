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

import com.bj58.ailab.dlpredictonline.grpc.WpaiDLPredictOnlineServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;

/**
 * @Description 客户端，测试服务
 * @author 58
 * @Date 2019/12/30
 **/
public class WpaiClient {
    private static ManagedChannel channel = null;
    /**
     * 阻塞/同步 的stub(存根)
     */
    private static WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceBlockingStub blockingStub = null;
    /**
     * 非阻塞/异步 的stub
     */
    private static WpaiDLPredictOnlineServiceGrpc.WpaiDLPredictOnlineServiceStub async = null;

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) {
        int argsLength = 2;
        if (args.length < argsLength){
            System.out.println("arge error, args example: ip port");
            System.exit(1);
        }
        String ip = args[0];
        int port = -1;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception e){
            System.err.println("Integer.parseInt args[1] error, args[1] is " + args[1]);
        }
        WpaiClient client = new WpaiClient(ip, port);
        String user = "world";
        try {
            client.greet(user);
            client.shutdown();
        } catch (InterruptedException e) {
            System.err.println("InterruptedException, msg=" + e.getMessage());
        }
    }

    public WpaiClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .maxInboundMessageSize(10485760)
            .build());
    }

    public WpaiClient(ManagedChannel channel1) {
        channel = channel1;

        blockingStub = WpaiDLPredictOnlineServiceGrpc.newBlockingStub(channel);
        async = WpaiDLPredictOnlineServiceGrpc.newStub(channel);
    }

    public void greet(String name) {
        TensorflowDssm.tensorflowClient(blockingStub);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
