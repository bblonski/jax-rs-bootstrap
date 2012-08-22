package com.bootstrap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import javax.inject.Named;

/**
 * @author bblonski
 */
@Named
@Configuration
@Import({SecurityConfig.class, PersistenceConfig.class})
@ImportResource("classpath:spring.xml")
@ComponentScan({"com.bootstrap", "com.bootstrap.persistence"})
public class AppConfig {
}
