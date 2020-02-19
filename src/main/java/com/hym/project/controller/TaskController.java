package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.utils.StringUtils;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.MyTask;
import com.hym.project.domain.Task;
import com.hym.project.service.MyTaskService;
import com.hym.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 任务发放
     *
     * @return
     * @Author lijun kou
     */
    @PostMapping("/gettasks")
    public AjaxResult getTastList(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        List<Task> tasks = taskService.selectAll();

        String uid = reqbody.getString("uid");
        if (StringUtils.isEmpty(uid)) {
            return AjaxResult.success(tasks);
        }
        List<MyTask> myTasks = myTaskService.selectMyTask(uid);

        //过滤获取 type=2的数据
        //List<Task> list2 = tasks.stream().filter((Task a) -> ("2".equals(a.get("type").toString()))).collect(Collectors.toList());

        List<Task> collect = tasks.stream().filter(task -> myTasks.stream().noneMatch(myTask -> myTask.getTid().equals(task.getId()))).collect(Collectors.toList());

        String str = JSON.toJSONString(new ResponseWraper("200", "ok", collect));
        System.out.println(" List<Task> selectAll() --->" + str);
        return AjaxResult.success(collect);
    }

    @PostMapping("/getCount")
    public AjaxResult getCount() {
        int count = taskService.getCount();
        int myCount = myTaskService.getCount();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("count", count);
        ajax.put("yqCount", myCount);
        ajax.put("wqCount", count - myCount);

        return ajax;
    }

    @PostMapping("/settask")
    public AjaxResult setTast() {
        List<Task> tasks = taskService.selectAll();
        String str = JSON.toJSONString(new ResponseWraper("200", "ok", tasks));
        System.out.println(" List<Task> selectAll() --->" + str);
        return AjaxResult.success(tasks);
    }
}
