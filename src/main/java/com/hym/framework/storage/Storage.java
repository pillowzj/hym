package com.hym.framework.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.hym.common.exception.PFException;
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
            } else if (GET.name().equals(method)) {
                JSONObject jsonObject = new JSONObject();
                request.getParameterMap().forEach((k, v) -> jsonObject.put(k, request.getParameter(k)));

                requestPara = JSON.toJSONString(jsonObject);
            } else {
                throw new Exception("No accept request method:[" + method + "]");
            }
            String securityFactor = request.getHeader(SECURITY_FACTOR);
            String signedData = request.getHeader(SIGNED_DATA);
            String token = request.getHeader(AUTHORIZATION);
            return new RequestData(token, securityFactor, signedData, requestPara);
        } catch (Exception e) {
            throw new PFException(e);
        }
    }
}


