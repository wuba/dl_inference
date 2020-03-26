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


package com.bj58.ailab.dlpredictonline.config;

import com.bj58.ailab.dlpredictonline.util.CommonUtils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载配置文件，获取配置
 * @author 58
 */
public class Configurations {

    private static final Logger logger = LoggerFactory.getLogger(Configurations.class);

    private static Properties systemProperties;
    private static String configurationFile;

    public static void init() {
        String configFile = System.getProperty("user.dir") + File.separator + "config/config.properties";
        String osName = System.getProperties().getProperty("os.name");
        String win = "WIN";
        if (osName.toUpperCase().contains(win)){
            configFile = "dlpredictonline/src/main/resources/config.properties";
        }
        Path confPath = Paths.get(configFile);
        if (!CommonUtils.checkFileExists(configFile)) {
            logger.error("Cannot locate configuration file {}. You can specify it by -Dior.config=<a reachable configuration>", confPath.toAbsolutePath());
            System.exit(-1);
        }

        systemProperties = new Properties();
        try {
            systemProperties.load(new FileReader(configFile));
        } catch (IOException e) {
            logger.warn("Exception occurs while loading system configuration file {}", confPath.toAbsolutePath());
            System.exit(-1);
        }

        configurationFile = confPath.toAbsolutePath().toString();
    }


    public static String getProperty(String key, String defVal, boolean exit) {
        if (systemProperties.containsKey(key)) {
            return systemProperties.getProperty(key);
        }

        if (System.getProperties().containsKey(key)) {
            return System.getProperty(key);
        }

        if (exit) {
            logger.warn("Cannot found configuration property {}, please check whether you specified in your configuration file", key);
            System.exit(-1);
        }

        return defVal;
    }


    public static int getProperty(String key, int defVal, boolean exit) {
        try {
            String val = getProperty(key, defVal + "", exit);
            return Integer.parseInt(val);
        } catch (Exception e) {
            logger.error("getProperty int error, key={}", key);
        }
        return defVal;
    }

    public static long getProperty(String key, long defVal, boolean exit) {
        try {
            String val = getProperty(key, defVal + "", exit);
            return Long.parseLong(val);
        } catch (Exception e) {
            logger.error("getProperty long error, key={}", key);
        }
        return defVal;
    }


    public static float getProperty(String key, float defVal, boolean exit) {
        try {
            String val = getProperty(key, defVal + "", exit);
            return Float.parseFloat(val);
        } catch (Exception e) {
            logger.error("getProperty float error, key={}", key);
        }
        return defVal;
    }

    public static double getProperty(String key, double defVal, boolean exit) {
        try {
            String val = getProperty(key, defVal + "", exit);
            return Double.parseDouble(val);
        } catch (Exception e) {
            logger.error("getProperty double error, key={}", key);
        }
        return defVal;
    }


    public static boolean getProperty(String key, boolean defVal, boolean exit) {
        try {
            String val = getProperty(key, defVal + "", exit);
            return Boolean.valueOf(val);
        } catch (Exception e) {
            logger.error("getProperty boolean error, key={}", key);
        }
        return defVal;
    }
}
