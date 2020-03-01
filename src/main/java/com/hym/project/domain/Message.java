/*
 * Copyright Dingxuan. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

		 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.hym.project.domain;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

/**
 * 受理平台中所有消息父类
 *
 * @author sunzongyu
 * @date 2018/11/09
 * @company Dingxuan
 */
@Component
public class Message {
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String toString(boolean esc) {
		return JSON.toJSONString(this).replaceAll("\\\\", "");
	}
}
