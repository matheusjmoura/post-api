package com.matheusjmoura.postapi.controller;

import com.matheusjmoura.postapi.commons.application.annotation.ApiAuthPageable;
import com.matheusjmoura.postapi.commons.application.annotation.ApiAuthorization;
import com.matheusjmoura.postapi.commons.application.response.ApiCollectionResponse;
import com.matheusjmoura.postapi.commons.exception.BodyError;
import com.matheusjmoura.postapi.controller.request.comment.CommentFilterRequest;
import com.matheusjmoura.postapi.controller.request.comment.CommentRequest;
import com.matheusjmoura.postapi.controller.request.comment.CommentUpdateRequest;
import com.matheusjmoura.postapi.controller.response.comment.CommentResponse;
import com.matheusjmoura.postapi.entity.Comment;
import com.matheusjmoura.postapi.service.CommentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/comment")
@Api(value = "comment-operations", tags = "Comment Operations")
public class CommentController {

    private final CommentService commentService;

    @ApiAuthPageable
    @GetMapping(path = "/search")
    @ApiOperation(value = "Search comments with filters", response = ApiCollectionResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = ApiCollectionResponse.class)
    })
    public ResponseEntity<ApiCollectionResponse<CommentResponse>> searchByFilters(
        @ApiParam(value = "CommentFilterRequest") CommentFilterRequest filters,
        @ApiIgnore @ApiParam(value = "PageRequest") Pageable pageable
    ) {
        log.info("[CommentController.findAll] Receiving request to search comments with filters");
        return ResponseEntity.ok(commentService.searchByFilters(filters, pageable));
    }

    @ApiAuthorization
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Search comment by id", response = CommentResponse.class)
    public ResponseEntity<CommentResponse> findById(@PathVariable @ApiParam(value = "Comment id", required = true) UUID id) {
        log.info("[CommentController.findById] Receiving request to find comment by id");
        CommentResponse response = CommentResponse.from(commentService.findById(id));
        return ResponseEntity.ok(response);
    }

    @ApiAuthorization
    @PostMapping(path = "/create")
    @ApiOperation(value = "Create a new comment", response = CommentResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = CommentResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BodyError.class),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CommentResponse> create(
        @RequestBody @Valid @ApiParam(value = "CommentRequest", required = true) CommentRequest commentRequest
    ) {
        log.info("[CommentController.create] Receiving request to create a new comment");
        Comment comment = commentService.create(commentRequest);
        CommentResponse commentResponse = CommentResponse.from(comment);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @ApiAuthorization
    @PutMapping(path = "/{id}/update")
    @ApiOperation(value = "Update text of the comment", response = CommentResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = CommentResponse.class)
    })
    public ResponseEntity<CommentResponse> update(
        @PathVariable @ApiParam(value = "Comment id", required = true) UUID id,
        @RequestParam @ApiParam(value = "User id", required = true) UUID userId,
        @RequestParam @ApiParam(value = "Post id", required = true) UUID postId,
        @RequestBody @Valid @ApiParam(value = "UpdateCommentRequest", required = true) CommentUpdateRequest request
    ) {
        log.info("[CommentController.update] Receiving request to update a comment");
        Comment comment = commentService.update(id, userId, postId, request);
        CommentResponse commentResponse = CommentResponse.from(comment);
        return ResponseEntity.ok(commentResponse);
    }

    @ApiAuthorization
    @ApiOperation(value = "Delete a comment")
    @DeleteMapping(path = "/{id}/delete")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable @ApiParam(value = "Comment id", required = true) UUID id,
                                       @RequestParam @ApiParam(value = "User id", required = true) UUID userId,
                                       @RequestParam @ApiParam(value = "Post id", required = true) UUID postId) {
        log.info("[CommentController.delete] Receiving request to delete a comment");
        commentService.delete(id, userId, postId);
        return ResponseEntity.noContent().build();
    }

}
