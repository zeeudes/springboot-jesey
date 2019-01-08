package com.challenge.example.config.security.service.impl;

import com.challenge.example.config.security.api.domain.AuthUserInfo;
import com.challenge.example.user.entity.User;
import com.challenge.example.user.service.UserService;
import com.challenge.example.config.security.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserService service;

    @Autowired
    public UserDetailService(final UserService userService) {
        this.service = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = Optional.ofNullable(service.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user '%s' not found.", username)));

        return new AuthUserInfo(
                user.getUsername(),
                user.getPassword(),
                toGrantedAuthorities(user.getRoles()),
                user.isActive()
        );
    }

    private Set<GrantedAuthority> toGrantedAuthorities(Set<Role> authorities) {
        return authorities.stream()
                .map(Role::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}