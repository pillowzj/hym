package com.hym.project.mapper;

import com.hym.project.domain.Task;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Task record);

    List<Task> selectAll();

    int selectCount();
}