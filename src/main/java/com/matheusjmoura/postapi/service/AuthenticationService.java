package com.matheusjmoura.postapi.service;

import com.matheusjmoura.postapi.commons.util.JwtTokenUtil;
import com.matheusjmoura.postapi.controller.request.authentication.AuthenticationRequest;
import com.matheusjmoura.postapi.controller.response.authentication.AuthenticationResponse;
import com.matheusjmoura.postapi.entity.User;
import com.matheusjmoura.postapi.exception.UserEmailAlreadyExistException;
import com.matheusjmoura.postapi.exception.UserUsernameAlreadyExistException;
import com.matheusjmoura.postapi.mapper.UserMapper;
import com.matheusjmoura.postapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        log.info("[AuthenticationService.login] Starting user login process - Username: {}", authenticationRequest.getUsername());

        Authentication auth = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));
        if (auth.isAuthenticated()) {
            log.info("[AuthenticationService.login] User logged in successfully - Username: {}", authenticationRequest.getUsername());
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);
            return AuthenticationResponse.buildLoginSuccess(token, authenticationRequest.getUsername());
        } else {
            log.error("[AuthenticationService.login] User login error (invalid credentials) - Username: {}",
                authenticationRequest.getUsername());
            return AuthenticationResponse.buildLoginError("Invalid Credentials");
        }
    }

    public AuthenticationResponse register(AuthenticationRequest authenticationRequest) {
        log.info("[AuthenticationService.register] Starting new user registration process - Request: {}", authenticationRequest);

        log.info("[AuthenticationService.register] Validating user email and username - Email: {}, Username: {}",
            authenticationRequest.getEmail(), authenticationRequest.getUsername());
        if (Boolean.TRUE.equals(userRepository.existsByEmail(authenticationRequest.getEmail()))) {
            log.error("[AuthenticationService.register] Already has a registered user with email: {}.", authenticationRequest.getEmail());
            throw new UserEmailAlreadyExistException(authenticationRequest.getEmail());
        } else if (Boolean.TRUE.equals(userRepository.existsByUsername(authenticationRequest.getUsername()))) {
            log.error("[AuthenticationService.register] Already has a registered user with email: {}.", authenticationRequest.getEmail());
            throw new UserUsernameAlreadyExistException(authenticationRequest.getUsername());
        }

        log.info("[AuthenticationService.register] Registering new user");
        User user = UserMapper.buildUserUpdate(authenticationRequest);
        userRepository.save(user);

        log.info("[AuthenticationService.register] New user registered successfully, generating JWT token - User: {}", user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        log.info("[AuthenticationService.register] JWT token generated successfully - Username: {}", user.getUsername());
        return AuthenticationResponse.buildRegisterSuccess(user.getUsername(), token);
    }
}
