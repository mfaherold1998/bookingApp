package com.example.booking.configuration;

import com.example.booking.exception.AuthException;
import com.example.booking.utils.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JwtTokenProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = jwtProvider.getTokenFromRequest(request);
            jwtProvider.isTokenValid(jwtToken);
            final String userSubject = jwtProvider.extractSubject(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userSubject);
            UsernamePasswordAuthenticationToken authToken = getAuthToken(userDetails);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        }
        catch (AuthException ex) {
            var error = new AuthException("Authentication Exception", ex.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(error));
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        //I do not filter on /auth/.*
        List<RequestMatcher> requestMatchers = List.of(
                new RegexRequestMatcher("/auth/register", HttpMethod.POST.name()),
                new RegexRequestMatcher("/auth/login", HttpMethod.POST.name()),
                new RegexRequestMatcher("/auth/refresh/.*", HttpMethod.GET.name())
        );
        for (var requestMatcher : requestMatchers)
            if (requestMatcher.matches(request))
                return true;
        return false;
    }

    private UsernamePasswordAuthenticationToken getAuthToken(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

