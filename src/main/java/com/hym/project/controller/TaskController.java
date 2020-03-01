package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hym.common.constant.WorkflowConstans;
import com.hym.common.utils.IdUtils;
import com.hym.common.utils.StringUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.MyTask;
import com.hym.project.domain.Task;
import com.hym.project.service.MyTaskService;
import com.hym.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务
 *
 * @author lijun kou
 */
@RestController
@RequestMapping("/api/hym/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private MyTaskService myTaskService;

    @PostMapping("/createTask")
    public AjaxResult createTask(){
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());

        String type = reqbody.getString("type");
        String title = reqbody.getString("title");
        String description = reqbody.getString("description");
        String icon = reqbody.getString("icon");
        String token = reqbody.getString("token");
        String totalSum = reqbody.getString("totalSum");
        String hym = reqbody.getString("hym");
        if(reqbody.containsKey("id")){
            String id = reqbody.getString("id");
            Task task = new Task(id,title,description,icon, WorkflowConstans.ZERO,totalSum,token,new Date());
            taskService.updateByPrimaryKeySelective(task);
        }else{
            Task task = new Task(IdUtils.fastSimpleUUID(),title,description,icon,WorkflowConstans.ZERO,totalSum,token,new Date());
            taskService.insert(task);
        }
        return AjaxResult.success();
    }

    /**
     * 任务发放
     *
     * @return
     * @Author lijun kou
     */
    @GetMapping("/gettasks")
    public AjaxResult getTastList() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        int pageNum = reqbody.getInteger("pageNum");
        int pageSize = reqbody.getInteger("pageSize");
        PageInfo<Task> tasks = taskService.selectAll(pageNum,pageSize);

        String uid = reqbody.getString("uid");
        if (StringUtils.isEmpty(uid)) {
            return AjaxResult.success(tasks);
        }
        List<MyTask> myTasks = myTaskService.selectMyTask(uid,WorkflowConstans.NINE);
        //过滤获取 type=2的数据
        List<Task> collect = tasks.getList().stream().filter(task -> myTasks.stream().noneMatch(myTask -> myTask.getTid().equals(task.getId()))).collect(Collectors.toList());
        tasks.setList(collect);
        return AjaxResult.success(tasks);
    }

    @GetMapping("/getCount")
    public AjaxResult getCount() {
        int count = taskService.getCount();
        int myCount = myTaskService.getCount("",WorkflowConstans.NINE);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("count", count);
        ajax.put("yqCount", myCount);
        ajax.put("wqCount", count - myCount);

        return ajax;
    }

//    @PostMapping("/settask")
//    public AjaxResult setTast() {
//        List<Task> tasks = taskService.selectAll();
//        String str = JSON.toJSONString(new ResponseWraper("200", "ok", tasks));
//        System.out.println(" List<Task> selectAll() --->" + str);
//        return AjaxResult.success(tasks);
//    }
}
