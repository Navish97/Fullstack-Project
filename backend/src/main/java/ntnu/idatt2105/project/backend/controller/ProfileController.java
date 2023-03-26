package ntnu.idatt2105.project.backend.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.dto.ErrorResponse;
import ntnu.idatt2105.project.backend.model.dto.UserProfileDTO;
import ntnu.idatt2105.project.backend.model.enums.AuthenticationState;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.service.AuthenticationService;
import ntnu.idatt2105.project.backend.service.CookieService;
import ntnu.idatt2105.project.backend.service.JwtService;
import ntnu.idatt2105.project.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Login Controller", description = "Controller to handle user login")
public class ProfileController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    private final CookieService cookieService;

    Logger logger = Logger.getLogger(ProfileController.class.getName());

    /**
     * Retrieves the profile of the authenticated user.
     *
     * @param request the HTTP request containing the JWT token in the cookie
     * @return ResponseEntity containing the user's profile information
     */
    @Operation(summary = "Gets the profile of an authenticated user",
            description = "Retrieves the profile information of the authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "If authenticated, the authenticated user's profile information.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserProfileDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Returns error when authentication fails",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserProfileDTO.class)))
            })
    @GetMapping("/my-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        try{
            logger.info("Received request to get user profile");
            User user = userService.findByEmail(jwtService.extractUsername(cookieService.extractTokenFromCookie(request)));
            UserProfileDTO userProfileDTO = new UserProfileDTO(user.getName(), user.getEmail());
            logger.info("Returning user profile");
            return ResponseEntity.ok(userProfileDTO);
        }
        catch (TokenExpiredException e) {
            logger.info("Token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Token expired"));
        }
    }

    /**
     * Retrieves the authentication status of the user.
     *
     * @param request the HTTP request containing the JWT token in the cookie
     * @return ResponseEntity with status code 200 if the user is authenticated, 401 if the user is not authenticated
     */
    @Operation(summary = "Gets the authentication status of a user",
            description = "Retrieves the authentication status of the user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "When user is authenticated.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationState.class))),
                    @ApiResponse(responseCode = "401", description = "When authentication fails.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationState.class))),
            })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user-status")
    public ResponseEntity<?> getUserStatus(HttpServletRequest request) {
        try {
            String jwt = cookieService.extractTokenFromCookie(request); // Extract the token from the cookie
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
}