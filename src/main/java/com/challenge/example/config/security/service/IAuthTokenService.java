package com.challenge.example.config.security.service;

import com.challenge.example.config.security.api.domain.AuthTokenInfo;
import com.challenge.example.config.security.enums.Role;

import java.util.Set;

public interface IAuthTokenService {

    String getToken(final String username, final Set<Role> authorities);

    AuthTokenInfo parseToken(final String token);

    String refreshAuthToken(final AuthTokenInfo currentAuthTokenInfo);
}
