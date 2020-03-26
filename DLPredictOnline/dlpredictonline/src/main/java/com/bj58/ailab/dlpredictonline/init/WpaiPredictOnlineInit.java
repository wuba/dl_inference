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


package com.bj58.ailab.dlpredictonline.init;

import com.bj58.ailab.dlpredictonline.tensorflowserving.TensorflowServingBlockingStub;
import com.bj58.ailab.dlpredictonline.config.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化操作
 * 服务启动加载
 * @author 58
 */
public class WpaiPredictOnlineInit {

    private static final Logger logger = LoggerFactory.getLogger(WpaiPredictOnlineInit.class);

    public static void init() {
        logger.info("WpaiPredictOnlineInit.init start");
        try{
            // 配置文件加载
            Configurations.init();
            // 初始化节点信息配置
            TensorflowServingBlockingStub.init();
            logger.info("WpaiPredictOnlineInit.init end");
        } catch (Exception e){
            logger.error("WpaiPredictOnlineInit.init failed {}", e.getMessage());
            System.exit(-1);
        }
    }

}
