package com.example.booking.controller;

import com.example.booking.configuration.AuthEntryPointConfig;
import com.example.booking.configuration.SecurityConfig;
import com.example.booking.dto.JwtAuthResponse;
import com.example.booking.dto.SignInRequest;
import com.example.booking.dto.SignUpRequest;
import com.example.booking.entity.VerificationCode;
import com.example.booking.exception.AuthException;
import com.example.booking.exception.CustomException;
import com.example.booking.repository.VerificationCodeRepository;
import com.example.booking.service.AuthService;
import com.example.booking.utils.Enums;
import com.example.booking.utils.JwtUtils;
import com.example.booking.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@Disabled
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private AuthController authController;

    @MockBean
    private AuthService authService;

    @MockBean
    private VerificationCodeRepository verificationCodeRepository;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private AuthEntryPointConfig authEntryPointConfig;


    @Test
    public void register_withNewUser_returnTrue() throws Exception {

        SignUpRequest request = SignUpRequest.builder()
                .firstName("maria")
                .lastName("artigas")
                .email("mfaherold1998@gmail.com")
                .password("1234")
                .roleName(Enums.RoleNames.CLIENT.getValue())
                .build();

        when(authService.register(any())).thenReturn(Boolean.TRUE);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        var result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));


    }

    @Test
    public void register_withRegisteredEmail_throwsException() throws Exception {

        when(authService.register(any())).thenThrow(new CustomException(" ", HttpStatus.BAD_REQUEST));

        var result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));

        result
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_withValidUser_returnJwtAuthResponse() throws Exception {

        SignInRequest request = SignInRequest.builder()
                .email("mfaherold1998@gmail.com")
                .password("1234")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        when(authService.login(any())).thenReturn(JwtAuthResponse.builder().build());

        var result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void login_withNonValidUser_returnAuthException() throws Exception {

        when(authService.login(any())).thenThrow(new AuthException(" "));

        var result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" "));

        result
                .andExpect(status().isBadRequest());

    }

    @Test
    void refresh_withValidToken_returnJwtAuthResponse() throws Exception {

        String token = "token";
        when(authService.refresh(anyString())).thenReturn(JwtAuthResponse.builder().build());

        var result = mockMvc.perform(get("/auth/refresh/"+token));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void refresh_withNonValidToken_returnAuthException() throws Exception {

        String token = "token";
        when(authService.refresh(anyString())).thenThrow(new AuthException(" "));

        var result = mockMvc.perform(get("/auth/refresh/" +token));

        result
                .andExpect(status().is4xxClientError());
    }

    @Test
    void activateEmail_withValidUserValidCode_returnTrue() throws Exception {

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date futureDate = calendar.getTime();

        // /auth/activate_email/{code} actually required an authenticated user
        var mockedUser = TestUtils.mockUserAuthenticationWithRole(Enums.RoleNames.CLIENT, jwtUtils, userDetailsService);

        var code = "code";
        VerificationCode verCode = VerificationCode.builder()
                .code(code)
                .email(mockedUser.getEmail())
                .expirationDate(futureDate)
                .user(mockedUser)
                .build();

        when(verificationCodeRepository.findByCode(anyString())).thenReturn(Optional.of(verCode));

        when(authService.activateEmail(anyString())).thenReturn(Boolean.TRUE);


        var result = mockMvc.perform(get("/auth/activate_email/" + code));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

    }

    @Test
    void activateEmail_withNonValidUserValidCode_returnFalse() throws Exception {

        // /auth/activate_email/{code} actually required an authenticated user
        var mockedUser = TestUtils.mockUserAuthenticationWithRole(Enums.RoleNames.CLIENT, jwtUtils, userDetailsService);

        String code = "non-valid-code";

        when(verificationCodeRepository.findByCode(anyString())).thenReturn(Optional.empty());

        var result = mockMvc.perform(get("/auth/activate_email/" + code));

        result
                .andExpect(status().is4xxClientError());
    }

}
