/*
 *  Copyright (C) 2017 the xkw.com authors.
 *  http://www.xkw.com
 */

package com.xkw.rbm.configuration.shiro;

import com.alibaba.fastjson.JSON;
import com.xkw.commons.util.ErrorMessage;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fupengfei
 * @since 1.0.
 */
public class RestAuthcFilter extends UserFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //        Subject subject = SecurityUtils.getSubject();
        //        Session session = subject.getSession(false);
        //        if(session == null){
        res.setStatus(HttpStatus.FORBIDDEN.value());
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        res.getWriter().write(JSON.toJSONString(new ErrorMessage(req.getRequestURL().toString(), "未登录，不允许访问")));
        //        }else{
        //            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        //            res.setContentType(MediaType.APPLICATION_JSON.toString());
        //            res.getWriter().write(JSON.toJSONString(new ErrorMessage(req.getRequestURL().toString(), "你没有权限访问此接口")));
        //        }
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString())) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
