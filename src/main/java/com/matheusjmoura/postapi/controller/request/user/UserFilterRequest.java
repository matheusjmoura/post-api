package com.matheusjmoura.postapi.controller.request.user;

import com.matheusjmoura.postapi.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFilterRequest {

    @ApiModelProperty(value = "User name", example = "John Snow")
    private String name;

    @ApiModelProperty(value = "User email", example = "johnsnow@email.com")
    private String email;

    @ApiModelProperty(value = "Username", example = "johnsnow")
    private String userName;

    @ApiModelProperty(value = "User status", example = "true")
    private Boolean active;

    @ApiModelProperty(value = "User system role", example = "USER")
    private UserRole role;

}
