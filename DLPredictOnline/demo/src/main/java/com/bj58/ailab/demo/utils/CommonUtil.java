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


package com.bj58.ailab.demo.utils;

/**
 * 常用工具类
 * @author 58
 **/
public class CommonUtil {

    private static final String OS_NAME_KEY = "os.name";
    private static final String WIN_KEY = "win";
    /**
     * 检查系统是否是win系统
     * @return true win系统
     *         false 非win系统，可默认为linux系统
     */
    public static boolean checkSystemIsWin(){
        return System.getProperty(OS_NAME_KEY).toLowerCase().contains(WIN_KEY);
    }
}
