/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */

package com.xkw.rbm.user.support;

import com.xkw.mdm.dto.Credentials;
import com.xkw.mdm.dto.UserInfo;
import com.xkw.rbm.support.MdService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author fupengfei
 * @since 1.0.
 */
@RestController
@RequestMapping("/account")
public class UserController {

    @Autowired
    private MdService mdService;

    /**
     * 用户登录，使用angular-shiro的subject.login(token)进行登录
     *
     * @param params ：{"token":{"principal":"username","credentials":"password","rememberMe":true/false}}
     * @return AccountController#getUserInfo()
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public UserInfo login(@RequestBody Map<String, Map<String, String>> params) {
        Map<String, String> token = params.get("token");

        boolean rememberMe = Boolean.valueOf(token.get("rememberMe"));
        //登录时，将用户名置为小写
        UsernamePasswordToken upToken =
                new UsernamePasswordToken(token.get("principal").toLowerCase(), token.get("credentials"), rememberMe);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);

        UserInfo info = mdService.getUserInfo(token.get("principal"));

        Credentials credentials = new Credentials();

        info.getInfo().getAuthc().setCredentials(credentials);
        return info;
    }
}
