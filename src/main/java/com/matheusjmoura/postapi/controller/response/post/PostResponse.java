package com.matheusjmoura.postapi.controller.response.post;

import com.matheusjmoura.postapi.entity.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse {

    @ApiModelProperty(value = "Post id", example = "5d7d4740-769d-11ec-90d6-0242ac120003")
    private UUID id;

    @ApiModelProperty(value = "Post title", example = "Lorem Ipsum")
    private String title;

    @ApiModelProperty(value = "Post body", example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry...")
    private String body;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(value = "Post date", example = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime date;

    @ApiModelProperty(value = "Post owner id", example = "e3dbe3df-197d-421d-9ccc-a1491b617479")
    private UUID userId;

    @ApiModelProperty(value = "Comments id's of the post", example = "[5d7d4740-769d-11ec-90d6-0242ac120003, d6998a3a-97df-4794-8f2c-34d0155aef49]")
    private List<UUID> comments;

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(),
            post.getTitle(),
            post.getBody(),
            ZonedDateTime.of(post.getDate(), ZoneId.systemDefault()),
            post.getUserId(),
            post.getComments());
    }

}
