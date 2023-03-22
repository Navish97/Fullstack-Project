package ntnu.idatt2105.project.backend.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ntnu.idatt2105.project.backend.model.enums.AuthenticationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY;

    //Uses constructor injection to inject the environment object, check if active spring boot profile is dev.
    //If the profile is dev that means the project is run locally, it then sets the key to the local key,
    // otherwise it is running on server and uses the key from the environment variable stored in server.
    @Autowired
    public JwtService(Environment environment, @Value("${MY_JWT_SECRET_KEY:}") String secretKey) {
        String LOCAL_SECRET_KEY = "THISKEYISONLYUSEDLOCALLYTHISKEYISONLYUSEDLOCALLYTHISKEYISONLYUSEDLOCALLY";
        this.SECRET_KEY = Arrays.asList(environment.getActiveProfiles()).contains("dev") ? LOCAL_SECRET_KEY : secretKey;
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutes
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        if (userDetails == null) {
            return false;
        }

        final String username = extractUsername(token);
        boolean isUsernameMatch = username.equals(userDetails.getUsername());
        boolean isTokenExpired = isTokenExpired(token);
        return (isUsernameMatch && !isTokenExpired);
    }

    public AuthenticationState getAuthenticationState(String token, UserDetails userDetails) {
        if (isTokenValid(token, userDetails)) {
            return AuthenticationState.AUTHENTICATED;
        }
        try {
            extractAllClaims(token);
        } catch (TokenExpiredException e) {
            return AuthenticationState.TOKEN_EXPIRED;
        } catch (Exception e) {
            // Ignore other exceptions
        }

        return AuthenticationState.UNAUTHENTICATED;
    }





    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) throws TokenExpiredException {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            //retrieved token expired date
            Date expiration = e.getClaims().getExpiration();
            throw new TokenExpiredException("Token is expired", expiration.toInstant());
        }
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
