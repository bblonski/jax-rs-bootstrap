package com.bootstrap.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base Service interface for implementing CRUD operations.
 *
 * @param <T> Model class to provide CRUD services for.
 */
@Transactional
public interface BasePersistence<T> extends JpaRepository<T, Long> {

}
