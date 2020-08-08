package com.sam.wealthmangement.blog.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sam.wealthmangement.blog.model.BlogUser;
import com.sam.wealthmangement.blog.model.Role;
import com.sam.wealthmangement.blog.repository.BlogUserRepository;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final BlogUserRepository blogUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        BlogUser user = blogUserRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("No such such user with username: " + userName));
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());

    }

    public BlogUser signUp(BlogUser user) {
        String password = user.getPassword();
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(password));
        return blogUserRepository.save(user);
    }
}
