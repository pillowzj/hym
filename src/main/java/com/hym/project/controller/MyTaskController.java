package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.Account;
import com.hym.project.domain.MyTask;
import com.hym.project.domain.Task;
import com.hym.project.mapper.MyTaskMapper;
import com.hym.project.service.AccountService;
import com.hym.project.service.MyTaskService;
import com.hym.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 我的任务
 *
 * @Author lijun kou
 */
@RestController
@RequestMapping("/api/hym")
public class MyTaskController {

    @Autowired
    MyTaskService myTaskService;
    @Autowired
    TaskService taskService;
    @Autowired
    AccountService accountService;

    /**
     * 领取任务
     *
     * @param data
     * @return
     */
    @PostMapping("/createMyTask")
    public AjaxResult createMyTask(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        String tid = reqbody.getString("tid");

        Task task = taskService.selectByPrimaryKey(tid);
        MyTask myTask = new MyTask();
        myTask.setTid(tid);
        myTask.setUid(uid);
        myTask.setTitle(task.getTitle());
        myTask.setToken(task.getToken());
        myTask.setIcon(task.getIcon());
        myTask.setInsertDate(new Date());
        int flag = myTaskService.insert(myTask);
        String str = JSON.toJSONString(new ResponseWraper("200", "ok"));

        return AjaxResult.success();
    }

    /**
     * 我的任务
     *
     * @param data
     * @return
     */
    @PostMapping("/getMyTask")
    public AjaxResult getMyTask(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        List<MyTask> myTasks = myTaskService.selectMyTask(uid);
//        String str = JSON.toJSONString(new ResponseWraper("200", "ok",myTasks));
        return AjaxResult.success(myTasks);
    }

    /**
     * 提交任务结果
     * 若任务在规定的时间（30分钟内）没有完成，任务自动释放。
     *
     * @param data
     * @return
     */
    @PostMapping("/finishMyTask")
    public AjaxResult finishMyTask(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        String id = reqbody.getString("id");
        String token = reqbody.getString("token");// my task id
        List<String> pictrues = (List) reqbody.getJSONArray("pictrues");

        MyTask myTask = new MyTask();
        myTask.setId(id);
        myTask.setFinalDate(new Date());
        myTask.setStatus(1);
        myTask.setResult(pictrues.toString());
        myTaskService.updateByPrimaryKeySelective(myTask);


        // 更新账户信息
        Account account = accountService.selectByPrimaryKey(uid);
        // 总token=获取当前任务的token+之前的token
        account.setToken(account.getToken().add(new BigDecimal(token)));
        // 记录交易信息
        accountService.updateByPrimaryKeySelective(account);
//        String str = JSON.toJSONString(new ResponseWraper("200", "ok"));
        return AjaxResult.success();
    }
}
