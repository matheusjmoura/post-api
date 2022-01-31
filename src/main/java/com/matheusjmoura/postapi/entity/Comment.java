package com.matheusjmoura.postapi.entity;

import com.matheusjmoura.postapi.controller.request.comment.CommentRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document("comment")
public class Comment {

    @Id
    private UUID id;

    private String text;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    private UUID userId;

    private UUID postId;

    public static Comment createComment(CommentRequest commentRequest, @NonNull User user, @NonNull Post post) {
        return new Comment(UUID.randomUUID(),
            commentRequest.getText(),
            LocalDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.systemDefault()),
            user.getId(),
            post.getId());
    }

    public boolean isOwnerUser(UUID userId) {
        return this.userId.equals(userId);
    }

}
