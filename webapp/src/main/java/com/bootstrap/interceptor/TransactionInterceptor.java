package com.bootstrap.interceptor;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * @author bblonski
 */
@Transaction
@Interceptor
public class TransactionInterceptor {
    @Inject
    private Logger log;
    @Inject
    private EntityManager em;

    @AroundInvoke
    public Object manageTransaction(InvocationContext context) throws Exception {
        EntityTransaction tx = em.getTransaction();
        log.trace("Starting Transaction.");
        if (!tx.isActive()) {
            tx.begin();
        } else {
            log.trace("Transaction already in progress.");
        }
        try {
            Object result = context.proceed();
            log.trace("Committing Transaction.");
            tx.commit();
            return result;
        } catch (Exception e) {
            tx.rollback();
            log.error("Transaction failed.  Rolling back.");
            throw e;
        }
    }
}
