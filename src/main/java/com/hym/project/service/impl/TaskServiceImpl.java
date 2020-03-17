package com.hym.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hym.project.domain.Task;
import com.hym.project.mapper.TaskMapper;
import com.hym.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskMapper taskMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Task record) {
        return taskMapper.insert(record);
    }

    @Override
    public Task selectByPrimaryKey(String id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Task> selectAll(int appType,int pageNum,int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        Map map = new HashMap();
        map.put("appType",appType);
        List<Task> list = taskMapper.selectAll(map);
        PageInfo<Task> page = new PageInfo<Task>(list);
        return page;
    }

    @Override
    public int getCount() {
        return taskMapper.selectCount();
    }

    @Override
    public int updateByPrimaryKeySelective(Task record) {
        return taskMapper.updateByPrimaryKeySelective(record);
    }


}
