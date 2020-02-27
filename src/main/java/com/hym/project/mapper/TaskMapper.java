package com.hym.project.mapper;

import com.hym.project.domain.Task;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String id);


    List<Task> selectAll();

    int selectCount();
    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}