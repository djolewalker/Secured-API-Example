package security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import javax.annotation.Priority;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import model.CustomExceptionResponse;
import properties.Properties;

@Provider
@Authorize
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Inject
    private Properties properties;

    @Override
    public void filter(ContainerRequestContext crc) {

        String autorizationHeader = crc.getHeaderString(HttpHeaders.AUTHORIZATION);

        try {
            String token = autorizationHeader.replace("Bearer ", "");
            SecretKey key = Keys.hmacShaKeyFor(properties.getProperty("secret").getBytes());
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            System.out.println("uspesna validacija");
        } catch (Exception e) {
            crc.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new CustomExceptionResponse(401, e.getMessage(), "Unauthorized")).build());
        }
    }

}
