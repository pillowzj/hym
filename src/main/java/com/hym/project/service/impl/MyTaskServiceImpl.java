package com.hym.project.service.impl;

import com.hym.common.utils.IdUtils;
import com.hym.project.domain.MyTask;
import com.hym.project.mapper.MyTaskMapper;
import com.hym.project.service.AccountService;
import com.hym.project.service.MyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class MyTaskServiceImpl implements MyTaskService {

    @Autowired
    private MyTaskMapper myTaskMapper;
    @Autowired
    private AccountService accountService;
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(MyTask record) {
        record.setId(IdUtils.fastUUID());
        System.out.println(record.getId());
        record.setInsertDate(new Date());
        record.setStatus(0);
        return myTaskMapper.insert(record);
    }

    @Override
    public int insertSelective(MyTask record) {
        return 0;
    }

    @Override
    public MyTask selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public List<MyTask> selectMyTask(String uid) {
        return myTaskMapper.selectMyTask(uid);
    }

    @Override
    public int getCount() {
        return myTaskMapper.selectCount();
    }

    @Override
    public int updateByPrimaryKeySelective(MyTask record) {
//        accountService.selectByPrimaryKey()
//        accountService.updateByPrimaryKeySelective();
        return myTaskMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MyTask record) {
        return 0;
    }
}
