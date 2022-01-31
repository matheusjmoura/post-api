package com.matheusjmoura.postapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.matheusjmoura.postapi.commons.application.annotation.ApiAuthorization;
import com.matheusjmoura.postapi.controller.request.authentication.AuthenticationRequest;
import com.matheusjmoura.postapi.controller.request.authentication.AuthenticationRequest.AdvancedInfo;
import com.matheusjmoura.postapi.controller.request.authentication.AuthenticationRequest.BasicInfo;
import com.matheusjmoura.postapi.controller.response.authentication.AuthenticationResponse;
import com.matheusjmoura.postapi.enums.UserRole;
import com.matheusjmoura.postapi.service.AuthenticationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Api(value = "auth-operations", tags = "Authentication Operations")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    @ApiOperation(value = "Authenticate user", response = AuthenticationResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = AuthenticationResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = AuthenticationResponse.class),
    })
    public ResponseEntity<AuthenticationResponse> login(
        @JsonView(BasicInfo.class) @RequestBody @Validated(BasicInfo.class)
        @ApiParam(value = "AuthenticationRequest", required = true) AuthenticationRequest authenticationRequest
    ) {
        log.info("[AuthenticationController.login] Receiving request to login a user");

        try {
            AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
            return Boolean.TRUE.equals(authenticationResponse.getError())
                ? ResponseEntity.status(401).body(authenticationResponse)
                : ResponseEntity.ok(authenticationResponse);
        } catch (DisabledException e) {
            return ResponseEntity.status(500).body(AuthenticationResponse.buildLoginError("User is disabled"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(AuthenticationResponse.buildLoginError("Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(AuthenticationResponse.buildLoginError("Something went wrong"));
        }
    }

    @ApiAuthorization
    @PostMapping("register")
    @ApiOperation(value = "Register a new user", response = AuthenticationResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = AuthenticationResponse.class),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponse> register(
        @JsonView(AdvancedInfo.class) @RequestBody @Validated(AdvancedInfo.class)
        @ApiParam(value = "AuthenticationRequest", required = true) AuthenticationRequest authenticationRequest
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(UserRole.ROLE_ADMIN.name()))) {
            log.info("[AuthenticationController.register] Receiving request to register a new user");

            AuthenticationResponse authenticationResponse = authenticationService.register(authenticationRequest);
            return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
        } else return ResponseEntity.status(401).body(AuthenticationResponse.buildLoginError("Access denied"));
    }
}
