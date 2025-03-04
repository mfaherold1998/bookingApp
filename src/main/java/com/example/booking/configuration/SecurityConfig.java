package com.example.booking.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilterConfig jwtAuthFilterConfiguration;
    private final AuthEntryPointConfig authEntryPointConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        HttpSecurity httpSecurity = http.csrf(AbstractHttpConfigurer::disable) //Not needed since we use stateless JWT token authentication
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authEntryPointConfiguration))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                new RegexRequestMatcher("/v3/api-docs", null),
                                new RegexRequestMatcher("/v3/api-docs/.*", null),
                                new RegexRequestMatcher("/swagger-ui/.*", null)
                        ).permitAll()
                )
                .authorizeHttpRequests(request -> request.anyRequest().permitAll()) //Every other request is controlled from preauthorize by code
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilterConfiguration, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
