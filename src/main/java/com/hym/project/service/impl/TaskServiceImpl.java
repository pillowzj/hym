package com.hym.project.service.impl;

import com.hym.project.domain.Task;
import com.hym.project.mapper.TaskMapper;
import com.hym.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return 0;
    }

    @Override
    public int insertSelective(Task record) {
        return 0;
    }

    @Override
    public Task selectByPrimaryKey(String id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Task> selectAll(){
       return taskMapper.selectAll();
    }

    @Override
    public int getCount() {
        return taskMapper.selectCount();
    }

    @Override
    public int updateByPrimaryKeySelective(Task record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Task record) {
        return 0;
    }
}
