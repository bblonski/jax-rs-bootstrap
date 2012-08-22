package com.bootstrap;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base service unit test.  Specifies test database configuration and common service test functionality.  Service test
 * classes should extend this class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceConfig.class} )
public abstract class BasePersistenceTest {
}
