package com.matheusjmoura.postapi.controller.request.comment;

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
public class CommentRequest {

    @NotNull(message = "{comment.user.notNull}")
    @ApiModelProperty(value = "User id", example = "63c632ba-769d-11ec-90d6-0242ac120003")
    private UUID userId;

    @NotNull(message = "{comment.post.notNull}")
    @ApiModelProperty(value = "Post id", example = "8a64efbe-56bf-4e53-a9fb-d0642196d537")
    private UUID postId;

    @NotBlank(message = "{comment.text.notBlank}")
    @Size(min = 1, max = 150, message = "{comment.text.size}")
    @ApiModelProperty(value = "Comment text", example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry...")
    private String text;

}