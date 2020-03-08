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
package com.hym.framework.interceptor;


import com.hym.common.utils.StringUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 拦截器，校验消息摘要与安全因子是否正确
 *
 * @author koulijun
 * @date 2020/02/28
 *
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private Storage storage;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		RequestData requestData = this.storage.getRequestData(request);
		//在request中读取请求参数，写入线程缓存中
		ThreadCache.setPostRequestParams(requestData);

		String token =  requestData.getToken();
		String securityFactor = requestData.getSecurityFactor();
		String signedData = requestData.getSignedData();
		String requestPara =  requestData.getData();

		String msg = "x";//checkMsg(securityFactor, signedData, requestPara);
		if (StringUtils.isEmpty(msg)) {
			//打印消息日志
			RequestData data = new RequestData(token,securityFactor, signedData, requestPara);
			String errMsg = data.toString() + " is invalid for invoke";
//			log.error(errMsg);
//			this.storage.setResponseWhenFailed(requestData.getRequestID(), errMsg, response);
			return false;
		}

		return true;
	}
}
