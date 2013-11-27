package com.rootls.base.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-11-27
 * Time: 下午1:44
 * To change this template use File | Settings | File Templates.
 */
public class SaltAwareJdbcRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
