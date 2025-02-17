package com.example.booking.service;

import com.example.booking.dto.SignUpRequest;
import com.example.booking.entity.Client;
import com.example.booking.entity.RoleEntity;
import com.example.booking.entity.UserEntity;
import com.example.booking.entity.VerificationCode;
import com.example.booking.repository.RefreshTokenRepository;
import com.example.booking.repository.RoleRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.repository.VerificationCodeRepository;
import com.example.booking.utils.Enums;
import com.example.booking.utils.JwtUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//@Disabled
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

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
    private ProprietorService proprietorService;

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
    void register_asNewClient_returnTrue(){

        SignUpRequest request = SignUpRequest.builder()
                .firstName("maria")
                .lastName("artigas")
                .email("mfaherold1998@gmail.com")
                .password("1234")
                .roleName("CLIENT")
                .build();

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("");

        when (roleService.findByName(request.getRoleName())).thenReturn(RoleEntity.builder().name(Enums.RoleNames.CLIENT.getValue()).build());

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        when(userService.getRepository()).thenReturn(userRepository);

        when(userService.save(any(UserEntity.class))).thenReturn(new UserEntity());

        when(clientService.save(any(Client.class))).thenReturn(new Client());

        var result = authService.register(request);

        Assertions.assertTrue(result);
    }
}
