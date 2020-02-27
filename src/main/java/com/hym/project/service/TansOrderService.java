package com.hym.project.service;

import com.github.pagehelper.PageInfo;
import com.hym.project.domain.TransOrder;

import java.util.List;

public interface TansOrderService {

    int deleteByPrimaryKey(String id);

    int insert(TransOrder record);



    TransOrder getOrderById(String id);

    int updateByPrimaryKeySelective(TransOrder record);

    PageInfo<TransOrder> getOrderBySellerId(String sellerId, int pageNum, int pageSize);

    int submitMyOrder (String uid,String hymPrice,String hymCount,String totalSum,String fee,String isAutho,String status);
}
