/*
 *  Copyright (C) 2017 the xkw.com authors.
 *  http://www.xkw.com
 */

package com.xkw.rbm.configuration.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author fupengfei
 * @since 1.0.
 */
public class SsoUsernamePasswordToken extends UsernamePasswordToken {

    private String token;

    public SsoUsernamePasswordToken(String token, String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
