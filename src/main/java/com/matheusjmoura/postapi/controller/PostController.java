package com.matheusjmoura.postapi.controller;

import com.matheusjmoura.postapi.commons.application.annotation.ApiAuthPageable;
import com.matheusjmoura.postapi.commons.application.annotation.ApiAuthorization;
import com.matheusjmoura.postapi.commons.application.response.ApiCollectionResponse;
import com.matheusjmoura.postapi.commons.exception.BodyError;
import com.matheusjmoura.postapi.controller.request.post.PostFilterRequest;
import com.matheusjmoura.postapi.controller.request.post.PostRequest;
import com.matheusjmoura.postapi.controller.request.post.PostUpdatRequest;
import com.matheusjmoura.postapi.controller.response.post.PostResponse;
import com.matheusjmoura.postapi.service.PostService;
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
@RequestMapping(path = "/api/post")
@Api(value = "post-operations", tags = "Post Operations")
public class PostController {

    private final PostService postService;

    @ApiAuthPageable
    @GetMapping(path = "/search")
    @ApiOperation(value = "Search posts with filters", response = ApiCollectionResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = ApiCollectionResponse.class)
    })
    public ResponseEntity<ApiCollectionResponse<PostResponse>> searchByFilters(
        @ApiParam(value = "PostFilterRequest") PostFilterRequest filters,
        @ApiIgnore @ApiParam(value = "PageRequest") Pageable pageable
    ) {
        log.info("[PostController.searchByFilters] Receiving request to search posts with filters");
        return ResponseEntity.ok(postService.searchByFilters(filters, pageable));
    }

    @ApiAuthorization
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Search post by id", response = PostResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = PostResponse.class),
    })
    public ResponseEntity<PostResponse> findById(@PathVariable @ApiParam(value = "Post id", required = true) UUID id) {
        log.info("[PostController.findById] Receiving request to find post by id");
        PostResponse postResponse = PostResponse.from(postService.findById(id));
        return ResponseEntity.ok(postResponse);
    }

    @ApiAuthorization
    @PostMapping(path = "/create")
    @ApiOperation(value = "Create a new post", response = PostResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created", response = PostResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BodyError.class),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<PostResponse> create(
        @RequestBody @Valid @ApiParam(value = "PostRequest", required = true) PostRequest postRequest
    ) {
        log.info("[PostController.create] Receiving request to create a new post");
        PostResponse postResponse = PostResponse.from(postService.create(postRequest));
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @ApiAuthorization
    @PutMapping(path = "/{id}/update")
    @ApiOperation(value = "Update title and body of the post", response = PostResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = PostResponse.class)
    })
    public ResponseEntity<PostResponse> update(
        @PathVariable @ApiParam(value = "Post id", required = true) UUID id,
        @RequestParam @ApiParam(value = "User id", required = true) UUID userId,
        @RequestBody @Valid @ApiParam(value = "PostUpdateRequest", required = true) PostUpdatRequest postUpdatRequest
    ) {
        log.info("[PostController.update] Receiving request to update a post");
        PostResponse postResponse = PostResponse.from(postService.update(id, userId, postUpdatRequest));
        return ResponseEntity.ok(postResponse);
    }

    @ApiAuthorization
    @ApiOperation("Delete a post")
    @DeleteMapping(path = "/{id}/delete")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable @ApiParam(value = "Post id", required = true) UUID id,
                                       @RequestParam @ApiParam(value = "User id", required = true) UUID userId) {
        log.info("[PostController.delete] Receiving request to delete a post");
        postService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

}
