package com.bootstrap.service;

import com.bootstrap.NamedResource;
import com.bootstrap.Secured;
import com.bootstrap.Transaction;
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
@Secured
@NamedResource("user")
public class UserService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserPersistence userPersistence;
    @Context
    private HttpHeaders headers;

    @GET
    public List<User> getUser() {
//        validateUser();
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

//    private void validateUser() {
//        try {
//            Subject user = SecurityUtils.getSubject();
//            if (!user.isAuthenticated()) {
//                String username = headers.getRequestHeader("username").get(0);
//                String password = headers.getRequestHeader("password").get(0);
//                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//                token.setRememberMe(true);
//                user.login(token);
//            }
//        } catch (Exception e) {
//            throw new WebApplicationException(Response.Status.FORBIDDEN);
//        }
//    }

}
