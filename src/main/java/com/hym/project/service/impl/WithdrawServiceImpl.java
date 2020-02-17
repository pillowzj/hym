package com.hym.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hym.project.domain.Withdraw;
import com.hym.project.mapper.WithdrawMapper;
import com.hym.project.service.WithdrawService;
import com.hym.project.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WithdrawServiceImpl implements WithdrawService {

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Override
    public int insert(Withdraw record) {
        return withdrawMapper.insert(record);
    }

    @Override
    public Withdraw selectByPrimaryKey(String id) {
        return withdrawMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Withdraw record) {
        return withdrawMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<Withdraw>  selectWithdrawPage(String uid, int pageNum, int pageSize) {
        int total = withdrawMapper.selectCountByUid(uid);
        int totalPage = 1;

        try{
            totalPage = (total + pageSize -1) / pageSize;
        }catch (Exception e){

            totalPage =1;
        }
        pageNum = pageNum == 1 ?0:pageSize*(pageNum-1);

        PageHelper.startPage(pageNum,pageSize);
       List<Withdraw> list = withdrawMapper.selectWithdrawPage(uid);

        PageInfo<Withdraw> page = new PageInfo<Withdraw>(list);

        return page;

    }

    @Override
    public int selectCountByUid(String uid) {
        return withdrawMapper.selectCountByUid(uid);
    }
}
