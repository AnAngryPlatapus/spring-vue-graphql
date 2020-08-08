package com.sam.wealthmangement.blog.service;

import java.util.List;
import java.util.Optional;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sam.wealthmangement.blog.model.BlogUser;
import com.sam.wealthmangement.blog.repository.BlogUserRepository;

@Service
@GraphQLApi
@RequiredArgsConstructor
public class UserService {

    private final BlogUserRepository blogUserRepository;

    private String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GraphQLQuery
    public BlogUser currentUser() throws IllegalAccessException {
        return blogUserRepository.findByUsername(getUserName()).orElseThrow(IllegalAccessException::new);
    }

    @GraphQLQuery
    public List<BlogUser> allUsers() {
        return blogUserRepository.findAll();
    }

    @GraphQLQuery
    public BlogUser user(Long id) {
        Optional<BlogUser> user = blogUserRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("User not found for id " + id);
        }
    }
}
