package com.challenge.example.config.security.service;

import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import com.challenge.example.config.security.enums.Role;

import java.util.Set;

public interface IAuthTokenService {

    String getToken(String username, Set<Role> authorities);

    AuthTokenInfo parseToken(String token);

    String refreshAuthToken(AuthTokenInfo currentAuthTokenInfo);
}
