package security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import properties.Properties;


public class TokenGenerator {
    
    @Inject
    private Properties properties;
    
    public String issueToken(String username) throws IOException {
        SecretKey key = Keys.hmacShaKeyFor(properties.getProperty("secret").getBytes());
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuer("My local PC")
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
}
