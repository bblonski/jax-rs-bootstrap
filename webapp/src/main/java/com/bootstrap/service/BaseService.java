package com.bootstrap.service;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Base REST Service.
 */
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public abstract class BaseService {
}
