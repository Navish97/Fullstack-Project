package ntnu.idatt2105.project.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.authentication.AuthenticationRequest;
import ntnu.idatt2105.project.backend.model.dto.AuthenticationResponse;
import ntnu.idatt2105.project.backend.model.authentication.RegisterRequest;
import ntnu.idatt2105.project.backend.exceptions.UserAlreadyExistsException;
import ntnu.idatt2105.project.backend.service.AuthenticationService;
import ntnu.idatt2105.project.backend.service.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Date;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Login Controller", description = "Controller to handle user login")
public class LoginController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    Logger logger = Logger.getLogger(LoginController.class.getName());


    /**
     * Registers a new user and returns an access token as an HttpOnly cookie.
     *
     * @param request RegisterRequest object containing the user's information.
     * @param response HTTPServletResponse containing the access token as a cookie.
     * @return ResponseEntity containing an AuthenticationResponse object with the access token.
     * Returns status code 200 if the user is registered, 400 if the user already exists.
     */
    @Operation(summary = "Registers a user",
            description = "Registers a new user and returns an access token as an HttpOnly cookie.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The access token as an HttpOnly cookie.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)))
            })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request, HttpServletResponse response) throws UserAlreadyExistsException {
        try {
            logger.info("Registering user " + request.getEmail());
            AuthenticationResponse authResponse = authenticationService.register(request);
            response.addCookie(getCookie(authResponse));
            logger.info("User " + request.getEmail() + " registered successfully");
            return ResponseEntity.ok(authResponse);
        } catch (UserAlreadyExistsException e) {
            logger.info("User " + request.getEmail() + " already exists");
            throw new UserAlreadyExistsException("User " + request.getEmail() + " already exists");
        }
    }

    /**
     * Authenticates a user and returns an access token as an HttpOnly cookie.
     *
     * @param authenticationRequest AuthenticationRequest object containing the user's email and password.
     * @param response HttpServletResponse containing the access token as a cookie.
     * @param request HttpServletRequest object containing the server name.
     * @return ResponseEntity containing an AuthenticationResponse object with the access token and user's role.
     * Returns status code 200 if the user is authenticated, 401 if the user is not authenticated.
     */
    @Operation(summary = "Authenticate a user",
            description = "Authenticates a user and returns an access token as an HttpOnly cookie.",
            parameters = {
                    @Parameter(name = "email", description = "The user's email address."),
                    @Parameter(name = "password", description = "The user's password.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "The access token as an HttpOnly cookie.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)))
            })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response, HttpServletRequest request){
        try {
            logger.info("Authenticating user " + authenticationRequest.getEmail());
            AuthenticationResponse authResponse = authenticationService.authenticate(authenticationRequest);
            Cookie cookie = getCookie(authResponse);
            if (!request.getServerName().equals("localhost")){
                logger.info("Setting cookie domain to " + request.getServerName());
                response.addHeader("Set-Cookie", cookieToHeaderWithSameSite(cookie));
            }
            else{
                logger.info("Setting cookie domain to localhost");
                cookie.setDomain("localhost");
                response.addCookie(cookie);
            }
            logger.info("Authentication successful");
            User user = userService.findByEmail(authenticationRequest.getEmail());
            authResponse.setUserId(user.getId());
            authResponse.setUserRole(user.getRole().toString());
            return ResponseEntity.ok(authResponse);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().errorMessage(e.getMessage()).build());
        }
    }

    /**
     * Logs out a user by deleting the access token cookie.
     *
     * @param request HttpServletRequest object containing the access token cookie.
     * @param response HttpServletResponse object for sending the response back.
     * @return ResponseEntity containing a message indicating successful logout.
     */
    @Operation(summary = "Logout endpoint for users",
            description = "Logs out a user by sending an expired access token cookie to overwrite the existing one.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful logout.")
            })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        logger.info("Logging out user");
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("myMarketPlaceAccessToken")){
                    logger.info("Deleting cookie");
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        logger.info("User logged out");
        return ResponseEntity.ok("Logged out");
    }

    /**
     * Creates an access token as an HttpOnly cookie.
     *
     * @param authResponse AuthenticationResponse object containing the access token.
     * @return Cookie object containing the access token as an HttpOnly cookie.
     */
    public Cookie getCookie(AuthenticationResponse authResponse){
        // Set access token as an HttpOnly cookie
        Cookie accessTokenCookie = new Cookie("myMarketPlaceAccessToken", authResponse.getToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(20 * 60); // 5 minutes

        return accessTokenCookie;
    }

    /**
     * Converts a cookie to a header with the SameSite attribute set to "none".
     * This is done because the HTTPOnly attribute is not supported by the browser when SameSite is set to "None".
     * It's worth noting that since all communication happens over HTTPS, the Secure attribute is not as relevant.
     * @param cookie Cookie object to be converted to a header.
     * @return String containing the cookie header with SameSite attribute set to "none".
     */
    private String cookieToHeaderWithSameSite(Cookie cookie) {
        return cookie.getName() + "=" + cookie.getValue() + "; Path=" + cookie.getPath() + "; Max-Age=" + cookie.getMaxAge() + "; Secure; HttpOnly; SameSite=" + "none";
    }

}