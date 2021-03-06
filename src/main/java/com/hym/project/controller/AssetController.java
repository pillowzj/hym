package com.hym.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.utils.Arith;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.Asset;
import com.hym.project.service.AssetService;
import com.hym.project.service.TansOrderService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public AjaxResult getAccount() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String id = reqbody.getString("uid");
        Asset asset = accountService.selectByPrimaryKey(id);
        Map map = new HashMap<>();

        String frozenToken = asset.getFrozenToken();
        String token = asset.getToken();
        String totalAsset = Arith.add(frozenToken,token); // 可用资产+
        map.put("token",token);
        map.put("totalAsset",totalAsset);
        return AjaxResult.success(map);
    }

}
