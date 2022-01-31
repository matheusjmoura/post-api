package com.matheusjmoura.postapi.service;

import com.matheusjmoura.postapi.commons.application.response.ApiCollectionResponse;
import com.matheusjmoura.postapi.commons.util.PageableUtils;
import com.matheusjmoura.postapi.controller.request.user.UserFilterRequest;
import com.matheusjmoura.postapi.controller.request.user.UserUpdateRequest;
import com.matheusjmoura.postapi.controller.response.user.UserResponse;
import com.matheusjmoura.postapi.entity.User;
import com.matheusjmoura.postapi.exception.UserDeactivatedException;
import com.matheusjmoura.postapi.exception.UserNotFoundException;
import com.matheusjmoura.postapi.mapper.UserMapper;
import com.matheusjmoura.postapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ApiCollectionResponse<UserResponse> searchByFilters(UserFilterRequest filters, Pageable pageable) {
        log.info("[PostService.findAll] Starting users search process - Filters: {}, Pageable: {}", filters, pageable);

        pageable = PageableUtils.sort(pageable, "name");
        Page<UserResponse> users = userRepository.findUsersByProperties(filters, pageable).map(UserResponse::from);

        ApiCollectionResponse<UserResponse> response = ApiCollectionResponse.from(users);
        log.info("[PostService.findAll] Users search process completed successfully");
        return response;
    }

    public void update(UUID userId, UserUpdateRequest request) {
        log.info("[UserService.update] Starting user update process - User id: {}, Request: {}", userId, request);

        User user = findById(userId);
        if (Boolean.FALSE.equals(user.getActive())) {
            log.error("[UserService.update] User is deactivated");
            throw new UserDeactivatedException(userId);
        }

        log.info("[UserService.update] Updating user - User id: {}", userId);
        User updatedUser = userRepository.save(UserMapper.buildUserUpdate(user, request));

        log.info("[PostService.update] User update process completed successfully - Updated user: {}", updatedUser);
    }

    public void activate(UUID userId) {
        log.info("[UserService.activate] Starting user activation process - User id: {}", userId);

        User user = findById(userId);
        if (Boolean.TRUE.equals(user.getActive())) {
            log.error("[UserService.activate] User is already activated, ignoring request");
            return;
        }

        log.info("[UserService.activate] Activating user - User id: {}", userId);
        user.activate();
        userRepository.save(user);

        log.info("[PostService.update] User activation process completed successfully - User id: {}", userId);
    }

    public void deactivate(UUID userId) {
        log.info("[UserService.activate] Starting user deactivation process - User id: {}", userId);

        User user = findById(userId);
        if (Boolean.FALSE.equals(user.getActive())) {
            log.error("[UserService.deactivate] User is already deactivated, ignoring request");
            return;
        }

        log.info("[UserService.deactivate] Deactivating user - User id: {}", userId);
        user.deactivate();
        userRepository.save(user);

        log.info("[PostService.update] User deactivation process completed successfully - User id: {}", userId);
    }

    public User findById(UUID userId) {
        log.info("[UserService.findById] Starting user search in database - User id: {}", userId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.error("[UserService.findById] User not found - User id: {}", userId);
                throw new UserNotFoundException(userId);
            });
        log.info("[UserService.findById] Finishing user search in the database - User: {}", user);
        return user;
    }

    public User findByIdAndIsActive(UUID userId) {
        log.info("[UserService.findByIdAndIsActive] Checking if user exists and is active - User id: {}", userId);
        User user = findById(userId);
        if (Boolean.FALSE.equals(user.getActive())) {
            log.error("[UserService.findByIdAndIsActive] User is deactivated - User id: {}", user.getId());
            throw new UserDeactivatedException(user.getId());
        }
        return user;
    }
}
