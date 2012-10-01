package com.bootstrap.service;

import com.bootstrap.persistence.UserPersistence;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author bblonski
 */
@Path("/service/login")
@Produces(MediaType.APPLICATION_JSON)
@Named
@RequestScoped
public class LoginService {

    @Inject
    private UserPersistence userPersistence;

    @POST
    public Response login(@FormParam("username") String username,
                      @FormParam("password") String password) {
//        Subject user = SecurityUtils.getSubject();
//        if(!user.isAuthenticated()) {
//            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//            token.setRememberMe(true);
//            user.login(token);
//        }
        return Response.ok().build();
    }
}
