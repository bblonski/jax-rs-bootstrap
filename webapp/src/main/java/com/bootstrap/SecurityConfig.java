package com.bootstrap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Named;

/**
 * @author bblonski
 */
@Named
@Configuration
public class SecurityConfig {

    @Bean
    @DependsOn("userPersistence")
    public SecurityManager getManager(AuthorizingRealm myRealm) {
        return new DefaultWebSecurityManager(myRealm);
    }

    @Bean
    public LifecycleBeanPostProcessor getlLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilter(SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/");
        bean.setUnauthorizedUrl("/error");
        bean.setFilterChainDefinitions("/auth = authc");
        return bean;
    }

}
