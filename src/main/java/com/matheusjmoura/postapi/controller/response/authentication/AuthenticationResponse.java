package com.matheusjmoura.postapi.controller.response.authentication;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationResponse {

    @ApiModelProperty(value = "Error status", example = "false")
    private Boolean error;

    @ApiModelProperty(value = "Username", example = "johnsnow")
    private String username;

    @ApiModelProperty(value = "Request description", example = "Account created successfully")
    private String message;

    @ApiModelProperty(value = "JWT token", example = "xxxxx.yyyyy.zzzzz")
    private String token;

    public static AuthenticationResponse buildRegisterSuccess(String userName, String token) {
        return new AuthenticationResponse(false, userName, "Account created successfully", token);
    }

    public static AuthenticationResponse buildLoginSuccess(String token, String username) {
        return new AuthenticationResponse(false, username, "Logged In", token);
    }

    public static AuthenticationResponse buildLoginError(String message) {
        return new AuthenticationResponse(true, null, message, null);
    }
}
