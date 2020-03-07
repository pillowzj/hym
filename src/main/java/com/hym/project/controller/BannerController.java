package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.Banner;
import com.hym.project.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hym/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;
    @GetMapping("/getById")
    public AjaxResult getById(){
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        Integer id = reqbody.getInteger("id");
        Banner banner = bannerService.getByPrimaryKey(id);
        return AjaxResult.success(banner);
    }
    @GetMapping("/getByStatus")
    public AjaxResult getByStatus(){
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        Integer status = reqbody.getInteger("status");
        List<Banner> banner = bannerService.getByStatus(status);
        return AjaxResult.success(banner);
    }
}
