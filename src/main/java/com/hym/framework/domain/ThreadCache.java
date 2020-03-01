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
package com.hym.framework.domain;

/**
 *　线程缓存
 *
 * @author lijun kou
 * @date 27/02/2020
 */
public class ThreadCache {
	private static ThreadLocal<RequestData> threadLocal = new ThreadLocal<>();

	public static RequestData getPostRequestParams() {
		return threadLocal.get();
	}

	public static void setPostRequestParams(RequestData postRequestParams){
		threadLocal.set(postRequestParams);
	}

	public static void removePostRequestParams(){
		threadLocal.remove();
	}
}
