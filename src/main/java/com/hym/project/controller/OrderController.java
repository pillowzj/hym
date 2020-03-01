package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.TransOrder;
import com.hym.project.service.AssetService;
import com.hym.project.service.TansOrderService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hym/order")
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private AssetService accountService;

    @Autowired
    private TansOrderService orderService;

    @PostMapping("/submitMyOrder")
    public AjaxResult submitMyOrder() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");
        String isAutho = reqbody.getString("isAutho");
        String status = reqbody.getString("status");
        String hymPrice = reqbody.getString("hymPrice");
        String hymCount = reqbody.getString("hymCount");
        String totalSum = reqbody.getString("totalSum");
        String fee = reqbody.getString("fee");

        int flag = orderService.submitMyOrder(uid,hymPrice,hymCount,totalSum,fee,isAutho,status);

        return AjaxResult.success();
    }

       @GetMapping("/getMyOrder")
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


    @GetMapping("/getMyOrderDetail")
    public AjaxResult getMyOrderDetail() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String id = reqbody.getString("id");
        TransOrder ts = orderService.getOrderById(id);
        return AjaxResult.success(ts);
    }

}
