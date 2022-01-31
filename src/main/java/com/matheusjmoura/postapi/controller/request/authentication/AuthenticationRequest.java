package com.matheusjmoura.postapi.controller.request.authentication;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationRequest {

    @NotBlank(groups = {AdvancedInfo.class}, message = "{user.name.notBlank}")
    @Size(groups = {AdvancedInfo.class}, min = 4, max = 15, message = "{user.name.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$", message = "{user.name.invalid}")
    @ApiModelProperty(value = "User name", example = "John Snow")
    @JsonView(AdvancedInfo.class)
    private String name;

    @Email(groups = {AdvancedInfo.class}, message = "{user.email.badFormat}")
    @NotBlank(groups = {AdvancedInfo.class}, message = "{user.email.notBlank}")
    @ApiModelProperty(value = "User email", example = "johnsnow@email.com")
    @JsonView(AdvancedInfo.class)
    private String email;

    @Size(groups = {BasicInfo.class}, min = 4, max = 15, message = "{user.username.size}")
    @NotBlank(groups = {BasicInfo.class}, message = "{user.username.notBlank}")
    @ApiModelProperty(value = "Username", example = "johnsnow")
    @JsonView(BasicInfo.class)
    private String username;

    @Size(groups = {BasicInfo.class}, min = 8, message = "{user.password.size}")
    @NotBlank(groups = {BasicInfo.class}, message = "{user.password.notBlank}")
    @ApiModelProperty(value = "User password", example = "n5kt4h#M@qcGtd%u")
    @JsonView(BasicInfo.class)
    private String password;

    @Override
    public String toString() {
        return "AuthenticationRequest(name=" + this.getName() + ", userName=" + this.getUsername() + ", email=" + this.getEmail() + ")";
    }

    public interface BasicInfo {
    }

    public interface AdvancedInfo extends BasicInfo {
    }

}
