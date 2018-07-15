/*
 *  Copyright (C) 2017 the xkw.com authors.
 *  http://www.xkw.com
 */

package com.xkw.rbm.configuration.shiro;

import com.xkw.rbm.support.MdService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author flyspider
 * @since 1.0
 */
@Component
public class RestCredentialsMatcher implements CredentialsMatcher {

    @Autowired
    private MdService mdService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (token instanceof SsoUsernamePasswordToken) {
            return true;
        }

        try {
            UsernamePasswordToken upToken = (UsernamePasswordToken) token;
            mdService.login(upToken.getUsername(), new String(upToken.getPassword()), false);
            return true;
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            // 此Matcher中抛出的异常必须继承自AuthenticationException
            throw new AuthenticationException(e.getMessage(), e);
        }
    }

}
