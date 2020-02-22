package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.TransOrder;
import com.hym.project.service.AssetService;
import com.hym.project.service.TansOrderService;
import com.hym.project.service.UserService;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TransferQueue;

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
    public AjaxResult submitMyOrder(String data) {
        JSONObject reqbody = JSON.parseObject(data);
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

       @PostMapping("/getMyOrder")
    public AjaxResult getMyOrder(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");


        List<TransOrder> ts = orderService.getOrderBySellerId(uid);

        return AjaxResult.success(ts);
    }


    @PostMapping("/getMyOrderDetail")
    public AjaxResult getMyOrderDetail(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        String id = reqbody.getString("id");
        TransOrder ts = orderService.getOrderById(id);
        return AjaxResult.success(ts);
    }

}
