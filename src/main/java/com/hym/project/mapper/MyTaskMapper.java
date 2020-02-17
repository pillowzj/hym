package com.hym.project.mapper;

import com.hym.project.domain.MyTask;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MyTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(MyTask record);

    int insertSelective(MyTask record);

    MyTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MyTask record);

    int updateByPrimaryKey(MyTask record);

     int selectCount();

    @Select("select * from my_task where uid=#{uid}")
    List<MyTask> selectMyTask(String uid);
}