/*
 *  Copyright (C) 2017 the xkw.com authors.
 *  http://www.xkw.com
 */

package com.xkw.rbm.configuration.shiro;

import com.xkw.commons.util.StringUtils;
import com.xkw.mdm.dto.UserInfo;
import com.xkw.rbm.support.MdService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author flyspider
 * @since 1.0
 */
@Component
public class RestUserAuthRealm extends AuthorizingRealm {

    @Autowired
    private MdService mdService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        if (!StringUtils.isEmpty(userName)) {
            UserInfo userInfo = mdService.getUserInfo(userName);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRoles(userInfo.getInfo().getAuthz().getRoles());
            info.addStringPermissions(userInfo.getInfo().getAuthz().getPermissions());
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return new RestAuthenticationInfo(((UsernamePasswordToken) token).getUsername(), getName());
    }
}
