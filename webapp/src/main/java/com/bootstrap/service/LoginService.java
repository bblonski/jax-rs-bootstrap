package com.bootstrap.service;

import com.bootstrap.persistence.UserPersistence;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
@Path("/service")
@Produces(MediaType.APPLICATION_JSON)
@Named
@RequestScoped
public class LoginService {

    @Inject
    private UserPersistence userPersistence;
    @Inject
    private Subject subject;

    @POST
    @Path("/login")
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) {
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                subject.login(token);
            } catch (Exception e) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        return Response.ok().build();
    }

    @POST
    @Path("/logout")
    public Response logout() {
        subject.logout();
        return Response.ok().build();
    }

}
