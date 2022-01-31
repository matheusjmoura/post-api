package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.controller.request.comment.CommentFilterRequest;
import com.matheusjmoura.postapi.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Comment> findCommentsByProperties(CommentFilterRequest filters, Pageable page) {
        final Query query = new Query().with(page);
        final List<Criteria> criteria = new ArrayList<>();
        if (StringUtils.isNotBlank(filters.getText()))
            criteria.add(Criteria.where("text").in(filters.getText()));
        if (!ObjectUtils.isEmpty(filters.getInitialDate()) && !ObjectUtils.isEmpty(filters.getFinalDate()))
            criteria.add(Criteria.where("date").in(filters.getInitialDate(), filters.getFinalDate()));
        if (!ObjectUtils.isEmpty(filters.getUserId()))
            criteria.add(Criteria.where("userId").is(filters.getUserId()));
        if (!ObjectUtils.isEmpty(filters.getPostId()))
            criteria.add(Criteria.where("postId").is(filters.getPostId()));

        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        List<Comment> postList = mongoTemplate.find(query, Comment.class);
        return PageableExecutionUtils.getPage(postList, page, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Comment.class));
    }
}
