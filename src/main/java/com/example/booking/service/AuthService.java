package com.example.booking.service;

import com.example.booking.dto.JwtAuthResponse;
import com.example.booking.dto.SignInRequest;
import com.example.booking.dto.SignUpRequest;
import com.example.booking.entity.RefreshToken;
import com.example.booking.entity.RoleEntity;
import com.example.booking.entity.UserEntity;
import com.example.booking.entity.VerificationCode;
import com.example.booking.exception.AuthException;
import com.example.booking.exception.CustomException;
import com.example.booking.repository.RefreshTokenRepository;
import com.example.booking.repository.VerificationCodeRepository;
import com.example.booking.utils.Constants;
import com.example.booking.utils.Enums;
import com.example.booking.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final EmailService emailService;

    private final UserService userService;

    private final RoleService roleService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final VerificationCodeRepository verificationCodeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Transactional
    public Boolean register(SignUpRequest request) {

        if(isEmailAlreadyRegistered(request.getEmail())){
            throw new CustomException("An account with this email already exists", HttpStatus.BAD_REQUEST);
        }
        //TODO Un if para el caso owner/corporation y otro para el caso client
        UserEntity newUser = new UserEntity();
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());

        newUser.setRole(roleService.findByName(Enums.RoleNames.CLIENT.getValue()));

        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setConfirmedEmail(false);
        userService.save(newUser);

        sendVerificationEmail(request.getEmail());

        return Boolean.TRUE;
    }

    public JwtAuthResponse login(SignInRequest request) {
        var authRequest = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        try {
            authenticationManager.authenticate(authRequest);
        } catch (BadCredentialsException ex) {
            throw new AuthException(Constants.Errors.INVALID_CREDENTIALS);
        } catch (LockedException ex) {
            throw new AuthException(Constants.Errors.ACCOUNT_LOCKED);
        } catch (DisabledException ex) {
            throw new AuthException(Constants.Errors.ACCOUNT_DISABLED);
        } catch (AuthenticationException ex) {
            throw new AuthException(Constants.Errors.INVALID_CREDENTIALS);
        }

        String email = request.getEmail();
        HashMap<String, Object> extraClaims = new HashMap<>();
        String token = jwtUtils.generateToken(extraClaims, email);

        //The old refresh token is deleted then a new one is created
        refreshTokenRepository.deleteByEmail(email);
        RefreshToken refreshToken = jwtUtils.createRefreshToken(email);

        refreshTokenRepository.save(refreshToken);
        return JwtAuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken.getToken())
                .expiresAt(jwtUtils.extractExpiration(token).getTime())
                .build();
    }

    public JwtAuthResponse refresh(String token) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByToken(token);
        RefreshToken refreshToken = optionalRefreshToken.orElseThrow(
                () -> AuthException.builder().message(Constants.Errors.INVALID_REFRESH_TOKEN).build()
        );
        if(refreshToken.getExpirationDate().before(new Date())){
            throw AuthException.builder().message(Constants.Errors.INVALID_REFRESH_TOKEN).build();
        }

        HashMap<String, Object> extraClaims = new HashMap<>();
        String accessToken = jwtUtils.generateToken(extraClaims, refreshToken.getUser().getEmail());
        return JwtAuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiresAt(jwtUtils.extractExpiration(accessToken).getTime())
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
                UserEntity user = verificationCode.getUser();
                user.setConfirmedEmail(Boolean.TRUE);
                userService.update(user);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean sendVerificationEmail(String email) {

        Optional<UserEntity> optionalUser = userService.getRepository().findByEmail(email);

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
