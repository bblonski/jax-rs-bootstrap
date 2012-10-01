package com.bootstrap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.enterprise.inject.Produces;

/**
 * @author bblonski
 */
public class SecurityConfig {

    @Produces
    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    @Produces
    public org.apache.shiro.mgt.SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager();
    }

}
