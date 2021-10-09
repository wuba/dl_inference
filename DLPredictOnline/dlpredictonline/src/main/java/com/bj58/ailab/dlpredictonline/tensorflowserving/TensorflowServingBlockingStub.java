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
import com.bj58.ailab.dlpredictonline.config.Configurations;
import com.bj58.ailab.dlpredictonline.util.Md5Util;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 后端节点连接管理
 * @author 58
 */
public class TensorflowServingBlockingStub {

    private static Logger logger = LoggerFactory.getLogger(TensorflowServingBlockingStub.class);

    private static Map<String, List<BlockingStubEntity>> blockingStubMap = new ConcurrentHashMap<>();
    private static Map<String, String> md5LineMap = new ConcurrentHashMap<>();
    private static Map<String, Integer> timeoutMap = new ConcurrentHashMap<>();

    /**
     * GRPC连接最大包大小, 默认10M
     */
    private static int maxMessageSize = Configurations.getProperty("predict.grpc.maxMessageSize", 1024 * 1024 * 10, false);

    private final static String KEY_SPLIT = "=";
    private final static String KEY_IP_SPLIT = ",";
    private final static String KEY_IP_PORT_SPLIT = ":";

    private final static int linesLength = 2;

    public static void init() throws Exception{
        String nodeFile = System.getProperty("user.dir") + File.separator + "config/nodefile.txt";
        nodeFile = Configurations.getProperty("predict.online.node.file", nodeFile, false);
        String finalNodeFile = nodeFile;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                File file = new File(finalNodeFile);
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("#")){
                            continue;
                        }
                        updateByLine(line);
                    }
                } catch (Exception e){
                    logger.error("read file error, msg=" + e.getMessage());
                }finally {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("nodeFile-pool-%d").build();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, namedThreadFactory);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, 0L, 10, TimeUnit.SECONDS);
        logger.info("read node file thread pool start");
    }

    /**
     * 检查当前行是否需要更新
     * 需要更新则生成链接更新到缓存map中
     * 不需要更新则返回，不做任何操作
     * @param line 当前行
     */
    public static void updateByLine(String line){
        if (!line.contains(KEY_SPLIT)){
            return;
        }
        String[] lines = line.split(KEY_SPLIT);

        if (lines.length > linesLength){
            return;
        }
        String taskId = lines[0];
        String md5Line = Md5Util.getMd5(line);
        if (!md5LineMap.containsKey(taskId)){
            List<BlockingStubEntity> blockingStubEntityList = getBlockingListByLine(lines[1]);
            blockingStubMap.put(taskId, blockingStubEntityList);
            md5LineMap.put(taskId, md5Line);
            updateTimeOutByLine(taskId, lines[1]);
            logger.info("updateByLine add success, taskId={}", taskId);
        } else if (!md5Line.equals(md5LineMap.get(taskId))){
            List<BlockingStubEntity> blockingStubEntityList = blockingStubMap.get(taskId);
            List<BlockingStubEntity> newBlockingStubEntityList = getBlockingListByLine(lines[1]);
            blockingStubMap.put(taskId, newBlockingStubEntityList);
            for (BlockingStubEntity blockingStubEntity : blockingStubEntityList){
                blockingStubEntity.getChannel().shutdown();
            }
            md5LineMap.put(taskId, md5Line);
            updateTimeOutByLine(taskId, lines[1]);
            logger.info("updateByLine update success, taskId={}", taskId);
        }

    }

    /**
     * 获取任务超时时间配置
     * @param taskid 任务id
     * @return timeout 超时时间，单位ms
     */
    public static int getTimeoutByTaskid(String taskid){
        if (!timeoutMap.containsKey(taskid)){
            return Config.DEFAULT_STUB_TIMEOUT;
        }
        return timeoutMap.get(taskid).intValue();
    }

    /**
     * 文件行提取timeout参数配置
     * KEY_IP_SPLIT 分隔第0个表示 超时时间配置
     * @param taskId 任务编号
     * @param line 文件行
     */
    public static void updateTimeOutByLine(String taskId, String line){
        String[] ipPorts = line.split(KEY_IP_SPLIT);
        try {
            Integer timeOut = Integer.parseInt(ipPorts[0]);
            if (timeoutMap.containsKey(taskId)){
                if (timeoutMap.get(taskId).intValue() != timeOut.intValue()) {
                    timeoutMap.put(taskId, timeOut);
                }
            }else {
                timeoutMap.put(taskId, timeOut);
            }
        } catch (Exception e){
            logger.error("Integer.parseInt timeOut error, taskId={}, msg={}", taskId, e.getMessage());
        }
    }
    /**
     * 文件行提取ip+port信息，构造BlockingStubEntity放在List中
     * @param line 文件行
     * @return List BlockingStubEntity
     */
    public static List<BlockingStubEntity> getBlockingListByLine(String line){
        List<BlockingStubEntity> blockingStubEntityList = new ArrayList<>();
        String[] ipPorts = line.split(KEY_IP_SPLIT);
        for (int i = 1; i < ipPorts.length; i++){
            String ipPort = ipPorts[i];
            if (!ipPort.contains(KEY_IP_PORT_SPLIT)) {
                continue;
            }
            String ip = ipPort.split(KEY_IP_PORT_SPLIT)[0];
            String port = ipPort.split(KEY_IP_PORT_SPLIT)[1];
            BlockingStubEntity blockingStubEntity = getBlockingStub(ip, port);
            if (blockingStubEntity == null){
                continue;
            }
            blockingStubEntityList.add(blockingStubEntity);
        }
        return blockingStubEntityList;
    }

    /**
     * 根据ip port得到BlockingStub
     * @param ip ip地址
     * @param port 端口
     * @return BlockingStubEntity
     */
    public static BlockingStubEntity getBlockingStub(String ip, String port){
        int portInt;
        try{
            portInt = Integer.parseInt(port);
        } catch (Exception e){
            logger.info("getBlockingStub port not Integer ip=" + ip + " port=" + port);
            return null;
        }
        BlockingStubEntity blockingStubEntity = new BlockingStubEntity();
        blockingStubEntity.setIp(ip);
        blockingStubEntity.setPort(portInt);
        ManagedChannel channel = NettyChannelBuilder.forAddress(ip, portInt)
            // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
            // needing certificates.
            .usePlaintext()
            .maxInboundMessageSize(maxMessageSize)
            .build();
        blockingStubEntity.setChannel(channel);
        return blockingStubEntity;
    }

    /**
     * 动态轮询算法获取链接
     * @param taskId 任务编号
     * @return BlockingStubEntity后端链接
     */
    public static BlockingStubEntity getBlockingStubByRandom(int taskId){
        String taskIdStr = taskId + "";
        if (!blockingStubMap.containsKey(taskIdStr)){
            return null;
        }
        List<BlockingStubEntity> stubEntities = blockingStubMap.get(taskIdStr);
        int size = stubEntities.size();
        if (size == 0){
            return null;
        }
        long start = System.currentTimeMillis();
        BlockingStubEntity blockingStubEntity = null;
        synchronized (stubEntities) {
            int totle = 0;
            for (BlockingStubEntity stubEntity : stubEntities){
                stubEntity.currentWeight += stubEntity.effectiveWeight;
                totle += stubEntity.effectiveWeight;
                if (blockingStubEntity == null || blockingStubEntity.currentWeight < stubEntity.currentWeight){
                    blockingStubEntity = stubEntity;
                }
            }
            blockingStubEntity.currentWeight -= totle;
        }
        long end = System.currentTimeMillis();
        if (!blockingStubEntity.getChannel().isShutdown() &&
            !blockingStubEntity.getChannel().isTerminated()){

            logger.info("getBlockingStubByRandom taskid={}, ip={}, currentWeight={}, "
                    + "effectiveWeight={}, spend={}", taskId, blockingStubEntity.getIp(),
                blockingStubEntity.currentWeight, blockingStubEntity.effectiveWeight, (end - start));

            return blockingStubEntity;
        }
        if (blockingStubEntity.isShutDown()){
            // 主动shutdown
            return null;
        }
        BlockingStubEntity blockingStubEntityNew = getBlockingStub(blockingStubEntity.getIp(),
            blockingStubEntity.getPort() + "");
        blockingStubEntity.setChannel(blockingStubEntityNew.getChannel());
        logger.info("channel shutdown getBlockingStubByRandom taskid={}, ip={}, currentWeight={}, "
                + "effectiveWeight={}, spend={}", taskId, blockingStubEntity.getIp(),
            blockingStubEntity.currentWeight, blockingStubEntity.effectiveWeight, (end - start));
        return blockingStubEntity;
    }

    /**
     * 更新有效权重
     */
    public static void updateWeight(BlockingStubEntity blockingStubEntity, boolean isOk){
        if (blockingStubEntity == null){
            return;
        }
        synchronized (blockingStubEntity) {
            // 节点正常，升权
            if (isOk) {
                // 达到权重最大值， 不进行升权操作
                if (blockingStubEntity.effectiveWeight >= blockingStubEntity.weight) {
                    return;
                }
                // 升权，快速升权
                blockingStubEntity.effectiveWeight +=
                    (blockingStubEntity.weight - blockingStubEntity.effectiveWeight + 1) >> 1;
            } else {
                // 降权, 权重达到最小值 1， 不进行降权
                if (blockingStubEntity.effectiveWeight == 1) {
                    return;
                }
                // 降权
                blockingStubEntity.effectiveWeight >>= 1;
            }
        }
    }
}
