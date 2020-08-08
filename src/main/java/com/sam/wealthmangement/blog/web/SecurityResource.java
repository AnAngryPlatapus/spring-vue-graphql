package com.sam.wealthmangement.blog.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.wealthmangement.blog.model.BlogUser;
import com.sam.wealthmangement.blog.model.dto.AuthRequest;
import com.sam.wealthmangement.blog.model.dto.AuthResponse;
import com.sam.wealthmangement.blog.service.JwtUserDetailsService;
import com.sam.wealthmangement.blog.util.JwtTokenUtil;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecurityResource {

    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil tokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "signIn")
    public HttpEntity<Object> signIn(@RequestBody AuthRequest dto) {
        return new ResponseEntity<>(authorizeUser(dto), HttpStatus.OK);
    }

    @PostMapping(path = "signUp")
    public HttpEntity<Object> signUp(@RequestBody BlogUser dto) {
        return new ResponseEntity<>(userDetailsService.signUp(dto), HttpStatus.OK);
    }

    private AuthResponse authorizeUser(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            final UserDetails blogUser = userDetailsService.loadUserByUsername(authRequest.getUsername());
            return new AuthResponse(tokenUtil.generateToken(blogUser));
        } catch (Exception e) {
            log.warn("Unauthorized Request: {}", e.getMessage());
            throw new BadCredentialsException("Unauthorized Request");
        }
    }

}
