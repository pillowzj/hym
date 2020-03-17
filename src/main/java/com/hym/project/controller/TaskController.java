package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hym.common.constant.Constants;
import com.hym.common.constant.WorkflowConstants;
import com.hym.common.utils.Arith;
import com.hym.common.utils.IdUtils;
import com.hym.common.utils.StringUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.Task;
import com.hym.project.domain.vo.MyTaskVO;
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
    @Autowired
    private TokenService tokenService;

    @PostMapping("/createTask")
    public AjaxResult createTask() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        Task task = JSON.parseObject(requestData.getData(), Task.class);
         // 完成此任务获取的HYM 数量
        task.setToken(Arith.mul(task.getPrice(), 100));
        task.setPeople(task.getPeople() * Constants.TEN_THOUSAND_X);
        String rmbSum = Arith.mul(task.getRmbSum(), Constants.TEN_THOUSAND_X);
        String tokenTotalSum = Arith.mul(task.getTokenTotalSum(), Constants.TEN_THOUSAND_X);
        task.setRmbSum(rmbSum);
        task.setTokenTotalSum(tokenTotalSum);

        task.setId(IdUtils.fastSimpleUUID());
        task.setStatus(WorkflowConstants.ZERO);
        taskService.insert(task);
        return AjaxResult.success();
    }

    /**
     * 任务发放
     *
     * @return
     * @Author lijun kou
     */
    @PostMapping("/gettasks")
    public AjaxResult getTastList() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String token = requestData.getToken();
        LoginUser loginUser = tokenService.psrseUser(token);
        String uid = loginUser.getUser().getId();
        int pageNum = reqbody.getInteger("pageNum");
        int pageSize = reqbody.getInteger("pageSize");
        int appType = reqbody.getInteger("appType");
        PageInfo<Task> tasks = taskService.selectAll(appType,pageNum, pageSize);

        if (StringUtils.isEmpty(uid)) {
            return AjaxResult.success(tasks);
        }
        List<MyTaskVO> myTasks = myTaskService.selectMyTask(uid, WorkflowConstants.NINE);
        //过滤获取 type=2的数据
        List<Task> collect = tasks.getList().stream().filter(task -> myTasks.stream().noneMatch(myTask -> myTask.getTid().equals(task.getId()))).collect(Collectors.toList());
        tasks.setList(collect);
        return AjaxResult.success(tasks);
    }

    @PostMapping("/getTaskCount")
    public AjaxResult getCount() {
        int count = taskService.getCount();
        int myCount = myTaskService.getCount("", WorkflowConstants.NINE);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("count", count);
        ajax.put("yqCount", myCount);
        ajax.put("wqCount", count - myCount);

        return ajax;
    }

}
