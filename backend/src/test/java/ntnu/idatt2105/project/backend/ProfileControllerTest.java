package ntnu.idatt2105.project.backend;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import ntnu.idatt2105.project.backend.controller.ProfileController;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.dto.UserProfileDTO;
import ntnu.idatt2105.project.backend.model.dto.response.UserStatusResponse;
import ntnu.idatt2105.project.backend.model.enums.AuthenticationState;
import ntnu.idatt2105.project.backend.model.enums.Role;
import ntnu.idatt2105.project.backend.service.AuthenticationService;
import ntnu.idatt2105.project.backend.service.CookieService;
import ntnu.idatt2105.project.backend.service.JwtService;
import ntnu.idatt2105.project.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProfileControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private CookieService cookieService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ProfileController profileController;

    @Test
    public void testGetMyProfile() throws TokenExpiredException {
        String email = "test@example.com";
        String name = "Test User";
        UserProfileDTO userProfileDTO = new UserProfileDTO(name , email);
        Cookie cookie = new Cookie("token" , "test-token");
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        when(cookieService.extractTokenFromCookie(request)).thenReturn("test-token");
        when(jwtService.extractUsername("test-token")).thenReturn(email);
        when(userService.findByEmail(email)).thenReturn(user);

        ResponseEntity<?> response = profileController.getMyProfile(request);

        assertEquals(HttpStatus.OK , response.getStatusCode());
        assertEquals(userProfileDTO , response.getBody());
    }

    @Test
    public void testGetUserStatus() throws TokenExpiredException {
        String email = "test@example.com";
        String role = "USER";
        Cookie cookie = new Cookie("token" , "test-token");
        User user = new User();
        user.setEmail(email);
        user.setRole(Role.valueOf(role));

        when(cookieService.extractTokenFromCookie(request)).thenReturn("test-token");
        when(jwtService.extractUsername("test-token")).thenReturn(email);
        when(userService.findByEmail(email)).thenReturn(user);
        when(jwtService.getAuthenticationState("test-token" , user)).thenReturn(AuthenticationState.AUTHENTICATED);

        ResponseEntity<?> response = profileController.getUserStatus(request);

        assertEquals(HttpStatus.OK , response.getStatusCode());

        UserStatusResponse expectedResponse = new UserStatusResponse(AuthenticationState.AUTHENTICATED , role);
        assertEquals(expectedResponse , response.getBody());
    }
}
