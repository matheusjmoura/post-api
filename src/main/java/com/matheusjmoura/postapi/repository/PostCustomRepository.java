package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.controller.request.post.PostFilterRequest;
import com.matheusjmoura.postapi.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {
    Page<Post> findPostsByProperties(PostFilterRequest filters, Pageable page);
}
