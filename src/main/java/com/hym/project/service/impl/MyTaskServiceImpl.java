package com.hym.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hym.common.utils.IdUtils;
import com.hym.project.domain.MyTask;
import com.hym.project.mapper.MyTaskMapper;
import com.hym.project.service.AssetService;
import com.hym.project.service.MyTaskService;
import com.hym.project.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyTaskServiceImpl implements MyTaskService {

    @Autowired
    private MyTaskMapper myTaskMapper;
    @Autowired
    private AssetService accountService;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(MyTask record) {
        record.setId(IdUtils.fastSimpleUUID());
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
    public PageInfo<MyTask> selectMyTask(String uid,int status,int pageNum,int pageSize) {
        Map map1 = new HashMap();
        map1.put("uid",uid);
        map1.put("status",status);
        int total = myTaskMapper.selectCount(map1);
        pageNum = Page.getPageNum(pageNum, pageSize, total);
        PageHelper.startPage(pageNum,pageSize);
        Map map = new HashMap();
        map.put("uid",uid);
        map.put("status",status);
        List<MyTask> list = myTaskMapper.selectMyTask(map);
        PageInfo<MyTask> page = new PageInfo<MyTask>(list);
        return page;
    }

    @Override
    public List<MyTask> selectMyTask(String uid,int status) {
        Map map = new HashMap();
        map.put("uid",uid);
        map.put("status",status);
        return myTaskMapper.selectMyTask(map);
    }

    @Override
    public int updateByPrimaryKeySelective(MyTask record) {
//        accountService.selectByPrimaryKey()
//        accountService.updateByPrimaryKeySelective();
        return myTaskMapper.updateByPrimaryKeySelective(record);
    }
    @Override
    public int getCount(String uid,int status){
        Map map = new HashMap();
        map.put("uid",uid);
        map.put("status",status);
        return myTaskMapper.selectCount(map);
    }
    @Override
    public int updateByPrimaryKey(MyTask record) {
        return 0;
    }
}
