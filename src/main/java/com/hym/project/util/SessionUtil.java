package com.hym.project.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    /**
     * 获取request
     * @return
     */
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes request= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return request==null?null:request.getRequest();
    }

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession(){
        return getRequest().getSession(true);
    }

    /**
     * 获取真实路径
     * @return
     */
    public static String getRealRootPath(){
        return getRequest().getServletContext().getRealPath("/");
    }

    /**
     * 获取ip
     * @return
     */
    public String getIP(){
        ServletRequestAttributes request= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (request!=null){
            HttpServletRequest httpServletRequest=request.getRequest();
            return httpServletRequest.getRemoteAddr();
        }
        return null;
    }
}
