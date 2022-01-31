package com.matheusjmoura.postapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserRole {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    @Getter
    private String description;

}
