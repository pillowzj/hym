package com.hym.project.mapper;

import com.hym.project.domain.MyTask;
import com.hym.project.domain.vo.MyTaskVO;

import java.util.List;
import java.util.Map;

public interface MyTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(MyTask record);

    int insertSelective(MyTask record);

    MyTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MyTask record);

    int updateByPrimaryKey(MyTask record);

     int selectCount(Map map);

    List<MyTaskVO> selectMyTask(Map map);
}