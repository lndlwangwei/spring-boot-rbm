/*
 *  Copyright (C) 2017 the xkw.com authors.
 *  http://www.xkw.com
 */

package com.xkw.rbm.configuration.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * @author flyspider
 * @since 1.0
 */
public class RestAuthenticationInfo implements AuthenticationInfo {

    private static final long serialVersionUID = 2876150227225437166L;

    private final String realm;

    private Object principal;

    public RestAuthenticationInfo(final Object principal, final String realm) {
        this.principal = principal;
        this.realm = realm;
    }

    public Object getPrincipal() {
        return principal;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return new SimplePrincipalCollection(principal, realm);
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
