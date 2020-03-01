package com.hym.framework.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.hym.framework.domain.RequestData;
import com.hym.common.exception.PFException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 在请求头中获取请求参数
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

            } else if (GET.name().equals(method)) {
                JSONObject jsonObject = new JSONObject();
                request.getParameterMap().forEach((k, v) -> jsonObject.put(k, request.getParameter(k)));
                requestPara = JSON.toJSONString(jsonObject);
            } else {
                throw new Exception("No accept request method:[" + method + "]");
            }

            System.out.println("----------------------storage----------getRequestData------------");
            System.out.println(requestPara);
            String securityFactor = request.getHeader(SECURITY_FACTOR);
            String signedData = request.getHeader(SIGNED_DATA);
            return new RequestData(securityFactor, signedData, requestPara);
        } catch (Exception e) {
            throw new PFException(e);
        }
    }

    /**
     * 设置返回值
     */
    public void setResponseWhenFailed(String requestID, String errMsg, HttpServletResponse response) throws Exception {
//        String code = "0200";
//        String text = String.format("聚龙链系统错误信息：[%s]", errMsg);
//        ResponseMessage pMessage = new ResponseMessage(code, text, requestID);
//        //设置返回消息
//        response.getWriter().print(pMessage.toString(true));
    }

    /**
     * 红色文字
     */
    public static String redWord(String word) {
        return "\033[31;4m" + word + "\033[0m";
    }

}


