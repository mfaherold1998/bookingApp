package com.example.booking.service;

import com.example.booking.dto.JwtAuthResponseDto;
import com.example.booking.dto.SignInRequestDto;
import com.example.booking.dto.SignUpRequestDto;
import com.example.booking.entity.RefreshToken;
import com.example.booking.entity.User;
import com.example.booking.entity.VerificationCode;
import com.example.booking.exception.AuthException;
import com.example.booking.exception.ConstraintException;
import com.example.booking.mapper.UserMapper;
import com.example.booking.repository.RefreshTokenRepository;
import com.example.booking.repository.VerificationCodeRepository;
import com.example.booking.utils.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.booking.utils.Enums;

import java.util.*;


@Service
@AllArgsConstructor
public class AuthService {

    private final UserMapper userMapper;

    private final UserService userService;

    private final RoleService roleService;

    private final EmailService emailService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final VerificationCodeRepository verificationCodeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtProvider;

    @Transactional
    public Boolean register(SignUpRequestDto request) {

        if(isEmailAlreadyRegistered(request.getEmail())){
            throw new ConstraintException("Constraint Exception","An account with this email already exists");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setRoles(new HashSet<>(Collections.singleton(roleService.findByName(Set.of(Enums.RoleNames.STANDARD.toString())))));
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userService.saveEntity(newUser);

        sendVerificationEmail(request.getEmail());

        return Boolean.TRUE;
    }

    public JwtAuthResponseDto login(SignInRequestDto request) {
        var authRequest = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        try {
            authenticationManager.authenticate(authRequest);
        } catch (BadCredentialsException ex) {
            throw new AuthException("Authentication Exception","INVALID_CREDENTIALS");
        } catch (LockedException ex) {
            throw new AuthException("Authentication Exception","ACCOUNT_LOCKED");
        } catch (DisabledException ex) {
            throw new AuthException("Authentication Exception","ACCOUNT_DISABLED");
        } catch (AuthenticationException ex) {
            throw new AuthException("Authentication Exception","INVALID_CREDENTIALS");
        }

        String email = request.getEmail();
        HashMap<String, Object> extraClaims = new HashMap<>();
        String token = jwtProvider.generateToken(extraClaims, email);

        //The old refresh token is deleted then a new one is created
        refreshTokenRepository.deleteByEmail(email);
        RefreshToken refreshToken = jwtProvider.createRefreshToken(email);

        refreshTokenRepository.save(refreshToken);
        return JwtAuthResponseDto.builder()
                .token(token)
                .refreshToken(refreshToken.getToken())
                .expiresAt(jwtProvider.extractExpiration(token).getTime())
                .build();
    }

    public JwtAuthResponseDto refresh(String token) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByToken(token);
        RefreshToken refreshToken = optionalRefreshToken.orElseThrow(
                () -> AuthException.builder().message("INVALID_REFRESH_TOKEN").build()
        );
        if(refreshToken.getExpirationDate().before(new Date())){
            throw AuthException.builder().message("INVALID_REFRESH_TOKEN").build();
        }

        HashMap<String, Object> extraClaims = new HashMap<>();
        String accessToken = jwtProvider.generateToken(extraClaims, refreshToken.getUser().getEmail());
        return JwtAuthResponseDto.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiresAt(jwtProvider.extractExpiration(accessToken).getTime())
                .build();
    }
    private Boolean isEmailAlreadyRegistered(String email) {
        return userService.getRepository().findByEmail(email).isPresent();
    }

    public Boolean activateEmail(String code) {
        Optional<VerificationCode> optionalVerificationCode = verificationCodeRepository.findByCode(code);
        if(optionalVerificationCode.isPresent()){
            VerificationCode verificationCode = optionalVerificationCode.get();
            if(verificationCode.getExpirationDate().after(new Date(System.currentTimeMillis()))){
                User user = verificationCode.getUser();
                user.setConfirmedEmail(Boolean.TRUE);
                userService.update(userMapper.toDto(user));
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean sendVerificationEmail(String email) {

        Optional<User> optionalUser = userService.getRepository().findByEmail(email);

        if(optionalUser.isPresent()){
            //Delete old token
            verificationCodeRepository.deleteByEmail(email);
            //Save new token
            VerificationCode verificationCode = VerificationCode.generateCode(optionalUser.get());
            verificationCodeRepository.save(verificationCode);
            //Send Email
            emailService.sendNewEmail(
                    EmailService.EmailContainer.builder()
                            .to(email)
                            .subject("ACCOUNT ACTIVATION")
                            .text("Your verification code: " + verificationCode.getCode())
                            .build()
            );
        }

        return Boolean.TRUE;
    }
}
