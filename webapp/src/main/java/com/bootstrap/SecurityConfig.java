package com.bootstrap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
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
    public DefaultSecurityManager getSecurityManager(DBRealm realm) {
        DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

}
