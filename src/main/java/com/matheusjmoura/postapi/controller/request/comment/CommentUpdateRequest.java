package com.matheusjmoura.postapi.controller.request.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentUpdateRequest {

    @NotBlank(message = "{comment.text.notBlank}")
    @Size(min = 1, max = 150, message = "{comment.text.size}")
    @ApiModelProperty(value = "Comment text", example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry...")
    private String text;

}