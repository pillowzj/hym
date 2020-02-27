package com.hym.project.service;

import com.github.pagehelper.PageInfo;
import com.hym.project.domain.MyTask;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MyTaskService {

    int deleteByPrimaryKey(String id);

    int insert(MyTask record);

    int insertSelective(MyTask record);

    MyTask selectByPrimaryKey(String id);


    PageInfo<MyTask> selectMyTask(String uid,int status, int pageNum, int pageSize);
    List<MyTask> selectMyTask(String uid,int status);

    int updateByPrimaryKeySelective(MyTask record);
    int getCount(String uid,int status);
    int updateByPrimaryKey(MyTask record);
}
