package com.matheusjmoura.postapi.controller.request.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentFilterRequest {

    @ApiModelProperty(value = "Comment text", example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry...")
    private String text;

    @ApiModelProperty(value = "Initial date", example = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime initialDate;

    @ApiModelProperty(value = "Final date", example = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime finalDate;

    @ApiModelProperty(value = "User id", example = "e3dbe3df-197d-421d-9ccc-a1491b617479")
    private UUID userId;

    @ApiModelProperty(value = "Post id", example = "8a64efbe-56bf-4e53-a9fb-d0642196d537")
    private UUID postId;

}
