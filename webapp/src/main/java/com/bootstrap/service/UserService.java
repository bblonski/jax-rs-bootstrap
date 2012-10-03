package com.bootstrap.service;

import com.bootstrap.interceptor.NamedResource;
import com.bootstrap.interceptor.Secured;
import com.bootstrap.interceptor.Transaction;
import com.bootstrap.models.User;
import com.bootstrap.persistence.UserPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;

/**
 * Resource service for users.
 *
 * @author Brian Blonski
 */

@Path("/service/user")
@RequestScoped
@Named
@NamedResource("user")
public class UserService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserPersistence userPersistence;

    @GET
    @Secured
    public List<User> getUser() {
        return userPersistence.findAll();
    }

    @POST
    @Transaction
    public User post(@FormParam("firstName") String fistName, @FormParam("lastName") String lastName,
                     @FormParam("email") String email, @FormParam("password") String password) {
        User user = new User(fistName, lastName, email, password);
        user = userPersistence.saveAndFlush(user);
        log.debug("Saving user {}", user);
        return user;
    }
}
