package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends MongoRepository<Comment, UUID>, CommentCustomRepository {
}
