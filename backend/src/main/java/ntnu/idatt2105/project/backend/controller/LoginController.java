package ntnu.idatt2105.project.backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.authentication.AuthenticationRequest;
import ntnu.idatt2105.project.backend.authentication.AuthenticationResponse;
import ntnu.idatt2105.project.backend.authentication.RegisterRequest;
import ntnu.idatt2105.project.backend.exceptions.UserAlreadyExistsException;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.service.AuthenticationService;
import ntnu.idatt2105.project.backend.service.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
@Tag(name = "Login Controller", description = "Controller to handle user login")
public class LoginController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        try {
            return ResponseEntity.ok(authenticationService.register(request));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().errorMessage(e.getMessage()).build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        try {
            AuthenticationResponse authResponse = authenticationService.authenticate(request);

            // Set access token as an HttpOnly cookie
            Cookie accessTokenCookie = new Cookie("myMarketPlaceAccessToken", authResponse.getToken());
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(5 * 60); // 5 minutes
            response.addCookie(accessTokenCookie);

            return ResponseEntity.ok(authResponse);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().errorMessage(e.getMessage()).build());
        }
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