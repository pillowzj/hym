package com.hym.project.service;

import com.github.pagehelper.PageInfo;
import com.hym.project.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {

    int deleteByPrimaryKey(String id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String id);

    PageInfo<Task> selectAll(int pageNum, int pageSize);

    int getCount();

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}
