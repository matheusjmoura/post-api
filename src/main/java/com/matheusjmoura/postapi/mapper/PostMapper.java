package com.matheusjmoura.postapi.mapper;

import com.matheusjmoura.postapi.controller.request.post.PostUpdatRequest;
import com.matheusjmoura.postapi.entity.Post;
import com.matheusjmoura.postapi.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {

    public static Post buildPostUpdate(Post post, User user, PostUpdatRequest postUpdatRequest) {
        return Optional.ofNullable(postUpdatRequest).map(request ->
            Post.builder()
                .id(post.getId())
                .title(request.getTitle())
                .body(request.getBody())
                .date(post.getDate())
                .userId(user.getId())
                .comments(post.getComments())
                .build()
        ).orElse(null);
    }

}
