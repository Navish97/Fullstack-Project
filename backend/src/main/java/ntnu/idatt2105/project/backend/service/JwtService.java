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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**

 The JwtService class provides methods for generating, validating, and extracting information from JSON Web Tokens (JWTs).

 It allows for the generation of a JWT containing a user's authorities (roles), which can be used for authorization purposes.

 Additionally, it can validate whether a JWT is currently valid and retrieve the roles contained within a JWT.
 */
@Service
public class JwtService {

    private final String SECRET_KEY;

    /**

     Constructs a new JwtService object.
     The constructor uses constructor injection to inject the environment object to determine whether the application
     is being run locally or on a server. If the active profile is "dev", then a local secret key is used. Otherwise, it
     retrieves the secret key from an environment variable stored in the server.

     //Uses constructor injection to inject the environment object, check if active spring boot profile is dev.
     //If the profile is dev that means the project is run locally, it then sets the key to the local key,
     // otherwise it is running on server and uses the key from the environment variable stored in server.
     @param environment the Environment object representing the application's environment
     @param secretKey the secret key to use for generating and validating JWTs
     */
    @Autowired
    public JwtService(Environment environment, @Value("${MY_JWT_SECRET_KEY:}") String secretKey) {
        String LOCAL_SECRET_KEY = "THISKEYISONLYUSEDLOCALLYTHISKEYISONLYUSEDLOCALLYTHISKEYISONLYUSEDLOCALLY";
        this.SECRET_KEY = Arrays.asList(environment.getActiveProfiles()).contains("dev") ? LOCAL_SECRET_KEY : secretKey;
    }
    /**

     Extracts the username from a JWT token.
     @param token the JWT token
     @return the username contained within the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**

     Extracts a claim from a JWT token using a given function.
     @param token the JWT token
     @param claimsResolver the function used to extract the desired claim from the token's claims
     @param <T> the type of the extracted claim
     @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    /**

     Generates a JWT containing a user's authorities (roles).
     @param userDetails the UserDetails object representing the user for which to generate the token
     @return the generated JWT
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        extraClaims.put("roles", roles);
        return generateToken(extraClaims, userDetails);
    }


    /**

     Generates a JWT containing additional claims and a user's authorities (roles).
     @param extraClaims the additional claims to include in the token
     @param userDetails the UserDetails object representing the user for which to generate the token
     @return the generated JWT
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**

     Validates whether a given JWT token is currently valid for a given user.

     @param token the JWT token to validate

     @param userDetails the UserDetails object representing the user

     @return true if the token is currently valid for the user, false otherwise
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        if (userDetails == null) {
            return false;
        }

        final String username = extractUsername(token);
        boolean isUsernameMatch = username.equals(userDetails.getUsername());
        boolean isTokenExpired = isTokenExpired(token);
        return (isUsernameMatch && !isTokenExpired);
    }


    /**

     Determines the authentication state of a given JWT token and user.
     @param token the JWT token to check
     @param userDetails the UserDetails object representing the user
     @return the authentication state of the token and user
     (AUTHENTICATED if the token is valid, TOKEN_EXPIRED if the token is expired, or UNAUTHENTICATED otherwise)
     */
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





    /**

     Determines whether a given JWT token is currently expired.
     @param token the JWT token to check
     @return true if the token is currently expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    /**

     Extracts the expiration date of a given JWT token.
     @param token the JWT token to extract the expiration date from
     @return the expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**

     Extracts all claims from a given JWT token.
     @param token the JWT token to extract claims from
     @return a Claims object containing all claims from the token
     @throws TokenExpiredException if the token is expired
     */
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


    /**
     Generates a signing key to use for signing and verifying JWT tokens.
     @return the generated Key object
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
    Extracts the roles from a given JWT token.
    @param token the JWT token to extract roles from
    @return a list of roles contained within the token
    */
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> (List<String>) claims.get("roles", List.class));
    }


}
