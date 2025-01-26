package com.example.booking.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
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

import static com.example.booking.utils.Enums.RoleNames.*;

@Configuration
@EnableWebSecurity
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
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                new RegexRequestMatcher("/auth/register", HttpMethod.POST.name()),
                                new RegexRequestMatcher("/auth/login", HttpMethod.POST.name()),
                                new RegexRequestMatcher("/auth/refresh/.*", HttpMethod.GET.name())
                        ).permitAll()
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(new RegexRequestMatcher("/api/invitations/send", HttpMethod.POST.name())).hasAuthority(PROPRIETOR.getValue())
                        .requestMatchers(new RegexRequestMatcher("/api/invitations/accept/.*", HttpMethod.GET.name())).hasAuthority(CLIENT.getValue())
                        .requestMatchers(new RegexRequestMatcher("/api/invitations/reject/.*", HttpMethod.GET.name())).hasAuthority(CLIENT.getValue())
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(new RegexRequestMatcher("/role/get/.*", HttpMethod.GET.name())).hasAuthority(SUPERADMIN.getValue())
                        .requestMatchers(new RegexRequestMatcher("/role/save", HttpMethod.POST.name())).hasAuthority(SUPERADMIN.getValue())
                        .requestMatchers(new RegexRequestMatcher("/role/update/.*", HttpMethod.PUT.name())).hasAuthority(SUPERADMIN.getValue())
                        .requestMatchers(new RegexRequestMatcher("/role/delete/.*", HttpMethod.DELETE.name())).hasAuthority(SUPERADMIN.getValue())
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(new RegexRequestMatcher("/user/get/.*", HttpMethod.GET.name())).hasAnyAuthority(PROPRIETOR.getValue(), CLIENT.getValue())
                        .requestMatchers(new RegexRequestMatcher("/user/save", HttpMethod.POST.name())).hasAnyAuthority(PROPRIETOR.getValue(), CLIENT.getValue())
                        .requestMatchers(new RegexRequestMatcher("/user/update/.*", HttpMethod.PUT.name())).hasAnyAuthority(PROPRIETOR.getValue(), CLIENT.getValue())
                        .requestMatchers(new RegexRequestMatcher("/user/delete/.*", HttpMethod.DELETE.name())).hasAnyAuthority(PROPRIETOR.getValue(), CLIENT.getValue())
                )
                .authorizeHttpRequests(request -> request.anyRequest().denyAll()) //Every other request is denied
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
