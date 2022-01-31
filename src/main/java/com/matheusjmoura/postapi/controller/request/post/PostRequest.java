package com.matheusjmoura.postapi.controller.request.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRequest {

    @NotNull(message = "{post.user.notNull}")
    @ApiModelProperty(value = "User id", example = "63c632ba-769d-11ec-90d6-0242ac120003")
    private UUID userId;

    @NotBlank(message = "{post.title.notBlank}")
    @Size(min = 4, max = 30, message = "{post.title.size}")
    @ApiModelProperty(value = "Post title", example = "Lorem Ipsum")
    private String title;

    @NotBlank(message = "{post.body.notBLank}")
    @Size(min = 1, max = 250, message = "{post.body.size}")
    @ApiModelProperty(value = "Post body", example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry...")
    private String body;

}