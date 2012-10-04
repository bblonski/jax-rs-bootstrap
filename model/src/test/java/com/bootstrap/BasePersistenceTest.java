package com.bootstrap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import javax.persistence.EntityManager;

/**
 * Base service unit test.  Specifies test database configuration and common service test functionality.  Service test
 * classes should extend this class.
 */
public abstract class BasePersistenceTest {
    protected static PersistenceConfig config;
    protected static EntityManager em;
    protected static JpaRepositoryFactory factory;

    @BeforeClass
    public static void setupTransaction() {
        config = new PersistenceConfig();
        em = config.getEm();
        factory = config.getRepositoryFactory(em);
    }

    @Before
    public void beginTransaction() {
        em.getTransaction().begin();
    }

    @After
    public void teardownTransaction() {
        em.getTransaction().rollback();
    }
}
