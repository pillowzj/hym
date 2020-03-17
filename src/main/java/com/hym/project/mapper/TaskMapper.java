package com.hym.project.mapper;

import com.hym.project.domain.Task;

import java.util.List;
import java.util.Map;

public interface TaskMapper {

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Task record);

    List<Task> selectAll(Map map);

    int selectCount();
}