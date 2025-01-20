package com.example.booking.configuration;

import com.example.booking.exception.CustomExceptionBody;
import com.example.booking.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilterConfig extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        ///Metodo que se ejecuta siempre que se recibe una solicitud
        try {
            ///Ver si el token de la request es valido
            String jwtToken = jwtUtils.getTokenFromRequest(request);
            jwtUtils.isTokenValid(jwtToken);

            ///Cargar el objeto usuario a partir del subject de la solicitud
            final String userSubject = jwtUtils.extractSubject(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userSubject);

            ///Autenticacion del usuario en el contexto de seguridad
            UsernamePasswordAuthenticationToken authToken = getAuthToken(userDetails);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            ///Pasar al siguiente filtro en la cadena si existe
            filterChain.doFilter(request, response);
        }
        catch (Exception ex) {
            var error = CustomExceptionBody.builder()
                    .message(ex.getMessage())
                    .build();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(error));
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        ///URLs no afectadas por los filtros
        List<RequestMatcher> requestMatchers = List.of(
                new RegexRequestMatcher("/v3/api-docs", null),
                new RegexRequestMatcher("/v3/api-docs/.*", null),
                new RegexRequestMatcher("/swagger-ui/.*", null),
                new RegexRequestMatcher("/auth/.*", HttpMethod.POST.name())
        );
        for (var requestMatcher : requestMatchers)
            if (requestMatcher.matches(request))
                return true;
        return false;
    }

    private UsernamePasswordAuthenticationToken getAuthToken(UserDetails userDetails) {
        ///Token de autenticacion para el usuario en el sistema
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
