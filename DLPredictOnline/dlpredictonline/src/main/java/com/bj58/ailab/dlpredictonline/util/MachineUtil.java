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


package com.bj58.ailab.dlpredictonline.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务器信息获取
 * @author 58
 */
public class MachineUtil {

    private static Logger logger = LoggerFactory.getLogger(MachineUtil.class);

    private static volatile String ip = null;

    /**
     * 获取服务器ip
     * @return 本机IP地址
     */
    public static String getHostIp(){
        if(ip != null){
            return ip;
        }
        InetAddress netAddress = getInetAddress();
        if (netAddress == null){
            ip = "unknown";
        }else {
            ip = netAddress.getHostAddress();
        }
        return ip;
    }

    private static InetAddress getInetAddress(){
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            logger.error("getInetAddress error, unknown host!");
        }
        return null;
    }
}
