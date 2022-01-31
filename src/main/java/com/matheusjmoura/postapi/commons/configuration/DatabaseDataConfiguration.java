package com.matheusjmoura.postapi.commons.configuration;

import com.matheusjmoura.postapi.entity.Comment;
import com.matheusjmoura.postapi.entity.Post;
import com.matheusjmoura.postapi.entity.User;
import com.matheusjmoura.postapi.enums.UserRole;
import com.matheusjmoura.postapi.repository.CommentRepository;
import com.matheusjmoura.postapi.repository.PostRepository;
import com.matheusjmoura.postapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
//@Configuration
public class DatabaseDataConfiguration {

    @Bean
    public CommandLineRunner run(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        return (String[] args) -> loadInitialData(userRepository, postRepository, commentRepository);
    }

    public void loadInitialData(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        log.info("=== Loading database data ===");

        List<User> users = Arrays.asList(
            new User(UUID.randomUUID(), "John Barta", "johnbarta@email.com", "johnbarta",
                new BCryptPasswordEncoder().encode("123456789"), true, UserRole.ROLE_ADMIN),
            new User(UUID.randomUUID(), "Ed Snow", "edsnow@email.com", "edsnow",
                new BCryptPasswordEncoder().encode("123456789"), true, UserRole.ROLE_ADMIN),
            new User(UUID.randomUUID(), "John Snow", "johnsnow@email.com", "johnsnow",
                new BCryptPasswordEncoder().encode("n5kt4h#M@qcGtd%u"), true, UserRole.ROLE_USER));
        List<User> userList = userRepository.saveAll(users);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.systemDefault());
        List<Post> posts = Arrays.asList(
            new Post(UUID.randomUUID(), "Post Title 1", "Post content body 1", localDateTime, userList.get(0).getId(), List.of()),
            new Post(UUID.randomUUID(), "Post Title 2", "Post content body 2", localDateTime, userList.get(1).getId(), List.of()));
        List<Post> postList = postRepository.saveAll(posts);

        List<Comment> comments1 = Arrays.asList(
            new Comment(UUID.randomUUID(), "Comment text 1-1", localDateTime, userList.get(1).getId(), postList.get(0).getId()),
            new Comment(UUID.randomUUID(), "Comment text 1-2", localDateTime, userList.get(1).getId(), postList.get(0).getId()),
            new Comment(UUID.randomUUID(), "Comment text 1-3", localDateTime, userList.get(1).getId(), postList.get(0).getId()));
        List<UUID> commentList1 = commentRepository.saveAll(comments1).stream().map(Comment::getId).collect(Collectors.toList());

        List<Comment> comments2 = Arrays.asList(
            new Comment(UUID.randomUUID(), "Comment text 2-1", localDateTime, userList.get(0).getId(), postList.get(1).getId()),
            new Comment(UUID.randomUUID(), "Comment text 2-2", localDateTime, userList.get(0).getId(), postList.get(1).getId()),
            new Comment(UUID.randomUUID(), "Comment text 2-3", localDateTime, userList.get(0).getId(), postList.get(1).getId()));
        List<UUID> commentList2 = commentRepository.saveAll(comments2).stream().map(Comment::getId).collect(Collectors.toList());


        commentList1.forEach(comment -> postList.get(0).insertComment(comment));
        commentList2.forEach(comment -> postList.get(1).insertComment(comment));
        postRepository.saveAll(postList);

        log.info("=== Database loaded ===");
    }

}
