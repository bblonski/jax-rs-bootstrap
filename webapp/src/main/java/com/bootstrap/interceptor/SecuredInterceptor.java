package com.bootstrap.interceptor;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author bblonski
 */
@Secured
@Interceptor
public class SecuredInterceptor {
    @Inject
    private Logger log;
    @Inject
    private Subject subject;

    @AroundInvoke
    public Object interceptSecure(InvocationContext ctx) throws Exception {
        log.info("Securing {} {}", new Object[]{ctx.getMethod(),
                ctx.getParameters()});
        log.debug("Principal is: {}", subject.getPrincipal());

        final Class<? extends Object> runtimeClass = ctx.getTarget().getClass();
        log.debug("Runtime extended classes: {}", runtimeClass.getClasses());
        log.debug("Runtime implemented interfaces: {}", runtimeClass.getInterfaces());
        log.debug("Runtime annotations ({}): {}", runtimeClass.getAnnotations().length, runtimeClass.getAnnotations());

        final Class<?> declaringClass = ctx.getMethod().getDeclaringClass();
        log.debug("Declaring class: {}", declaringClass);
        log.debug("Declaring extended classes: {}", declaringClass.getClasses());
        log.debug("Declaring annotations ({}): {}", declaringClass.getAnnotations().length, declaringClass.getAnnotations());

        String entityName;
        try {
            NamedResource namedResource = runtimeClass.getAnnotation(NamedResource.class);
            entityName = namedResource.value();
            log.debug("Got @NamedResource={}", entityName);
        } catch (NullPointerException e) {
            entityName = declaringClass.getSimpleName().toLowerCase(); // TODO: should be lowerFirst camelCase
            log.warn("@NamedResource not annotated, inferred from declaring classname: {}", entityName);
        }

        String action = "admin";
        if (ctx.getMethod().getName().matches("get[A-Z].*"))
            action = "view";
        if (ctx.getMethod().getName().matches("set[A-Z].*"))
            action = "edit";
        String permission = String.format("%s:%s", action, entityName);
        log.info("Checking permission '{}' for user '{}'", permission, subject.getPrincipal());
        try {
            subject.checkPermission(permission);
        } catch (Exception e) {
            log.error("Access denied - {}: {}", e.getClass().getName(), e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN).build());
        }
        return ctx.proceed();
    }
}
