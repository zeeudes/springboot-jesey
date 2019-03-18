package com.challenge.example.config.security.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @NotNull(message = "Missing fields")
    private String email;

    @NotNull(message = "Missing fields")
    private String password;
}