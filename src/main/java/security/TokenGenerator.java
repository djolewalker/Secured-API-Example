package security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import properties.JediProperties;

public class TokenGenerator {

    @Inject
    private JediProperties properties;

    public String issueToken(String username) throws IOException {
        SecretKey key = Keys.hmacShaKeyFor(properties.getString("secret", "").getBytes());
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuer("My local PC")
                .setIssuedAt(new Date())
                .setExpiration(new Date(Calendar.getInstance().getTimeInMillis() + (long) ((Double.parseDouble(properties.getString("token.exp.time", "10")) * 60000))))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
}
