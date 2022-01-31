package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID>, UserCustomRepository {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String email);

    @Query(value = "{username:'?0'}")
    User findUserByUsername(String username);
}