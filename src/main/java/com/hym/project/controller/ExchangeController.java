package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.Exchange;
import com.hym.project.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/hym")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    /**
     * Rate 0.1CNY
     * @param data
     * @return
     */
    @PostMapping("/exchange")
    public AjaxResult exchange(String data){
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        String token = reqbody.getString("token");
        Exchange exchange = new Exchange();
        exchange.setToken(new BigDecimal(token));
        exchange.setUid(uid);
        exchange.setRate(new BigDecimal(0.1));
        exchange.setCny(exchange.getToken().multiply(exchange.getRate()));
        exchange.setInsertDate(new Date());
        exchangeService.insert(exchange);
        String str = JSON.toJSONString(new ResponseWraper("200", "ok"));
        return AjaxResult.success();
    }

    @PostMapping("/exchangeList")
    public AjaxResult exchangeList(String data){
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");


        List<Exchange> exchanges= exchangeService.selectByUid(uid);
        String str = JSON.toJSONString(new ResponseWraper("200", "ok",exchanges));
        return AjaxResult.success(exchanges);
    }
}
