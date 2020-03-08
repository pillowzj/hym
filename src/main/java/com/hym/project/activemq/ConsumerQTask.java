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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.project.domain.MyTask;
import com.hym.project.domain.Task;
import com.hym.project.service.MyTaskService;
import com.hym.project.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 消息消费：抢任务保存数据库
 */
@Component
public class ConsumerQTask {
    private static final Logger log = LoggerFactory.getLogger(ConsumerQTask.class);

    @Autowired
    MyTaskService myTaskService;
    @Autowired
    TaskService taskService;

    @JmsListener(destination = "QTask")
    public void receiveMessageTx(String message) throws Exception {
//        System.out.println("message--------->"+message);
        JSONObject jsonObject = JSON.parseObject(message);
        String uid = jsonObject.getString("uid");
        String tid = jsonObject.getString("tid");
        Task task = taskService.selectByPrimaryKey(tid);
        MyTask myTask = new MyTask();
        myTask.setTid(tid);
        myTask.setUid(uid);
        myTask.setTitle(task.getTitle());
        myTask.setToken(task.getToken());
        myTask.setIcon(task.getIcon());
        myTask.setInsertDate(new Date());
        int rCode = myTaskService.insert(myTask);
        String returncode = "Tx [returncode:" + rCode + "]";
        log.info(returncode);
//      Thread.sleep(2000);
    }

}
