package controllers;

import model.JwtTokenPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import model.CustomExceptionResponse;
import model.User;
import security.LdapService;
import security.TokenGenerator;

@Path("/authenticate")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Api(value = "authenticate", tags = {"authenticate"})
public class UserController {

    @Inject
    private TokenGenerator tokengnerator;

    @Inject
    private LdapService ldap;

    @POST()
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "authenticate", notes = "Take your jwt token", response = JwtTokenPojo.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad credentials"),
    })
    public Response authenticateUser(User authUser) {
        try {

            ldap.login(authUser);

            JwtTokenPojo token = new JwtTokenPojo();
            token.token = tokengnerator.issueToken(authUser.getUsername());
            token.username = authUser.getUsername();

            return Response.ok(token).cookie(new NewCookie("jwt-token", token.token, "/", "localhost", "", 86400, false, false)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new CustomExceptionResponse(400, "Invalid username or password!", "Bad credentials")).build();
        }
    }
}