package com.example.booking.service;

import com.example.booking.dto.JwtAuthResponse;
import com.example.booking.dto.SignInRequest;
import com.example.booking.dto.SignUpRequest;
import com.example.booking.entity.*;
import com.example.booking.exception.AuthException;
import com.example.booking.exception.CustomException;
import com.example.booking.exception.NotFoundException;
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

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {


    private final EmailService emailService;

    private final UserService userService;

    private final RoleService roleService;

    private final ClientService clientService;

    private final ProprietorService proprietorService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final VerificationCodeRepository verificationCodeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Transactional
    public Boolean register(SignUpRequest request) {

        ///Controla que email es unico
        if(isEmailAlreadyRegistered(request.getEmail())){
            throw new CustomException("An account with this email already exists", HttpStatus.BAD_REQUEST);
        }

        ///Determina el role para el registro
        Set<String> roles = new HashSet<>();

        switch (request.getRoleName()) {
            case "CLIENT" -> roles.add(Enums.RoleNames.CLIENT.getValue());
            case "PROPRIETOR" -> roles.addAll(Arrays.asList(
                    Enums.RoleNames.CLIENT.getValue(),
                    Enums.RoleNames.PROPRIETOR.getValue()
            ));
            default -> throw new IllegalArgumentException("Non valid Role");
        };

        ///Crear nuevo usuario con email
        UserEntity newUser = new UserEntity();
        newUser.setEmail(request.getEmail());

        ///Copiar nombres (que vienen del request)
        //newUser.setFirstName(request.getFirstName());
        //newUser.setLastName(request.getLastName());

        ///Añadir al set los roles correspondientes
        //newUser.getRoles().add(roleService.findByName(roles));
        roles.forEach(role -> newUser.getRoles().add(roleService.findByName(role)));

        ///Email y contraseña
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setConfirmedEmail(false);

        ///Crear cliente o proprietor asociado
        if(roles.contains(Enums.RoleNames.CLIENT.getValue())){
            Client c = new Client();
            c.setFirstName(request.getFirstName());
            c.setLastName(request.getLastName());
            c = clientService.save(c);
            newUser.setClient(c);
        }
        if(roles.contains(Enums.RoleNames.PROPRIETOR.getValue())){
            Proprietor p = new Proprietor();
            p.setFirstName(request.getFirstName());
            p.setLastName(request.getLastName());
            p = proprietorService.save(p);
            newUser.setProprietor(p);
        }

        userService.save(newUser);

        ///Mandar email de verificacion
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
                NotFoundException::new
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
