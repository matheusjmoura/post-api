package com.matheusjmoura.postapi.service;

import com.matheusjmoura.postapi.commons.application.response.ApiCollectionResponse;
import com.matheusjmoura.postapi.commons.util.PageableUtils;
import com.matheusjmoura.postapi.controller.request.post.PostFilterRequest;
import com.matheusjmoura.postapi.controller.request.post.PostRequest;
import com.matheusjmoura.postapi.controller.request.post.PostUpdatRequest;
import com.matheusjmoura.postapi.controller.response.post.PostResponse;
import com.matheusjmoura.postapi.entity.Comment;
import com.matheusjmoura.postapi.entity.Post;
import com.matheusjmoura.postapi.entity.User;
import com.matheusjmoura.postapi.exception.PostNotFoundException;
import com.matheusjmoura.postapi.exception.UserNotPostOwnerException;
import com.matheusjmoura.postapi.mapper.PostMapper;
import com.matheusjmoura.postapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public ApiCollectionResponse<PostResponse> searchByFilters(PostFilterRequest filters, Pageable pageable) {
        log.info("[PostService.searchByFilters] Starting posts search process - Filters: {}, Pageable: {}", filters, pageable);

        pageable = PageableUtils.sort(pageable, "text");
        Page<PostResponse> posts = postRepository.findPostsByProperties(filters, pageable).map(PostResponse::from);

        ApiCollectionResponse<PostResponse> response = ApiCollectionResponse.from(posts);
        log.info("[PostService.searchByFilters] Posts search process completed successfully");
        return response;
    }

    public Post create(PostRequest postRequest) {
        log.info("[PostService.create] Starting post creation process - Request: {}", postRequest);

        User user = userService.findByIdAndIsActive(postRequest.getUserId());

        log.info("[PostService.create] Creating new post");
        Post savedPost = postRepository.save(Post.createPost(postRequest, user));

        log.info("[PostService.create] Post creation process completed successfully - Saved post: {}", savedPost);
        return savedPost;
    }

    public Post update(UUID postId, UUID userId, PostUpdatRequest postUpdatRequest) {
        log.info("[PostService.update] Starting post update process - Post id: {}, User id: {}, Request: {}",
            postId, userId, postUpdatRequest);

        User user = userService.findByIdAndIsActive(userId);
        Post post = findById(postId);

        log.info("[PostService.update] Checking if user is post owner - Post id: {}, User id: {}", postId, userId);
        if (!post.isOwnerUser(userId)) {
            log.error("[PostService.update] User is not post owner - Post id: {}, User id: {}", postId, userId);
            throw new UserNotPostOwnerException(userId);
        }

        log.info("[PostService.update] Updating post - Post id: {}", postId);
        Post updatedPost = postRepository.save(PostMapper.buildPostUpdate(post, user, postUpdatRequest));

        log.info("[PostService.update] Post update process completed successfully - Updated post: {}", updatedPost);
        return updatedPost;
    }

    public void delete(UUID postId, UUID userId) {
        log.info("[PostService.delete] Starting post deletion process - Post id: {}, User id: {}", postId, userId);

        userService.findByIdAndIsActive(userId);
        Post post = findById(postId);

        log.info("[PostService.delete] Checking if user is post owner - Post id: {}, User id: {}", postId, userId);
        if (!post.isOwnerUser(userId)) {
            log.error("[PostService.delete] User is not post owner - Post id: {}, User id: {}", postId, userId);
            throw new UserNotPostOwnerException(userId);
        }

        log.info("[PostService.delete] Deleting post - Post id: {}", postId);
        postRepository.deleteById(postId);

        log.info("[PostService.delete] Post deletion process completed successfully");
    }

    public Post findById(UUID postId) {
        log.info("[PostService.findById] Starting post search by id - Post id: {}", postId);
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> {
                log.error("[PostService.findById] Post not found - Post id: {}", postId);
                throw new PostNotFoundException("post.exception.notFound", postId);
            });
        log.info("[PostService.findById] Post search by id completed successfully - Post: {}", post);
        return post;
    }

    public void insertComment(Post post, Comment comment) {
        log.info("[PostService.insertComment] Starting comment insertion into post - Post id: {}, Comment id: {}",
            post.getId(), comment.getId());

        post.insertComment(comment.getId());
        postRepository.save(post);

        log.info("[PostService.insertComment] Comment insertion into post completed successfully - Post id: {}, Comment id: {}",
            post.getId(), comment.getId());
    }

}
