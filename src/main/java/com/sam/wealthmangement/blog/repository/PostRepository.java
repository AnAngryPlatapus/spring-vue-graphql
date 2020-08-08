package com.sam.wealthmangement.blog.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sam.wealthmangement.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAll();

    List<Post> findAllByBlogUser_Username(String blogUsername);

    Optional<Post> findBySlug(String slug);
}
