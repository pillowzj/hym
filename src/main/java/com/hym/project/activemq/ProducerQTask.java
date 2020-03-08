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
package com.hym.project.activemq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * 消息生产者
 */
@Component
public class ProducerQTask {

	private static final Logger log = LoggerFactory.getLogger(ProducerQTask.class);

	@Autowired
	private JmsMessagingTemplate jmt;

	@Autowired
	private Queue queueTx;

	public void sendToQueue(String message) {
		//消息发送至等待响应队列
		jmt.convertAndSend(this.queueTx, message);
		log.info("message sent to tx: " + message);
	}
}

