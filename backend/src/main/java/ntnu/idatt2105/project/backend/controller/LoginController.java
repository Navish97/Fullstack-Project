package ntnu.idatt2105.project.backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.authentication.AuthenticationRequest;
import ntnu.idatt2105.project.backend.model.dto.AuthenticationResponse;
import ntnu.idatt2105.project.backend.model.authentication.RegisterRequest;
import ntnu.idatt2105.project.backend.exceptions.UserAlreadyExistsException;
import ntnu.idatt2105.project.backend.service.AuthenticationService;
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
    Logger logger = Logger.getLogger(LoginController.class.getName());


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request, HttpServletResponse response){
        try {
            AuthenticationResponse authResponse = authenticationService.register(request);
            response.addCookie(getCookie(authResponse));

            return ResponseEntity.ok(authResponse);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().errorMessage(e.getMessage()).build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response, HttpServletRequest request){
        try {
            AuthenticationResponse authResponse = authenticationService.authenticate(authenticationRequest);
            Cookie cookie = getCookie(authResponse);
            if (!request.getServerName().equals("localhost")){
                cookie.setDomain("https://mymarketplace-xt5ws57jza-lz.a.run.app");
                response.addHeader("Set-Cookie", cookieToHeaderWithSameSite(cookie));
            }
            else{
                cookie.setDomain("localhost");
                response.addCookie(cookie);
            }
            return ResponseEntity.ok(authResponse);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().errorMessage(e.getMessage()).build());
        }
    }

    public Cookie getCookie(AuthenticationResponse authResponse){
        // Set access token as an HttpOnly cookie
        Cookie accessTokenCookie = new Cookie("myMarketPlaceAccessToken", authResponse.getToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(5 * 60); // 5 minutes

        //cookie expires at "date"
        logger.info("Cookie expires at: " + new Date(System.currentTimeMillis() + 5 * 60 * 1000));
        return accessTokenCookie;
    }

    private String cookieToHeaderWithSameSite(Cookie cookie) {
        return cookie.getName() + "=" + cookie.getValue() + "; Path=" + cookie.getPath() + "; Max-Age=" + cookie.getMaxAge() + "; Secure; HttpOnly; SameSite=" + "none" + "; Domain=" + cookie.getDomain();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("myMarketPlaceAccessToken")){
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return ResponseEntity.ok("Logged out");
    }
}