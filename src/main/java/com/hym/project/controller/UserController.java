package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.constant.HttpStatus;
import com.hym.common.constant.WorkflowConstants;
import com.hym.common.utils.IdUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.IdInfo;
import com.hym.project.domain.PayCode;
import com.hym.project.domain.User;
import com.hym.project.service.AssetService;
import com.hym.project.service.IdInfoService;
import com.hym.project.service.PayCodeService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/hym/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AssetService assetService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private PayCodeService payCodeService;

    @Autowired
    private IdInfoService idInfoService;

    @PostMapping("/getUserInfo")
    public AjaxResult getUserInfo() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        String token =requestData.getToken();
        LoginUser loginUser = tokenService.psrseUser(token);
        User user = userService.selectByPrimaryKey(loginUser.getUser().getId());
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

//        String name = reqbody.getString("name");
//        String idNumber = reqbody.getString("idNumber");
//        String gender = reqbody.getString("gender");
//        String nation = reqbody.getString("nation");
//        String address = reqbody.getString("address");
//        String birthDay = reqbody.getString("birthDay");
//        String idAuthoIssue = reqbody.getString("idAuthoIssue");
//        String idExpirDate = reqbody.getString("idExpirDate");
//        String idPositive = reqbody.getString("idPositive");
//        String idOpposite = reqbody.getString("idOpposite");

//        info.setAddress(address);
//        info.setBirthday(birthDay);
//        info.setGender(gender);
//        info.setIdNumber(idNumber);
//        info.setIdAuthoIssue(idAuthoIssue);
//        info.setNation(nation);
//        info.setName(name);
//        info.setIdExpirDate(idExpirDate);
//        info.setIdPositive(idPositive);
//        info.setIdOpposite(idOpposite);
        IdInfo info = new IdInfo();
        if (idCards.size() > 0) {
            info.setId(IdUtils.fastSimpleUUID());
            info.setUid(user.getId());
            info.setIdPositive(idCards.get(0));
            info.setIdOpposite(idCards.get(1));
            info.setIdHand(idCards.get(2));
            info.setInsertTime(new Date());
        }
        idInfoService.insert(info);
        user.setIsAutho(WorkflowConstants.TWO); // 收款码已绑定 身份证已验证
        userService.updateByPrimaryKeySelective(user);
        return AjaxResult.success();
    }

    @PostMapping("/getPayCodeList")
    public AjaxResult getPayCodeList(){
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String uid = reqbody.getString("uid");

        List<PayCode> payCodes = payCodeService.selectByUid(uid);
        return AjaxResult.success(payCodes);
    }

    @PostMapping("/delPayCode")
    public AjaxResult delPayCode(){
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String id = reqbody.getString("id");

        payCodeService.deleteByPrimaryKey(id);
        return AjaxResult.success();
    }

    @PostMapping("/bindPayCode")
    public AjaxResult bindPayCode() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());

        String verifyCode = reqbody.getString("verifyCode");
        String cellPhone = reqbody.getString("cellPhone");
        //1 验证码验证
        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
            return AjaxResult.error(HttpStatus.BAD_VERIFY_CODE, " verify is error");
        }
        String uid = reqbody.getString("uid");
        int payType = reqbody.getInteger("payType");
        String name = reqbody.getString("name");
        String payName = reqbody.getString("payName");
        String payCode = reqbody.getString("payCode");


        User user = userService.selectByPrimaryKey(uid);
        PayCode pc = new PayCode();
        pc.setId(IdUtils.fastSimpleUUID());
        pc.setUid(user.getId());
        pc.setPayType(payType);
        pc.setName(name);
        pc.setPayName(payName);
        pc.setFlag(1);
        pc.setPayCode(payCode);
        pc.setInsertTime(new Date());
        if(reqbody.containsKey("weixin")){// 微信登录不进行身份验证
            user.setIsAutho(WorkflowConstants.TWO);//  1 绑定收款码
        }else if(user.getIsAutho() != WorkflowConstants.TWO){
            user.setIsAutho(WorkflowConstants.ONE);//  1 绑定收款码
        }else{

        }

        userService.updateByPrimaryKeySelective(user);
        payCodeService.insert(pc);
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

    @PostMapping("/updateVersion")
    public AjaxResult updateVersion() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        String token = requestData.getToken();
        LoginUser loginUser = tokenService.psrseUser(token);
        User user = new User();
        user.setId(loginUser.getUser().getId());
        user.setIsLatest(1);
        userService.updateByPrimaryKeySelective(user);
        return AjaxResult.success();
    }
}
