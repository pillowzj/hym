package com.hym.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hym.common.constant.Constants;
import com.hym.common.utils.Arith;
import com.hym.common.utils.IdUtils;
import com.hym.project.domain.Asset;
import com.hym.project.domain.TransOrder;
import com.hym.project.mapper.TransOrderMapper;
import com.hym.project.service.AssetService;
import com.hym.project.service.TansOrderService;
import com.hym.project.service.UserService;
import com.hym.project.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TransOrderServiceImpl implements TansOrderService {

    @Autowired
    private TransOrderMapper transOrderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private AssetService assetService;
    private int total;

    /**
     * 交易订单创建
     *
     * @param sellUid
     * @param hymPrice
     * @param hymCount
     * @param totalSum
     * @param fee
     * @param isAutho
     * @param status
     * @return
     */
    @Override
    public int submitMyOrder(String sellUid, String hymPrice, String hymCount, String totalSum, String fee, String isAutho, String status) {


        Asset myAsset = assetService.selectByPrimaryKey(sellUid);

        myAsset.setToken(Arith.sub(myAsset.getToken(), hymCount));
        // 冻结hymCount
        myAsset.setFrozenToken(Arith.add(myAsset.getFrozenToken(), hymCount));

        assetService.updateByPrimaryKeySelective(myAsset);

        // 插入订单
        TransOrder order = new TransOrder();
        order.setId(IdUtils.fastSimpleUUID());
        order.setOrderId(IdUtils.orderID());
        order.setHymPrice(new BigDecimal(hymPrice).setScale(Constants.DECIMAL_POINT).toString());
        order.setTotalSum(new BigDecimal(totalSum).setScale(Constants.DECIMAL_POINT).toString());
        order.setHymCount(new BigDecimal(hymCount).setScale(Constants.DECIMAL_POINT).toString());
        order.setFee(new BigDecimal(fee).setScale(Constants.DECIMAL_POINT).toString());
        order.setSellerId(sellUid);
        order.setBuyerId("0f803c86-0b23-4173-bcdd-b3b43262654f");
        order.setStatus(1);// 交易中
        order.setInsertDate(new Date());

        transOrderMapper.insert(order);

        // 调用支付接口
        return 0;
    }

    public static void main(String[] s) {
        BigDecimal b = new BigDecimal("222.43345").add(new BigDecimal("444")).setScale(4);
        System.out.println(b);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return transOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TransOrder record) {
        return transOrderMapper.insert(record);
    }


    @Override
    public TransOrder getOrderById(String id) {
        return transOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TransOrder record) {
        return transOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<TransOrder> getOrderBySellerId(String sellerId, int pageNum, int pageSize) {
        int total = transOrderMapper.selectCountById(sellerId);
        pageNum = Page.getPageNum(pageNum, pageSize, total);
        PageHelper.startPage(pageNum, pageSize);
        List<TransOrder> list = transOrderMapper.selectById(sellerId);
        PageInfo<TransOrder> page = new PageInfo<TransOrder>(list);
        return page;
    }
}
