package com.sam.wealthmangement.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.wealthmangement.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
