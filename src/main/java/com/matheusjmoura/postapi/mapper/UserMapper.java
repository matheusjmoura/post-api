package com.matheusjmoura.postapi.mapper;

import com.matheusjmoura.postapi.controller.request.authentication.AuthenticationRequest;
import com.matheusjmoura.postapi.controller.request.user.UserUpdateRequest;
import com.matheusjmoura.postapi.entity.User;
import com.matheusjmoura.postapi.enums.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User buildUserUpdate(AuthenticationRequest authenticationRequest) {
        return Optional.ofNullable(authenticationRequest).map(request ->
            User.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .active(Boolean.TRUE)
                .role(UserRole.ROLE_USER)
                .build()
        ).orElse(null);
    }

    public static User buildUserUpdate(User user, UserUpdateRequest userUpdateRequest) {
        return Optional.ofNullable(userUpdateRequest).map(request ->
            User.builder()
                .id(user.getId())
                .name(Optional.ofNullable(request.getName()).orElse(user.getName()))
                .email(Optional.ofNullable(request.getEmail()).orElse(user.getEmail()))
                .username(Optional.ofNullable(request.getUserName()).orElse(user.getUsername()))
                .password(Optional.ofNullable(request.getPassword()).map(password ->
                    new BCryptPasswordEncoder().encode(password)).orElse(user.getPassword()))
                .active(user.getActive())
                .role(Optional.ofNullable(request.getRole()).orElse(user.getRole()))
                .build()
        ).orElse(null);
    }
}
