package controllers;

import java.util.HashMap;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import model.User;
import security.LdapService;
import security.SecretUserReader;
import security.TokenGenerator;
import security.TokenService;

@Path("/authenticate")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class UserController {

    @Context
    private UriInfo context;
    
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

            TokenService.readTokens();
            String token;
            if (TokenService.checkUser(authUser)) {
                token = TokenService.getToken(authUser);
            } else {
                token = tokengnerator.issueToken(authUser.getUsername());
                TokenService.addUserToken(authUser, token);
                TokenService.writeToken();
            }

            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(400, "bad credentials").build();
        }
    }

    

}
