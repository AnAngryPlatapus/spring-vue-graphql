package com.sam.wealthmangement.blog.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sam.wealthmangement.blog.model.BlogUser;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {

    List<BlogUser> findAll();

    Optional<BlogUser> findById(Long id);

    Optional<BlogUser> findByEmail(String email);

    Optional<BlogUser> findByUsername(String username);
}
