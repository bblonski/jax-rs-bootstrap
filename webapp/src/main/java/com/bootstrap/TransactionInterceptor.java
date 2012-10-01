package com.bootstrap;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * @author bblonski
 */
@Transaction @Interceptor
public class TransactionInterceptor {

    @Inject
    EntityManager em;

    @AroundInvoke
    public Object manageTransaction(InvocationContext context) throws Exception {
        EntityTransaction tx = em.getTransaction();
        System.out.println("starting tx");
        tx.begin();
        Object result = context.proceed();
        System.out.println("stopping tx");
        tx.commit();
        return result;
    }
}
