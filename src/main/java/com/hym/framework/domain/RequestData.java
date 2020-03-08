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

import com.alibaba.fastjson.annotation.JSONType;
import com.hym.project.domain.Message;

/**
 * 请求数据
 * 包含请求头中securityFactor、signedData以及请求体中数据
 *
 * @author lijun kou
 * @date 27/02/2020
 */
@JSONType(orders = {"data", "securityFactor", "signedData"})
public class RequestData extends Message{
	private String token;
	private String securityFactor;
	private String signedData;
	private String data;

	public RequestData() {
	}

	public RequestData(String token,String securityFactor, String signedData, String data) {
		this.token = token;
		this.securityFactor = securityFactor;
		this.signedData = signedData;
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSecurityFactor() {
		return securityFactor;
	}

	public void setSecurityFactor(String securityFactor) {
		this.securityFactor = securityFactor;
	}

	public String getSignedData() {
		return signedData;
	}

	public void setSignedData(String signedData) {
		this.signedData = signedData;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


}
