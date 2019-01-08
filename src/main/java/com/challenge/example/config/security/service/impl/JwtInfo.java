package com.challenge.example.config.security.service.impl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtInfo {

    @Value("${authentication.jwt.secret}")
    private String secret;

    @Value("${authentication.jwt.clockSkew}")
    private Long clockSkew;

    @Value("${authentication.jwt.audience}")
    private String audience;

    @Value("${authentication.jwt.issuer}")
    private String issuer;

    @Value("${authentication.jwt.claimNames.authorities}")
    private String authoritiesClaimName;

    @Value("${authentication.jwt.claimNames.refreshCount}")
    private String refreshCountClaimName;

    @Value("${authentication.jwt.claimNames.refreshLimit}")
    private String refreshLimitClaimName;
}
