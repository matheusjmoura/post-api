package com.matheusjmoura.postapi.repository;

import com.matheusjmoura.postapi.controller.request.user.UserFilterRequest;
import com.matheusjmoura.postapi.entity.User;
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
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<User> findUsersByProperties(UserFilterRequest filters, Pageable page) {
        final Query query = new Query().with(page);
        final List<Criteria> criteria = new ArrayList<>();
        if (StringUtils.isNotBlank(filters.getName()))
            criteria.add(Criteria.where("name").in(filters.getName()));
        if (StringUtils.isNotBlank(filters.getEmail()))
            criteria.add(Criteria.where("email").in(filters.getEmail()));
        if (!ObjectUtils.isEmpty(filters.getUserName()))
            criteria.add(Criteria.where("username").is(filters.getUserName()));
        if (!ObjectUtils.isEmpty(filters.getActive()))
            criteria.add(Criteria.where("active").is(filters.getActive()));
        if (!ObjectUtils.isEmpty(filters.getRole()))
            criteria.add(Criteria.where("role").is(filters.getRole()));

        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        List<User> userList = mongoTemplate.find(query, User.class);
        return PageableExecutionUtils.getPage(userList, page, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), User.class));
    }

}
