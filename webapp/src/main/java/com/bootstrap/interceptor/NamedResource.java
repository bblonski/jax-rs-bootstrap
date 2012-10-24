package com.bootstrap.interceptor;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author bblonski
 */
@Inherited
@Target({TYPE})
@Retention(RUNTIME)
public @interface NamedResource {
    String value();
}
