package ntnu.idatt2105.project.backend.config;


import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.enums.AuthenticationState;
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

    private final Logger logger = LoggerFactory.getLogger(JwTAuthenticationFilter.class);

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
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String jwt = extractTokenFromCookie(request);

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

        logger.info("Username: " + username);
        logger.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication()));
        if (username != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            logger.info("User " + username + " is loaded");

            if (jwtService.isTokenValid(jwt, userDetails)) {
                authState = AuthenticationState.AUTHENTICATED;
                logger.info("User " + username + " is authenticated");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        logger.info("Authentication state: " + authState);

        // Set AuthenticationState as a request attribute
        request.setAttribute("authState", authState);

        filterChain.doFilter(request, response);
    }

}
