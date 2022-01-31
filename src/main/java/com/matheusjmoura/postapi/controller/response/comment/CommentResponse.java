package com.matheusjmoura.postapi.controller.response.comment;

import com.matheusjmoura.postapi.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

    @ApiModelProperty(value = "Comment id", example = "5d7d4740-769d-11ec-90d6-0242ac120003")
    private UUID id;

    @ApiModelProperty(value = "Comment text", example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry...")
    private String text;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(value = "Comment date", example = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime date;

    @ApiModelProperty(value = "Comment owner id", example = "e3dbe3df-197d-421d-9ccc-a1491b617479")
    private UUID userId;

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment.getId(),
            comment.getText(),
            ZonedDateTime.of(comment.getDate(), ZoneId.systemDefault()),
            comment.getUserId());
    }

}
