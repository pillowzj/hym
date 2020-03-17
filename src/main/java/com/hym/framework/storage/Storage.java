package com.hym.framework.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.hym.common.exception.PFException;
import com.hym.common.utils.StringUtils;
import com.hym.common.utils.security.Md5Utils;
import com.hym.framework.domain.RequestData;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * hym 仓库
 *
 * @author lijun kou
 * @date 27/02/2020
 */
@Component
public class Storage {

    public static final String SECURITY_FACTOR = "security_factor";
    public static final String SIGNED_DATA = "signeddata";
    public static final String AUTHORIZATION = "Authorization";

    /**
     *
     * @param request
     * @return
     * @throws PFException
     */
    public RequestData getRequestData(HttpServletRequest request) throws PFException {

        try {
            String requestPara;
            StringBuilder sb = new StringBuilder();
            BufferedReader streamReader = new BufferedReader(
                    new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8)
            );
            String line;

            String method = request.getMethod();
            //读取请求参数: POST PUT DELETE GET
            if (POST.name().equals(method) || PUT.name().equals(method) || DELETE.name().equals(method)) {
                while ((line = streamReader.readLine()) != null) {
                    sb.append(line);
                }
                requestPara = sb.toString();
                JSONObject reqData = JSON.parseObject(requestPara, Feature.OrderedField);
            } else {
                throw new Exception("No accept request method:[" + method + "]");
            }
            String securityFactor = request.getHeader(SECURITY_FACTOR);
            String signedData = request.getHeader(SIGNED_DATA);
            String token = request.getHeader(AUTHORIZATION);
            // 对数据签名验证
            // 。。。
            String str ="kouliang+xoF8npYJcIUfZ/urky3zGn1R6y+608ccKMOqiNOgsqde8yTvi6yNrvg82eHFnKq";
            if(StringUtils.isEmpty(requestPara)){
                str = Md5Utils.hash(str);
            }else {
                str = Md5Utils.hash(requestPara+str);
            }

            System.out.println("request Data--->"+requestPara);
            System.out.println("clent 签名：--->"+signedData);
            System.out.println("server 签名：--->"+str);
            if(!str.equals(signedData)){
                throw new PFException("权限不够");
            }
            return new RequestData(token, securityFactor, signedData, requestPara);
        } catch (Exception e) {
            throw new PFException(e);
        }
    }
}


