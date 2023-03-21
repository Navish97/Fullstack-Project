package ntnu.idatt2105.project.backend.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.dto.ErrorResponse;
import ntnu.idatt2105.project.backend.enums.AuthenticationState;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.service.AuthenticationService;
import ntnu.idatt2105.project.backend.service.JwtService;
import ntnu.idatt2105.project.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Login Controller", description = "Controller to handle user login")
public class ProfileController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;


    @GetMapping("/my-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        try {
            String jwt = extractTokenFromCookie(request); // Extract the token from the cookie
            if (jwt == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
            }
            User user = userService.findByEmail(jwtService.extractUsername(jwt)); // Pass the JWT token instead of the request
            return ResponseEntity.ok(user);
        } catch (TokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Token expired"));
        }
    }


    /*
    @GetMapping("/my-profile")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        Optional<Cookie> maybeAccessToken = extractTokenFromCookie(request);
        if (maybeAccessToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        String accessToken = maybeAccessToken.get().getValue();

        User user = null;
        try{
            user = userService.findByEmail(jwtService.extractUsername(accessToken));
            AuthenticationState state = jwtService.getAuthenticationState(accessToken, user);
            System.out.println(state);

            if (state == AuthenticationState.AUTHENTICATED) {
                return ResponseEntity.ok(user);
            } else if (state == AuthenticationState.TOKEN_EXPIRED) {
                throw new TokenExpiredException("Token expired", jwtService.extractExpiration(accessToken).toInstant());
            } else if (state == AuthenticationState.UNAUTHENTICATED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
            }
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        catch (TokenExpiredException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Token expired"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
        }


        return ResponseEntity.ok(user);
    }

     */

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("myMarketPlaceAccessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }







    /*
    @PostMapping
    @Operation(summary = "User login", description = "Authenticates a user and returns the user object if successful")
    public ResponseEntity<User> login(@RequestBody User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(existingUser);
        }
        return ResponseEntity.status(401).build();
    }


     */
}