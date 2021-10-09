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

/**
 * 项目相关参数配置
 * @author 58
 */
public class Config {

    /**
     * stub超时时间默认值，单位毫秒
     */
    public static final int DEFAULT_STUB_TIMEOUT = 100;
    /**
     * 异常重试次数
     */
    public static final int DEFAULT_RETRY_NUM = 2;
    /**
     * 模型名称分割线 e.g  tensorflow-666
     */
    public static final String SEPARATOR_LINE = "-";
}
