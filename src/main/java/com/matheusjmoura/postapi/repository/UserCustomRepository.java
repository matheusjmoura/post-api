package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.controller.request.user.UserFilterRequest;
import com.matheusjmoura.postapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {
    Page<User> findUsersByProperties(UserFilterRequest filters, Pageable page);
}
