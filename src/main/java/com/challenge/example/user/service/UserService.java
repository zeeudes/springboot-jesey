package com.challenge.example.user.service;

import com.challenge.example.config.security.api.domain.AuthToken;
import com.challenge.example.config.security.api.domain.Login;
import com.challenge.example.config.security.enums.Role;
import com.challenge.example.config.security.service.impl.AuthTokenService;
import com.challenge.example.user.api.dto.UserDTO;
import com.challenge.example.user.entity.User;
import com.challenge.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository repository;
    private AuthTokenService tokenService;
    private AuthenticationManager authManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthTokenService tokenService, AuthenticationManager authManager) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authManager = authManager;
    }

    public AuthToken save(UserDTO dto){
        final User user = UserDTO.toUser(dto);
        final String password = user.getPassword();

        final Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        user.setCreatedAt(ZonedDateTime.now());
        user.setActive(Boolean.TRUE);
        user.setUsername(user.getEmail());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        final User userRegistered = repository.saveAndFlush(user);
        final Login login = new Login(userRegistered.getEmail(), password);

        final Authentication toBeAuth =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        final Authentication auth = this.authManager.authenticate(toBeAuth);
        SecurityContextHolder.getContext().setAuthentication(auth);

        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Set<Role> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.toString()))
                .collect(Collectors.toSet());

        final String token = this.tokenService.getToken(username, authorities);
        final AuthToken authToken = new AuthToken();
        authToken.setToken(token);

        registerLoginTime(userRegistered);


        return authToken;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void registerLoginTime(User user) {
        user.setLastLogin(ZonedDateTime.now());
        this.repository.saveAndFlush(user);
    }
}
