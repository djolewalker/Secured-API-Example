package controllers;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import model.CustomExceptionResponse;
import model.User;
import security.LdapService;
import security.TokenGenerator;

@Path("/authenticate")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UserController {
    
    @Inject
    private TokenGenerator tokengnerator;

    @Inject
    private LdapService ldap;

    @POST()
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response authenticateUser(User authUser) {
        try {

            ldap.login(authUser);

            String token;
            token = tokengnerator.issueToken(authUser.getUsername());

            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomExceptionResponse(400, "Invalid username or password!", "Bad credentials")).build();
        }
    }
}
