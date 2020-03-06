package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hym.common.constant.WorkflowConstants;
import com.hym.common.utils.Arith;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.Asset;
import com.hym.project.domain.MyTask;
import com.hym.project.domain.Task;
import com.hym.project.service.AssetService;
import com.hym.project.service.MyTaskService;
import com.hym.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 我的任务
 *
 * @Author lijun kou
 */
@RestController
@RequestMapping("/api/hym/task")
public class MyTaskController {

    @Autowired
    MyTaskService myTaskService;
    @Autowired
    TaskService taskService;
    @Autowired
    AssetService assetService;

    /**
     * 领取任务
     *
     * @param
     * @return
     */
    @PostMapping("/createMyTask")
    public AjaxResult createMyTask() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
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
        return AjaxResult.success();
    }

    /**
     * 我的任务
     *
     * @param
     * @return
     */
    @GetMapping("/getMyTask")
    public AjaxResult getMyTask() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");
        int pageNum = reqbody.getInteger("pageNum");
        int pageSize = reqbody.getInteger("pageSize");
        PageInfo<MyTask> myTasks = myTaskService.selectMyTask(uid, WorkflowConstants.NINE,pageNum,pageSize);
//        String str = JSON.toJSONString(new ResponseWraper("200", "ok",myTasks));
        return AjaxResult.success(myTasks);
    }

    /**
     * 提交任务结果
     * 若任务在规定的时间（30分钟内）没有完成，任务自动释放。
     *
     * @param
     * @return
     */
    @PostMapping("/finishMyTask")
    @Transactional
    public AjaxResult finishMyTask() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");
        String id = reqbody.getString("id");
        String token = reqbody.getString("token");//获取token 数量
        List<String> pictrues = (List) reqbody.getJSONArray("pictrues");

        MyTask myTask = new MyTask();
        myTask.setId(id);
        myTask.setFinishDate(new Date());
        myTask.setStatus(WorkflowConstants.ONE);
        myTask.setResult(pictrues.toString());

        // 更新账户信息
        Asset asset = assetService.selectByPrimaryKey(uid);

        // 完成任务 : 上传证明--> 冻结资产增加 可用资产不变
        // 当任务审核通过时，冻结资产解冻为可用资产
        // 当前任务终结 状态为3

        asset.setFrozenToken(Arith.add(asset.getFrozenToken(),token));
//        asset.setToken(Arith.add(asset.getToken(),token));
        try{
            myTaskService.updateByPrimaryKeySelective(myTask);
            // 记录交易信息
            assetService.updateByPrimaryKeySelective(asset);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        throw  new RuntimeException("rockback-----");
        }
        return AjaxResult.success();
    }
}
