package com.matheusjmoura.postapi.service;

import com.matheusjmoura.postapi.commons.application.response.ApiCollectionResponse;
import com.matheusjmoura.postapi.commons.util.PageableUtils;
import com.matheusjmoura.postapi.controller.request.comment.CommentFilterRequest;
import com.matheusjmoura.postapi.controller.request.comment.CommentRequest;
import com.matheusjmoura.postapi.controller.request.comment.CommentUpdateRequest;
import com.matheusjmoura.postapi.controller.response.comment.CommentResponse;
import com.matheusjmoura.postapi.entity.Comment;
import com.matheusjmoura.postapi.entity.Post;
import com.matheusjmoura.postapi.entity.User;
import com.matheusjmoura.postapi.exception.CommentNotFoundException;
import com.matheusjmoura.postapi.exception.CommentNotRelatedException;
import com.matheusjmoura.postapi.exception.CommentPermissionException;
import com.matheusjmoura.postapi.mapper.CommentMapper;
import com.matheusjmoura.postapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public ApiCollectionResponse<CommentResponse> searchByFilters(CommentFilterRequest filters, Pageable pageable) {
        log.info("[CommentService.findAll] Starting comments search process - Filters: {}, Pageable: {}", filters, pageable);

        pageable = PageableUtils.sort(pageable, "text");
        Page<CommentResponse> comments = commentRepository.findCommentsByProperties(filters, pageable).map(CommentResponse::from);

        ApiCollectionResponse<CommentResponse> response = ApiCollectionResponse.from(comments);
        log.info("[CommentService.findAll] Comments search process completed successfully");
        return response;
    }

    public Comment create(CommentRequest commentRequest) {
        log.info("[CommentService.create] Starting comment creation process - Request: {}", commentRequest);

        User user = userService.findByIdAndIsActive(commentRequest.getUserId());
        Post post = postService.findById(commentRequest.getPostId());

        log.info("[CommentService.create] Creating new comment");
        Comment savedComment = commentRepository.save(Comment.createComment(commentRequest, user, post));
        postService.insertComment(post, savedComment);

        log.info("[CommentService.create] Comment creation process completed successfully - Saved comment: {}", savedComment);
        return savedComment;
    }

    public Comment update(UUID commentId, UUID userId, UUID postId, CommentUpdateRequest commentUpdateRequest) {
        log.info("[CommentService.update] Starting comment update process - Comment id: {}, Request: {}", commentId, commentUpdateRequest);

        userService.findByIdAndIsActive(userId);
        Post post = postService.findById(postId);
        Comment comment = findById(commentId);

        log.info("[CommentService.update] Checking if user is comment owner - Comment id: {}, User id: {}", commentId, userId);
        if (!comment.isOwnerUser(userId)) {
            log.error("[CommentService.update] User is not comment owner or post owner - Comment id: {}, User id: {}", commentId, userId);
            throw new CommentPermissionException("comment.exception.update.permission", userId);
        }

        log.info("[CommentService.update] Checking if comment is related to post - Comment id: {}, Post id: {}", commentId, postId);
        if (!post.relatedToComment(comment.getId())) {
            log.error("[CommentService.update] Comment is not related to post - Comment id: {}, Post id: {}", commentId, postId);
            throw new CommentNotRelatedException(commentId, postId);
        }

        log.info("[CommentService.update] Updating comment - Comment id: {}", commentId);
        Comment updatedComment = commentRepository.save(CommentMapper.buildCommentUpdate(commentUpdateRequest, comment));

        log.info("[CommentService.update] Comment update process completed successfully - Updated comment: {}", updatedComment);
        return updatedComment;
    }

    public void delete(UUID commentId, UUID userId, UUID postId) {
        log.info("[CommentService.delete] Starting comment deletion process - Comment id: {}, User id: {}", commentId, userId);

        userService.findByIdAndIsActive(userId);
        Post post = postService.findById(postId);
        Comment comment = findById(commentId);

        log.info("[CommentService.delete] Checking if user is comment owner or post owner - Comment id: {}, User id: {}, Post id: {}",
            commentId, userId, postId);
        if (!comment.isOwnerUser(userId) || !post.isOwnerUser(userId)) {
            log.error("[CommentService.delete] User is not comment owner or post owner - Comment id: {}, User id: {}", commentId, userId);
            throw new CommentPermissionException("comment.exception.delete.permission", userId);
        }

        log.info("[CommentService.delete] Deleting comment - Comment id: {}", commentId);
        commentRepository.deleteById(commentId);

        log.info("[CommentService.delete] Comment and all of its comments deleted.");
    }

    public Comment findById(UUID commentId) {
        log.info("[CommentService.findById] Starting comment search by id - Comment id: {}", commentId);
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> {
                log.error("[CommentService.findById] Comment not found - Comment id: {}", commentId);
                throw new CommentNotFoundException("comment.exception.notFound", commentId);
            });
        log.info("[CommentService.findById] Finishing comment search in the database - Comment: {}", comment);
        return comment;
    }

}
