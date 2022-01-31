package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.controller.request.comment.CommentFilterRequest;
import com.matheusjmoura.postapi.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
    Page<Comment> findCommentsByProperties(CommentFilterRequest filters, Pageable page);
}
