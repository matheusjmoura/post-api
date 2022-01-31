package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.controller.request.post.PostFilterRequest;
import com.matheusjmoura.postapi.entity.Post;
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
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Post> findPostsByProperties(PostFilterRequest filters, Pageable page) {
        final Query query = new Query().with(page);
        final List<Criteria> criteria = new ArrayList<>();
        if (StringUtils.isNotBlank(filters.getTitle()))
            criteria.add(Criteria.where("title").in(filters.getTitle()));
        if (StringUtils.isNotBlank(filters.getBody()))
            criteria.add(Criteria.where("body").in(filters.getBody()));
        if (!ObjectUtils.isEmpty(filters.getInitialDate()) && !ObjectUtils.isEmpty(filters.getFinalDate()))
            criteria.add(Criteria.where("date").in(filters.getInitialDate(), filters.getFinalDate()));
        if (!ObjectUtils.isEmpty(filters.getUserId()))
            criteria.add(Criteria.where("userId").is(filters.getUserId()));

        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        List<Post> postList = mongoTemplate.find(query, Post.class);
        return PageableExecutionUtils.getPage(postList, page, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Post.class));
    }
}
