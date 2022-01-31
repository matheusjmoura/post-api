package com.matheusjmoura.postapi.controller;

import com.matheusjmoura.postapi.commons.application.annotation.ApiAuthPageable;
import com.matheusjmoura.postapi.commons.application.annotation.ApiAuthorization;
import com.matheusjmoura.postapi.commons.application.response.ApiCollectionResponse;
import com.matheusjmoura.postapi.controller.request.user.UserFilterRequest;
import com.matheusjmoura.postapi.controller.request.user.UserUpdateRequest;
import com.matheusjmoura.postapi.controller.response.user.UserResponse;
import com.matheusjmoura.postapi.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user")
@Api(value = "user-operations", tags = "User Operations")
public class UserController {

    private final UserService userService;

    @ApiAuthPageable
    @GetMapping(path = "/search")
    @ApiOperation(value = "Search users with filters", response = ApiCollectionResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = ApiCollectionResponse.class)
    })
    public ResponseEntity<ApiCollectionResponse<UserResponse>> searchByFilters(
        @ApiParam(value = "UserFilterRequest") UserFilterRequest filters,
        @ApiIgnore @ApiParam(value = "PageRequest") Pageable pageable
    ) {
        log.info("[PostController.findAll] Receiving request to search users with filters");
        return ResponseEntity.ok(userService.searchByFilters(filters, pageable));
    }

    @ApiAuthorization
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Search user by id", response = UserResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = UserResponse.class)
    })
    public ResponseEntity<UserResponse> findById(@PathVariable @ApiParam(value = "User id", required = true) UUID id) {
        log.info("[UserController.findById] Receiving request to find user by id");
        UserResponse response = UserResponse.from(userService.findById(id));
        return ResponseEntity.ok(response);
    }

    @ApiAuthorization
    @ApiOperation("Update user data")
    @PutMapping(path = "/{id}/update")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateUser(@PathVariable @ApiParam(value = "User id", required = true) UUID id,
                                           @RequestBody @Valid @ApiParam(value = "UserUpdateRequest", required = true) UserUpdateRequest request) {
        log.info("[UserController.updateUser] Receiving request to update a user");
        userService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @ApiAuthorization
    @ApiOperation(value = "Activate user")
    @PutMapping(path = "/{id}/activate")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activate(@PathVariable @ApiParam(value = "User id", required = true) UUID id) {
        log.info("[UserController.activate] Receiving request to activate a user");
        userService.activate(id);
        return ResponseEntity.noContent().build();
    }

    @ApiAuthorization
    @ApiOperation(value = "Deactivate user")
    @PutMapping(path = "/{id}/deactivate")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable @ApiParam(value = "User id", required = true) UUID id) {
        log.info("[UserController.deactivate] Receiving request to deactivate a user");
        userService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @ApiAuthorization
    @GetMapping("username")
    @ApiOperation(value = "Get username by Access Token", response = Map.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok", response = Map.class)
    })
    public Map<String, Object> getUserNameByToken() {
        log.info("[UserController.deactivate] Receiving request to get username by access token");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", authentication.getName());
        userMap.put("error", false);
        return userMap;
    }
}
