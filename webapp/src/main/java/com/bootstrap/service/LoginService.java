package com.bootstrap.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author bblonski
 */
@Path("/service/login")
@Produces(MediaType.APPLICATION_JSON)
@Named
public class LoginService {

    @POST
    public Response login(@FormParam("username") String username,
                      @FormParam("password") String password) {
        Subject user = SecurityUtils.getSubject();
        if(!user.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            user.login(token);
        }
        return Response.ok().build();
    }
}
