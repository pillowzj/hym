package com.hym.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.Asset;
import com.hym.project.service.AssetService;
import com.hym.project.service.TansOrderService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hym/assets")
public class AssetController {

    @Autowired
    private UserService userService;

    @Autowired
    private AssetService accountService;

    @Autowired
    private TansOrderService orderService;
    /**
     * 获取account info
     *
     * @return
     */
    @PostMapping("/getAssets")
    public AjaxResult getAccount(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        String id = reqbody.getString("uid");
        Asset asset = accountService.selectByPrimaryKey(id);
        return AjaxResult.success(asset);
    }

}
