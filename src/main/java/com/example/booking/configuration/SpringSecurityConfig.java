package com.example.booking.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import com.example.booking.utils.Enums.RoleNames;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final JwtAuthenticationEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable) //Not needed since we use stateless JWT token authentication
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authEntryPoint))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                new RegexRequestMatcher("/v3/api-docs", HttpMethod.GET.name()),
                                new RegexRequestMatcher("/v3/api-docs/.*", HttpMethod.GET.name()),
                                new RegexRequestMatcher("/swagger-ui/.*", HttpMethod.GET.name())
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
                        .requestMatchers(new RegexRequestMatcher("/role/get/.*", HttpMethod.GET.name())).hasAuthority(RoleNames.SUPERADMIN.getValue())
                        .requestMatchers(new RegexRequestMatcher("/role/save", HttpMethod.POST.name())).hasAuthority(RoleNames.SUPERADMIN.getValue())
                        .requestMatchers(new RegexRequestMatcher("/role/update/.*", HttpMethod.PUT.name())).hasAuthority(RoleNames.SUPERADMIN.getValue())
                        .requestMatchers(new RegexRequestMatcher("/role/delete/.*", HttpMethod.DELETE.name())).hasAuthority(RoleNames.SUPERADMIN.getValue())
                        .requestMatchers(new RegexRequestMatcher("/role/filter", HttpMethod.POST.name())).hasAuthority(RoleNames.SUPERADMIN.getValue())
                        .requestMatchers(new RegexRequestMatcher("/role/filter_basic", HttpMethod.POST.name())).hasAuthority(RoleNames.SUPERADMIN.getValue())
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(new RegexRequestMatcher("/user/get/.*", HttpMethod.GET.name())).hasAnyAuthority(RoleNames.ADMIN.getValue(), RoleNames.STANDARD.getValue())
                        .requestMatchers(new RegexRequestMatcher("/user/save", HttpMethod.POST.name())).hasAnyAuthority(RoleNames.ADMIN.getValue(), RoleNames.STANDARD.getValue())
                        .requestMatchers(new RegexRequestMatcher("/user/update/.*", HttpMethod.PUT.name())).hasAnyAuthority(RoleNames.ADMIN.getValue(), RoleNames.STANDARD.getValue())
                        .requestMatchers(new RegexRequestMatcher("/user/delete/.*", HttpMethod.DELETE.name())).hasAnyAuthority(RoleNames.ADMIN.getValue(), RoleNames.STANDARD.getValue())
                        .requestMatchers(new RegexRequestMatcher("/user/filter", HttpMethod.POST.name())).hasAnyAuthority(RoleNames.ADMIN.getValue(), RoleNames.STANDARD.getValue())
                        .requestMatchers(new RegexRequestMatcher("/user/filter_basic", HttpMethod.POST.name())).hasAnyAuthority(RoleNames.ADMIN.getValue(), RoleNames.STANDARD.getValue())
                )
                .authorizeHttpRequests(request -> request.anyRequest().denyAll()) //Every other request is denied
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));//Util para apis publicas pero inseguro para produccion
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));// authorization para enviar tokens de autenticacion
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);//Las reglas CORS se aplicaran a todas las turas del servidor
        return source;
    }
}
