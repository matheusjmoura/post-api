package com.matheusjmoura.postapi.controller.request.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdatRequest {

    @Size(min = 4, max = 30, message = "{post.title.size}")
    @ApiModelProperty(value = "Post title", example = "Lorem Ipsum")
    private String title;

    @Size(min = 1, max = 250, message = "{post.body.size}")
    @ApiModelProperty(value = "Post body", example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry...")
    private String body;

}