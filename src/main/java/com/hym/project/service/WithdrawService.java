package com.hym.project.service;

import com.github.pagehelper.PageInfo;
import com.hym.project.domain.Withdraw;
import com.hym.project.util.Page;

import java.util.List;

public interface WithdrawService {

    int insert(Withdraw record);

    Withdraw selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Withdraw record);

    PageInfo<Withdraw> selectWithdrawPage(String uid , int pageNum, int pageSize);

    int selectCountByUid(String uid);
}
