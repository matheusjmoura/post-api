package com.matheusjmoura.postapi.controller.request.user;

import com.matheusjmoura.postapi.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min = 4, max = 15, message = "{user.name.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$", message = "{user.name.invalid}")
    @ApiModelProperty(value = "User name", example = "John Snow")
    private String name;

    @Email(message = "{user.email.badFormat}")
    @ApiModelProperty(value = "User email", example = "johnsnow@email.com")
    private String email;

    @Size(min = 4, max = 15, message = "{user.username.size}")
    @ApiModelProperty(value = "Username", example = "johnsnow")
    private String userName;

    @Size(min = 8, message = "{user.password.size}")
    @ApiModelProperty(value = "User password", example = "n5kt4h#M@qcGtd%u")
    private String password;

    @ApiModelProperty(value = "User system role", example = "USER")
    private UserRole role;

    @Override
    public String toString() {
        return "UserRequest(name=" + this.getName() + ", email=" + this.getEmail() + ", userName=" + this.getUserName() + ", role=" + this.getRole() + ")";
    }
}