package com.bootstrap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

/**
 * @author bblonski
 */
@Singleton
public class SecurityConfig {

    @Produces
    public Subject getSubject(DefaultSecurityManager securityManager) {
        SecurityUtils.setSecurityManager(securityManager);
        return SecurityUtils.getSubject();
    }

    @Produces
    public org.apache.shiro.mgt.DefaultSecurityManager getSecurityManager(DBRealm realm) {
        DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

}
