package com.sam.wealthmangement.blog.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sam.wealthmangement.blog.model.BlogUser;
import com.sam.wealthmangement.blog.model.Comment;
import com.sam.wealthmangement.blog.model.Post;
import com.sam.wealthmangement.blog.repository.BlogUserRepository;
import com.sam.wealthmangement.blog.repository.CommentRepository;
import com.sam.wealthmangement.blog.repository.PostRepository;

@Service
@GraphQLApi
@RequiredArgsConstructor
public class PostService {

    private final BlogUserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GraphQLQuery
    public List<Post> allPosts() {
        return postRepository.findAll();
    }

    @GraphQLQuery
    public Post post(String slug) {
        return postRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
    }

    @GraphQLMutation
    public Post addPost(String title, String content) {
        Optional<BlogUser> blogUserOptional = userRepository.findByUsername(getUserName());
        BlogUser blogUser = blogUserOptional.orElseThrow(IllegalAccessError::new);
        return postRepository.save(new Post(title, content, blogUser));
    }

    @GraphQLMutation
    public Comment addComment(String content, String postSlug) {
        Optional<BlogUser> blogUserOptional = userRepository.findByUsername(getUserName());
        BlogUser blogUser = blogUserOptional.orElseThrow(IllegalAccessError::new);
        Post post = postRepository.findBySlug(postSlug).orElseThrow(EntityNotFoundException::new);
        return commentRepository.save(new Comment(content, blogUser, post));
    }

}
