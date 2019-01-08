package com.challenge.example.user.service;

import com.challenge.example.config.security.api.domain.AuthToken;
import com.challenge.example.config.security.api.domain.Login;
import com.challenge.example.config.security.enums.Role;
import com.challenge.example.user.api.dto.UserDTO;
import com.challenge.example.user.entity.User;
import com.challenge.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Value("${server.port}")
    private Integer port;
    @Value("${app.uri.base}")
    private String uriBase;

    private PasswordEncoder passwordEncoder;
    private UserRepository repository;
    private Client client;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.client = ClientBuilder.newClient();
        this.passwordEncoder = passwordEncoder;
    }

    public AuthToken save(UserDTO dto){
        final User user = UserDTO.toUser(dto);

        final Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        user.setCreatedAt(ZonedDateTime.now());
        user.setActive(Boolean.TRUE);
        user.setUsername(user.getEmail());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        final User userRegistered = repository.saveAndFlush(user);
        final Login login = new Login(userRegistered.getEmail(), "hunter2");

        final Response response = client.target(this.uriBase.concat(this.port.toString())).path("/signin").request()
                .post(Entity.entity(login, MediaType.APPLICATION_JSON));

        return response.readEntity(AuthToken.class);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void registerLoginTime(User user) {
        user.setLastLogin(ZonedDateTime.now());
        this.repository.saveAndFlush(user);
    }
}
