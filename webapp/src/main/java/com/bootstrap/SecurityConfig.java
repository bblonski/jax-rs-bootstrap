package com.bootstrap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * @author bblonski
 */
@Named
//@Configuration
public class SecurityConfig {

    @Produces
    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    @Produces
    public org.apache.shiro.mgt.SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager();
    }

//    @Bean
//    @DependsOn("userPersistence")
//    public SecurityManager getManager(AuthorizingRealm myRealm) {
//        return new DefaultWebSecurityManager(myRealm);
//    }
//
//    @Bean
//    public LifecycleBeanPostProcessor getlLifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean(name = "shiroFilter")
//    public ShiroFilterFactoryBean getShiroFilter(SecurityManager manager) {
//        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
//        bean.setSecurityManager(manager);
//        bean.setLoginUrl("/login");
//        bean.setSuccessUrl("/");
//        bean.setUnauthorizedUrl("/error");
//        bean.setFilterChainDefinitions("/auth = authc");
//        return bean;
//    }

}
