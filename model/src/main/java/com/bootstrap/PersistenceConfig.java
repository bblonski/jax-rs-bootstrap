package com.bootstrap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Spring Configuration for webapp persistence context.  This file defines all the
 * class instances used in Spring dependency injection for the persistence layer.
 */
@Named
@Configuration
@ImportResource("classpath:spring.xml")
public class PersistenceConfig {

    @Bean
    public EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("mem").createEntityManager().getEntityManagerFactory();
    }

    @Bean
    public HibernateExceptionTranslator getPersistenceExceptionTranslationPostProcessor() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(getEntityManagerFactory());
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

}
