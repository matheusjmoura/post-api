package com.matheusjmoura.postapi.controller.response.user;

import com.matheusjmoura.postapi.entity.User;
import com.matheusjmoura.postapi.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    @ApiModelProperty(value = "User id", example = "e3dbe3df-197d-421d-9ccc-a1491b617479")
    private UUID id;

    @ApiModelProperty(value = "User name", example = "John Snow")
    private String name;

    @ApiModelProperty(value = "User email", example = "johnsnow@email.com")
    private String email;

    @ApiModelProperty(value = "User username", example = "johnsnow")
    private String userName;

    @ApiModelProperty(value = "User encrypted password", example = "$2a$10$giHjVmbB4VRS0nwyrVBAUuWB9txFQ3L8M9TvFKFSf90UUpWvxMi7G")
    private String password;

    @ApiModelProperty(value = "User status", example = "true")
    private boolean active;

    @ApiModelProperty(value = "User system role", example = "USER")
    private UserRole role;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(),
            user.getName(),
            user.getEmail(),
            user.getUsername(),
            user.getPassword(),
            user.getActive(),
            user.getRole());
    }

}
