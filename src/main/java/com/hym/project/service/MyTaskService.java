package com.hym.project.service;

import com.hym.project.domain.MyTask;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MyTaskService {

    int deleteByPrimaryKey(String id);

    int insert(MyTask record);

    int insertSelective(MyTask record);

    MyTask selectByPrimaryKey(String id);


    List<MyTask> selectMyTask(String uid);
    int getCount();

    int updateByPrimaryKeySelective(MyTask record);

    int updateByPrimaryKey(MyTask record);
}
