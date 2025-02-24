package com.example.booking.service;

import com.example.booking.dto.SignInRequest;
import com.example.booking.dto.SignUpRequest;
import com.example.booking.entity.*;
import com.example.booking.exception.AuthException;
import com.example.booking.exception.CustomException;
import com.example.booking.repository.RefreshTokenRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.repository.VerificationCodeRepository;
import com.example.booking.utils.Enums;
import com.example.booking.utils.JwtUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Spy
    @InjectMocks
    private AuthService authService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private ClientService clientService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Test
    void register_asNewUserValidRole_returnTrue(){

        SignUpRequest request = SignUpRequest.builder()
                .firstName("maria")
                .lastName("artigas")
                .email("mfaherold1998@gmail.com")
                .password("1234")
                .roleName(Enums.RoleNames.CLIENT.getValue())
                .build();

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("");

        when(roleService.findByName(request.getRoleName())).thenReturn(RoleEntity.builder().name(Enums.RoleNames.CLIENT.getValue()).build());

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        when(userService.getRepository()).thenReturn(userRepository);

        when(userService.save(any(UserEntity.class))).thenReturn(new UserEntity());

        when(clientService.save(any(Client.class))).thenReturn(new Client());

        Assertions.assertTrue(authService.register(request));
        verify(authService).sendVerificationEmail(request.getEmail());

    }

    @Test
    void register_asNewUserNonValidRole_throwException(){

        SignUpRequest request = SignUpRequest.builder()
                .firstName("maria")
                .lastName("artigas")
                .email("mfaherold1998@gmail.com")
                .password("1234")
                .roleName(Enums.RoleNames.EMPLOYEE.getValue())
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        when(userService.getRepository()).thenReturn(userRepository);

        Assertions.assertThrows(IllegalArgumentException.class, () -> authService.register(request));

    }

    @Test
    void register_withExistingEmail_throwException(){

        SignUpRequest request = SignUpRequest.builder()
                .firstName("maria")
                .lastName("artigas")
                .email("mfaherold1998@gmail.com")
                .password("1234")
                .roleName(Enums.RoleNames.CLIENT.getValue())
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new UserEntity()));

        when(userService.getRepository()).thenReturn(userRepository);

        CustomException exception = Assertions.assertThrows(CustomException.class, () -> {authService.register(request);});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());

    }

    @Test
    void login_withValidUser_returnJwtAuthResponse(){

        SignInRequest request = SignInRequest.builder()
                .email("mfaherold1998@gmail.com")
                .password("1234")
                .build();

        var authenticationMock = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        var token = "token";

        when(authenticationManager.authenticate(any())).thenReturn(authenticationMock);

        when(jwtUtils.generateToken(any(),anyString())).thenReturn(token);

        doNothing().when(refreshTokenRepository).deleteByEmail(anyString());

        when(jwtUtils.createRefreshToken(anyString())).thenReturn(new RefreshToken());

        when(jwtUtils.extractExpiration(any())).thenReturn(new Date());

        var result = authService.login(request);

        assertThat(result).isNotNull();
        assertThat(result.getToken())
                .isNotNull()
                .isNotEmpty();

        /*Verificaciones:
        Assertions.assertNotNull(result.getRefreshToken());
        Assertions.assertFalse(result.getRefreshToken().isEmpty());
        Assertions.assertNotNull(result.getExpiresAt());
        Assertions.assertTrue(result.getExpiresAt() > System.currentTimeMillis());
        Assertions.assertEquals("expectedTokenValue", result.getToken());
        Assertions.assertEquals("expectedRefreshTokenValue", result.getRefreshToken());
        Assertions.assertEquals(expectedExpirationTime, result.getExpiresAt());
         */

    }

    @Test
    void login_withNonValidUser_returnAuthException(){

        SignInRequest request = SignInRequest.builder()
                .email("mfaherold1998@gmail.com")
                .password("1234")
                .build();

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThatThrownBy(() -> {
            authService.login(request);
        })
                .isInstanceOf(AuthException.class);


    }

    @Test
    void refresh_withValidToken_returnJwtAuthResponse(){

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date futureDate = calendar.getTime();

        String token = "token";

        RefreshToken refToken = RefreshToken.builder().token(token).expirationDate(futureDate).user(UserEntity.builder().email("mfaherold1998@gmail.com").build()).build();

        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(refToken));

        when(jwtUtils.generateToken(any(), anyString())).thenReturn(token);

        when(jwtUtils.extractExpiration(anyString())).thenReturn(new Date());

        var result = authService.refresh(token);

        assertThat(result).isNotNull();
        assertThat(result.getToken())
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void refresh_withNonValidToken_returnAuthException(){

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Date futureDate = calendar.getTime();

        String token = "token";

        RefreshToken refToken = RefreshToken.builder().token(token).expirationDate(futureDate).user(UserEntity.builder().email("mfaherold1998@gmail.com").build()).build();

        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(refToken));

        assertThatThrownBy(() -> {
            authService.refresh(token);
        })
                .isInstanceOf(AuthException.class);
    }

    @Test
    void activateEmail_withValidCode_returnTrue(){

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date futureDate = calendar.getTime();

        String code = "code";

        VerificationCode verCode = VerificationCode.builder()
                .code(code)
                .email("maria@gmail.com")
                .expirationDate(futureDate)
                .user(new UserEntity())
                .build();

        when(verificationCodeRepository.findByCode(code)).thenReturn(Optional.of(verCode));

        var result = authService.activateEmail(code);
        Assertions.assertTrue(result);
    }

    @Test
    void activateEmail_withNonValidCode_returnFalse(){

        when(verificationCodeRepository.findByCode("code")).thenReturn(Optional.empty());

        var result = authService.activateEmail("code");
        Assertions.assertFalse(result);
    }

    @Test
    void sendVerificationEmail_withValidEmail_returnTrueAndVerifySendNEmail(){

        String email = "mfaherold1998@gmail.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(UserEntity.builder().email(email).build()));

        when(userService.getRepository()).thenReturn(userRepository);

        var result = authService.sendVerificationEmail(email);

        verify(emailService).sendNewEmail(argThat(argument ->
                argument.getTo().equals(email) &&
                        argument.getSubject().equals("ACCOUNT ACTIVATION") &&
                        argument.getText().contains("Your verification code")
        ));

        Assertions.assertTrue(result);





    }
}
