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

import com.bj58.ailab.dlpredictonline.config.Config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 工具类
 * @author: 58
 **/
public class CommonUtils {

    public static boolean checkFileExists(String file) {
        Path path = Paths.get(file);

        if (Files.exists(path)) {
            return true;
        }
        return false;
    }

    /**
     * 从模型名称提取任务ID
     * @param modelName
     * @return
     */
    public static int extractTaskIdFromModelName(String modelName){
        String[] modelNames = modelName.split(Config.SEPARATOR_LINE);
        int taskId = Integer.parseInt(modelNames[modelNames.length - 1]);
        return taskId;
    }
}
