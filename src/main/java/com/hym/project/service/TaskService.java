package com.hym.project.service;

import com.github.pagehelper.PageInfo;
import com.hym.project.domain.Task;


public interface TaskService {

    int deleteByPrimaryKey(String id);

    int insert(Task record);

    Task selectByPrimaryKey(String id);

    PageInfo<Task> selectAll(int appType,int pageNum, int pageSize);

    int getCount();

    int updateByPrimaryKeySelective(Task record);

}
