package com.matheusjmoura.postapi.entity;

import com.matheusjmoura.postapi.controller.request.post.PostRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document("post")
public class Post {

    @Id
    private UUID id;

    private String title;

    private String body;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    private UUID userId;

    @Builder.Default
    private List<UUID> comments = new ArrayList<>();

    public static Post createPost(PostRequest postRequest, @NonNull User user) {
        return new Post(UUID.randomUUID(),
            postRequest.getTitle(),
            postRequest.getBody(),
            LocalDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.systemDefault()),
            user.getId(),
            List.of());
    }

    public void insertComment(@NonNull UUID commentId) {
        this.comments.add(commentId);
    }

    public boolean isOwnerUser(@NonNull UUID userId) {
        return this.userId.equals(userId);
    }

    public boolean relatedToComment(UUID commentId) {
        return this.comments.contains(commentId);
    }

}
