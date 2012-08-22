package com.bootstrap.service;

import com.bootstrap.models.User;
import com.bootstrap.persistence.UserPersistence;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Resource service for users.
 *
 * @author Brian Blonski
 */

@Path("/service/user")
@Named
public class UserService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserPersistence userPersistence;
    @Context
    HttpHeaders headers;

    @Inject
    public UserService(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @GET
    public List<User> get() {
        validateUser();
        return userPersistence.findAll();
    }

    @POST
    public User post(@FormParam("firstName") String fistName, @FormParam("lastName") String lastName,
                     @FormParam("email") String email, @FormParam("password") String password) {
        User user = new User(fistName, lastName, email, password);
        user = userPersistence.saveAndFlush(user);
        log.debug("Saving user {}", user);
        return user;
    }

    private void validateUser() {
        try {
            Subject user = SecurityUtils.getSubject();
            if (!user.isAuthenticated()) {
                String username = headers.getRequestHeader("username").get(0);
                String password = headers.getRequestHeader("password").get(0);
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                token.setRememberMe(true);
                user.login(token);
            }
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
    }

    public UserPersistence getUserPersistence() {
        return userPersistence;
    }

    public void setUserPersistence(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }
}
