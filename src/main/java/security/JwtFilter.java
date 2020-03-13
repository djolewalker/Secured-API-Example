package security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import javax.annotation.Priority;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.NameBinding;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import model.CustomExceptionResponse;
import properties.JediProperties;

@Priority(Priorities.AUTHORIZATION)
@Authorize
@Provider
public class JwtFilter implements ContainerRequestFilter {

    @Inject
    private JediProperties properties;

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        //////////////////////////////////////
        //   Dve mogucnosti za autorizaciju
        //////////////////////////////////////
        try {
            String token = "";
            // 1. Store token in header of all requests (litle complicate)
            if (crc.getHeaderString(HttpHeaders.AUTHORIZATION) != null) {
                String autorizationHeader = crc.getHeaderString(HttpHeaders.AUTHORIZATION);
                token = autorizationHeader.replace("Bearer ", "");
            }
            // 2. Store token in cookie
            if (token.isEmpty()) {
                token = crc.getCookies().get("jwt-token").getValue();
            }
            SecretKey key = Keys.hmacShaKeyFor(properties.getString("secret", "").getBytes());
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception e) {
            System.out.println(e);
            crc.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new CustomExceptionResponse(401, e.getMessage(), "Unauthorized")).build());
        }
    }

}
