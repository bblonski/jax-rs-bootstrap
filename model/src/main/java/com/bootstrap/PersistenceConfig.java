package com.bootstrap;

import com.bootstrap.persistence.UserPersistence;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
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

    @Produces
    UserPersistence createUserPersistence(JpaRepositoryFactory factory) {
        return factory.getRepository(UserPersistence.class);
    }

    @Produces
    @ApplicationScoped
    public EntityManagerFactory getEntityManagerFactory(EntityManager em) {
        return em.getEntityManagerFactory();
    }

    @Produces
    public EntityManager getEm() {
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(Persistence.createEntityManagerFactory("mem"));
        return em;
    }

    @Produces
    public HibernateExceptionTranslator getPersistenceExceptionTranslationPostProcessor() {
        return new HibernateExceptionTranslator();
    }

    @Produces
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Produces
    public JpaRepositoryFactory getRepositoryFactory(EntityManager em, final PlatformTransactionManager xact) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(em);
        factory.addRepositoryProxyPostProcessor(new RepositoryProxyPostProcessor() {
            @Override
            public void postProcess(ProxyFactory proxyFactory) {
                proxyFactory.addAdvice(new TransactionInterceptor(xact, new AnnotationTransactionAttributeSource()));
            }
        });
        return factory;
    }

    @Produces
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

}
