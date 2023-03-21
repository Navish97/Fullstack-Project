package ntnu.idatt2105.project.backend.service;

import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.controller.ItemController;
import ntnu.idatt2105.project.backend.model.AuthenticationRequest;
import ntnu.idatt2105.project.backend.dto.AuthenticationResponse;
import ntnu.idatt2105.project.backend.model.RegisterRequest;
import ntnu.idatt2105.project.backend.exceptions.UserAlreadyExistsException;
import ntnu.idatt2105.project.backend.enums.Role;
import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.repository.UserRepository;
import org.apache.http.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(ItemController.class);


    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException {
        var user = User.builder()
                .id(String.valueOf(java.util.UUID.randomUUID()))
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        if (repository.findByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException("Email is already in use");
        repository.save(user);
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws InvalidCredentialsException {
        logger.info("Authenticating user with email: " + request.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        logger.info("User authenticated");
        var user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        logger.info("User found");
        logger.info("Generating token");
        var jwToken = jwtService.generateToken(user);
        logger.info("Token generated");
        return AuthenticationResponse.builder().token(jwToken).build();
    }


}
