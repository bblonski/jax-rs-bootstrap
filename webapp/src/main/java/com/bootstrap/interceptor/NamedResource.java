package com.bootstrap.interceptor;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * @author bblonski
 */
@Inherited
@Target({TYPE})
@Retention(RUNTIME)
public @interface NamedResource {
 String value();
}
