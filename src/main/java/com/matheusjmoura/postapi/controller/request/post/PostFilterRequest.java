package com.matheusjmoura.postapi.controller.request.post;

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
public class PostFilterRequest {

    @ApiModelProperty(value = "Post title", example = "Lorem Ipsum")
    private String title;

    @ApiModelProperty(value = "Post body", example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry...")
    private String body;

    @ApiModelProperty(value = "Initial date", example = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime initialDate;

    @ApiModelProperty(value = "Final date", example = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime finalDate;

    @ApiModelProperty(value = "User id", example = "e3dbe3df-197d-421d-9ccc-a1491b617479")
    private UUID userId;

}
