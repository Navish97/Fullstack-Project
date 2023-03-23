package ntnu.idatt2105.project.backend.config;


import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.enums.AuthenticationState;
import ntnu.idatt2105.project.backend.service.CookieService;
import ntnu.idatt2105.project.backend.service.JwtService;
import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final CookieService cookieService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String jwt = cookieService.extractTokenFromCookie(request);

        String username = null;
        AuthenticationState authState = AuthenticationState.UNAUTHENTICATED;

        if (jwt != null) {
            try {
                username = jwtService.extractUsername(jwt);
            } catch (ExpiredJwtException e) {
                authState = AuthenticationState.TOKEN_EXPIRED;
                username = null;
            } catch (Exception e) {
                username = null;
            }
        }

        if (username != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                authState = AuthenticationState.AUTHENTICATED;
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Set AuthenticationState as a request attribute
        request.setAttribute("authState", authState);

        filterChain.doFilter(request, response);
    }

}
