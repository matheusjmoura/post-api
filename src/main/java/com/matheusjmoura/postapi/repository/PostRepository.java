package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<Post, UUID>, PostCustomRepository {
}
