package ntnu.idatt2105.project.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Profile("dev")
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final Environment env;
    Logger logger = Logger.getLogger(SecurityConfig.class.getName());

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("dev")){
            logger.info("Dev profile is active");
            http
                    .headers().frameOptions().disable().and()
                    .cors().and()
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers("/h2-ui/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    //.requestMatchers("/my-profile").permitAll()
                    .requestMatchers(toH2Console()).permitAll()// Add this line if you want to access H2 console without authentication
                    .anyRequest()
                    .authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authenticationProvider(authenticationProvider)
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
            ;
            return http.build();
        }
        else{
            logger.info("Dev profile is not active");
            http
                    .headers().frameOptions().disable().and()
                    .cors().and()
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers("/h2-ui/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authenticationProvider(authenticationProvider)
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
            ;
            return http.build();
        }

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("https://mymarketplace-xt5ws57jza-lz.a.run.app");
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Add this line

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

}
