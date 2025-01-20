package com.example.booking.service;

import com.example.booking.dto.JwtAuthResponse;
import com.example.booking.dto.SignInRequest;
import com.example.booking.dto.SignUpRequest;
import com.example.booking.entity.*;
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
        String role = switch (request.getRoleName()) {
            case "user" -> Enums.RoleNames.CLIENT.getValue();
            case "owner" -> Enums.RoleNames.PROPRIETOR.getValue();
            default -> throw new IllegalArgumentException("Non valid Role");
        };

        ///Crear nuevo usuario con email unico
        UserEntity newUser = new UserEntity();
        newUser.setEmail(request.getEmail());

        ///Copiar role que viene del fronted en base a que boton se clicò
        newUser.setRole(roleService.findByName(role));

        ///Email y contraseña codificada
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setConfirmedEmail(false);

        ///Crear cliente o proprietor asociado
        switch (role) {
            case "CLIENT" -> {
                Client c = new Client();
                c.setFirstName(request.getFirstName());
                c.setLastName(request.getLastName());
                ///Salvar la entidad antes de salvar el nuevo cliente porque sino no lo encuentra en la base de datos para hacer la relacion
                c = clientService.save(c);
                newUser.setClient(c);
            }
            case "PROPRIETOR" -> {
                Proprietor p = new Proprietor();
                p.setFirstName(request.getFirstName());
                p.setLastName(request.getLastName());
                p = proprietorService.save(p);
                newUser.setProprietor(p);
            }
            default -> throw new IllegalArgumentException("Non valid Role");
        };

        userService.save(newUser);

        ///Mandar email de verificacion
        sendVerificationEmail(request.getEmail());

        return Boolean.TRUE;
    }

    public JwtAuthResponse login(SignInRequest request) {

        ///Crear token de autenticacion con ususario y contraseña
        var authRequest = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        ///Controlar si las credenciales con correctas
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

        ///General JWT token de usuario autenticado
        String email = request.getEmail();
        HashMap<String, Object> extraClaims = new HashMap<>();
        String token = jwtUtils.generateToken(extraClaims, email);

        ///Se crea un nuveo Refresh Token
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

        ///Encontrar si existe el refresh token dado
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByToken(token);
        ///Lanza una excepcion de que no existe si no lo encuentra
        RefreshToken refreshToken = optionalRefreshToken.orElseThrow(
                () -> AuthException.builder().message(Constants.Errors.INVALID_REFRESH_TOKEN).build()
        );
        ///Lanaza una excepcion si ya caduco
        if(refreshToken.getExpirationDate().before(new Date())){
            throw AuthException.builder().message(Constants.Errors.INVALID_REFRESH_TOKEN).build();
        }

        ///Generar un token de acceso nuevo
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

        ///Si el codigo esta presente y no ha expirado
        if(optionalVerificationCode.isPresent()){
            VerificationCode verificationCode = optionalVerificationCode.get();
            if(verificationCode.getExpirationDate().after(new Date(System.currentTimeMillis()))){
                UserEntity user = verificationCode.getUser();
                user.setConfirmedEmail(Boolean.TRUE);
                ///Siempre que se hace un cambio local en una entidad se debe salvar en la BD
                userService.update(user);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean sendVerificationEmail(String email) {

        ///Encontrar el usuario por su email
        Optional<UserEntity> optionalUser = userService.getRepository().findByEmail(email);

        if(optionalUser.isPresent()){
            ///Delete old token
            verificationCodeRepository.deleteByEmail(email);
            ///Save new token
            VerificationCode verificationCode = VerificationCode.generateCode(optionalUser.get());
            verificationCodeRepository.save(verificationCode);
            ///Send Email
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
