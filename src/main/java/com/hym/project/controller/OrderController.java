package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hym.common.utils.Arith;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.Asset;
import com.hym.project.domain.TransOrder;
import com.hym.project.service.AssetService;
import com.hym.project.service.TansOrderService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * HYM 卖出订单
 *
 */
@RestController
@RequestMapping("/api/hym/order")
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private AssetService accountService;

    @Autowired
    private TansOrderService orderService;

    @Autowired
    private AssetService assetService;

    @PostMapping("/submitSellHYM")
    @Transactional
    public AjaxResult submitSellHYM() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");
        String isAutho = reqbody.getString("isAutho");
        String status = reqbody.getString("status");
        String hymPrice = reqbody.getString("hymPrice");
        String hymCount = reqbody.getString("hymCount");
        String totalSum = reqbody.getString("totalSum");
        String fee = reqbody.getString("fee");

        Asset asset = assetService.selectByPrimaryKey(uid);

        // 完成任务 : 上传证明--> 冻结资产增加 可用资产不变
        // 当任务审核通过时，冻结资产解冻为可用资产
        // 当前任务终结 状态为3

        asset.setToken(Arith.sub(asset.getToken(), hymCount));
        asset.setFrozenToken(Arith.add(asset.getFrozenToken(),hymCount));
        try{
            assetService.updateByPrimaryKeySelective(asset);
            orderService.submitSellHYM(uid,hymPrice,hymCount,totalSum,fee,isAutho,status);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException("rockback-----");
        }
        return AjaxResult.success();
    }

    /**
     * 查询我的订单列表
     * @return
     */
    @PostMapping("/getMyOrder")
    public AjaxResult getMyOrder() {
           RequestData requestData = ThreadCache.getPostRequestParams();
           JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");
           int pageNum = reqbody.getInteger("pageNum");
           int pageSize = reqbody.getInteger("pageSize");
           int flag = reqbody.getInteger("flag");


           PageInfo<TransOrder> ts = orderService.getOrderBySellerId(uid,pageNum,pageSize);
           ts.setList(ts.getList().size()>2?ts.getList().subList(0,2):ts.getList());
        return AjaxResult.success(ts);
    }


    @PostMapping("/getMyOrderDetail")
    public AjaxResult getMyOrderDetail() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String id = reqbody.getString("id");
        TransOrder ts = orderService.getOrderById(id);
        return AjaxResult.success(ts);
    }

}
