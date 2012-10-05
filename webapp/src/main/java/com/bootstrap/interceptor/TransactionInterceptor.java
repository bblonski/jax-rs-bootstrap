package com.bootstrap.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(TransactionInterceptor.class);

    @Inject
    private EntityManager em;

    @AroundInvoke
    public Object manageTransaction(InvocationContext context) throws Exception {
        EntityTransaction tx = em.getTransaction();
        log.trace("Starting Transaction");
        tx.begin();
        try {
            Object result = context.proceed();
            log.trace("Committing Transaction");
            tx.commit();
            return result;
        }catch (Exception e) {
            tx.rollback();
            log.error("Transaction failed.  Rolling back.");
            throw e;
        }
    }
}
