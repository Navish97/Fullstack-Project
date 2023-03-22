package ntnu.idatt2105.project.backend.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.dto.ErrorResponse;
import ntnu.idatt2105.project.backend.dto.UserProfileDTO;
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
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Login Controller", description = "Controller to handle user login")
public class ProfileController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    Logger logger = Logger.getLogger(ProfileController.class.getName());


    @GetMapping("/my-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        try{
            User user = userService.findByEmail(jwtService.extractUsername(extractTokenFromCookie(request)));
            UserProfileDTO userProfileDTO = new UserProfileDTO(user.getName(), user.getEmail());
            return ResponseEntity.ok(userProfileDTO);
        }
        catch (TokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Token expired"));
        }
    }

    @GetMapping("/user-status")
    public ResponseEntity<?> getUserStatus(HttpServletRequest request) {
        try {
            String jwt = extractTokenFromCookie(request); // Extract the token from the cookie
            logger.info("JWT: " + jwt);
            if (jwt == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
            }
            User user = userService.findByEmail(jwtService.extractUsername(jwt)); // Pass the JWT token instead of the request
            logger.info("User: " + user);
            AuthenticationState state = jwtService.getAuthenticationState(jwt, user);

            logger.info("User status: " + state);

            return ResponseEntity.ok(state);
        } catch (TokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Token expired"));
        }
    }

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
}