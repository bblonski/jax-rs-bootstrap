package com.bootstrap;

import com.bootstrap.persistence.UserPersistence;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Spring Configuration for webapp persistence context.  This file defines all the
 * class instances used in Spring dependency injection for the persistence layer.
 */
@Named
public class PersistenceConfig {
    private static final Logger log = LoggerFactory.getLogger(PersistenceConfig.class);

    public UserPersistence createUserPersistence(JpaRepositoryFactory factory) {
        return factory.getRepository(UserPersistence.class);
    }

    @Produces
    @ApplicationScoped
    public EntityManagerFactory getEntityManagerFactory(EntityManager em) {
        return em.getEntityManagerFactory();
    }

    public void closeEntityManagerFactory(@Disposes EntityManagerFactory factory) {
        factory.close();
    }

    @Produces
    @ApplicationScoped
    public EntityManager getEm() {
        Map<String, String> properties = new HashMap<String, String>();
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("db.properties");
            Iterator<String> keys = config.getKeys();
            for (Iterator<String> i = config.getKeys(); i.hasNext(); ) {
                String key = i.next();
                properties.put(key, config.getString(key));
            }
        } catch (ConfigurationException e) {
            log.warn("No db.properties file found, using in-memory database", e);
            properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            properties.put("hibernate.connection.url", "jdbc:h2:mem:test;");
            properties.put("hibernate.connection.driver_class", "org.h2.Driver");
            properties.put("hibernate.connection.username", "");
            properties.put("hibernate.connection.password", "");
        }
        return Persistence.createEntityManagerFactory("mem", properties).createEntityManager();
    }

    public void closeEm(@Disposes EntityManager em) {
        em.close();
    }

    @Produces
    public JpaRepositoryFactory getRepositoryFactory(EntityManager em) {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(em);
        return factory;
    }

    @Produces
    public Logger getLogger(InjectionPoint ic) {
        return LoggerFactory.getLogger(ic.getMember().getDeclaringClass());
    }
}
