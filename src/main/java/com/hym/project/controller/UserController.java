package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.constant.HttpStatus;
import com.hym.common.constant.WorkflowConstans;
import com.hym.common.utils.StringUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.Asset;
import com.hym.project.domain.User;
import com.hym.project.service.AssetService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hym/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AssetService assetService;

    @GetMapping("/getUser")
    public AjaxResult getUser(){
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");
        User user = userService.selectByPrimaryKey(uid);
        return AjaxResult.success(user);
    }

    @PostMapping("/setTradePassword")
    public AjaxResult setTradePassword() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");
        String verifyCode = reqbody.getString("verifyCode");
        String newTradePassword = reqbody.getString("newTradePassword");
        String cellPhone = reqbody.getString("cellPhone");
        //1 验证码验证
        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
            return AjaxResult.error("403", " verify is error");
        }
        User user = userService.selectByPrimaryKey(uid);
        user.setTradePassword(newTradePassword);
        userService.updateByPrimaryKeySelective(user);
        return AjaxResult.success();
    }

    @GetMapping("/getUserHMY")
    public AjaxResult getUserHMY() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String status = reqbody.getString("status");
        String isAutho = reqbody.getString("isAutho");
        Map map = new HashMap();
        map.put("is_autho", isAutho);
        map.put("status", status);
        String id = userService.getUserHMY(map);
        if(!StringUtils.isEmpty(id)){
            return AjaxResult.error();
        }
        Asset asset = assetService.getUserHYMByPrimaryKey(id);
        return AjaxResult.success(asset);
    }

    @RequestMapping("/tradeHYK")
    public AjaxResult tradeHYK() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String flag = reqbody.getString("flag");
        String isAutho = reqbody.getString("isAutho");
        return AjaxResult.success();
    }

    /**
     * 提现前身份信息采集
     *
     * @return
     */
    @PostMapping("/verifyID")
    public AjaxResult verifyID() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");
        List<String> idCards = (List) reqbody.getJSONArray("idCard");
        User user = userService.selectByPrimaryKey(uid);
        System.out.println("idCards--->" + JSON.toJSON(idCards));
        if (idCards.size() > 0) {
            user.setIdPositive(idCards.get(0));
            user.setIdOpposite(idCards.get(1));
//            user.setIdHand(idCards.get(2));
        }
//        String name = reqbody.getString("name");
//        String idNumber = reqbody.getString("idNumber");
//        String gender = reqbody.getString("gender");
//        String nation = reqbody.getString("nation");
//        String address = reqbody.getString("address");
//        String birthDay = reqbody.getString("birthDay");
//
//        String idAuthoIssue = reqbody.getString("idAuthoIssue");
//        String idExpirDate = reqbody.getString("idExpirDate");
//        String idPositive = reqbody.getString("idPositive");
//        String idOpposite = reqbody.getString("idOpposite");


//        user.setAddress(address);
//        user.setBirthday(birthDay);
//        user.setGender(gender);
//        user.setIdNumber(idNumber);
//        user.setIdAuthoIssue(idAuthoIssue);
//        user.setNation(nation);
//        user.setName(name);
//        user.setIdExpirDate(idExpirDate);
//        user.setIdPositive(idPositive);
//        user.setIdOpposite(idOpposite);
        user.setIsAutho(WorkflowConstans.TWO); // 收款码已绑定 身份证已验证
        userService.updateByPrimaryKeySelective(user);
        return AjaxResult.success();
    }

    @PostMapping("/bindPayCode")
    public AjaxResult bindPayCode() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());

        String verifyCode = reqbody.getString("verifyCode");
        String cellPhone = reqbody.getString("celPhone");
        //1 验证码验证
        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
            return AjaxResult.error(HttpStatus.BAD_VERIFY_CODE, " verify is error");
        }
        String uid = reqbody.getString("uid");
        int payType = reqbody.getInteger("payType");
        String payName = reqbody.getString("payName");
        String payCode = reqbody.getString("payCode");

        User user = userService.selectByPrimaryKey(uid);
        user.setPayCode(payCode);
        user.setPayName(payName);
        user.setPayType(payType);
        user.setIsAutho(WorkflowConstans.ONE);//  1 已绑定收款码
        userService.updateByPrimaryKeySelective(user);
        return AjaxResult.success();
    }

    @PostMapping("/setTradePwd")
    public AjaxResult setTradePwd() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject json = JSON.parseObject(requestData.getData());
        String verifyCode = json.getString("verifyCode");
        String cellPhone = json.getString("cellPhone");
        String pwd = json.getString("pwd");
        String rePwd = json.getString("repwd");
        String uid = json.getString("uid");
        //1 验证码验证
        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
            return AjaxResult.error(HttpStatus.BAD_REPWD, " verify is error");
        } else if (!pwd.equals(rePwd)) {
            return AjaxResult.error(HttpStatus.BAD_REPWD, " repwd is error");
        }
        User user = new User();
        user.setId(uid);
        user.setTradePassword(pwd);
        userService.updateByPrimaryKeySelective(user);

        return AjaxResult.success();
    }
}
