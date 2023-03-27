package ntnu.idatt2105.project.backend;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ntnu.idatt2105.project.backend.controller.LoginController;
import ntnu.idatt2105.project.backend.exceptions.UserAlreadyExistsException;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.model.authentication.AuthenticationRequest;
import ntnu.idatt2105.project.backend.model.authentication.RegisterRequest;
import ntnu.idatt2105.project.backend.model.dto.AuthenticationResponse;
import ntnu.idatt2105.project.backend.model.enums.Role;
import ntnu.idatt2105.project.backend.service.AuthenticationService;
import ntnu.idatt2105.project.backend.service.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletResponse;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class AuthenticationTests {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private HttpServletResponse httpServletResponse;


    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private User user;

    @Test
    public void register_returnsOkResponse() throws UserAlreadyExistsException {
        RegisterRequest registerRequest = RegisterRequest.builder().email("email").name("name").password("password").build();
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().token("token").build();

        when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = loginController.register(registerRequest, httpServletResponse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
    }

    @Test
    public void register_throwsUserAlreadyExistsException() throws UserAlreadyExistsException {
        RegisterRequest registerRequest = new RegisterRequest();

        when(authenticationService.register(registerRequest)).thenThrow(UserAlreadyExistsException.class);

        assertThrows(UserAlreadyExistsException.class, () -> {
            loginController.register(registerRequest, httpServletResponse);
        });
    }

    @Test
    public void authenticate_returnsOkResponse() throws InvalidCredentialsException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().token("token").build();

        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        // Set up the mock to return a server name
        when(httpServletRequest.getServerName()).thenReturn("example.com");

        // Set up the mock to return a non-null User object
        when(userService.findByEmail(authenticationRequest.getEmail())).thenReturn(user);
        when(user.getId()).thenReturn(String.valueOf(UUID.randomUUID()));

        // Set up the mock to return a non-null Role object
        when(user.getRole()).thenReturn(Role.ADMIN); // Replace Role.USER with the appropriate enum constant

        ResponseEntity<AuthenticationResponse> response = loginController.authenticate(authenticationRequest, httpServletResponse, httpServletRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
    }






    @Test
    public void authenticate_throwsInvalidCredentialsException() throws InvalidCredentialsException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        when(authenticationService.authenticate(authenticationRequest)).thenThrow(InvalidCredentialsException.class);

        assertThrows(InvalidCredentialsException.class, () -> {
            loginController.authenticate(authenticationRequest, null, null);
        });
    }

    @Test
    public void logout_returnsOkResponse() {
        ResponseEntity<String> response = loginController.logout(httpServletRequest, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged out", response.getBody());
    }

}
