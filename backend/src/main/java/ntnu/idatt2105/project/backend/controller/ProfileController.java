package ntnu.idatt2105.project.backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.authentication.AuthenticationRequest;
import ntnu.idatt2105.project.backend.authentication.AuthenticationResponse;
import ntnu.idatt2105.project.backend.authentication.RegisterRequest;
import ntnu.idatt2105.project.backend.exceptions.UserAlreadyExistsException;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.service.AuthenticationService;
import ntnu.idatt2105.project.backend.service.JwtService;
import ntnu.idatt2105.project.backend.service.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
@Tag(name = "Login Controller", description = "Controller to handle user login")
public class ProfileController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;


    @GetMapping("/my-profile")
    public ResponseEntity<User> getMyProfile(HttpServletRequest request) {
        Optional<Cookie> maybeAccessToken = extractTokenFromCookie(request);
        System.out.println("maybeAccessToken: " + maybeAccessToken);
        if (maybeAccessToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String accessToken = maybeAccessToken.get().getValue();
        //username is email
        String email = jwtService.extractUsername(accessToken);

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(user);
    }

    private Optional<Cookie> extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("myMarketPlaceAccessToken".equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
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