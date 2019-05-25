package com.globant.auth.authserver.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory {

    private final JwtConfig jwtConfig;

    @Autowired
    public JwtTokenFactory(JwtConfig settings) {
        this.jwtConfig = settings;
    }

    public String createAccessJwtToken(String userName, Collection<? extends GrantedAuthority> authorities) {
        if (StringUtils.isEmpty(userName))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (authorities == null || authorities.isEmpty())
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("scopes", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        DateTime currentTime = new DateTime();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(currentTime.toDate())
                .setExpiration(currentTime.plusMinutes(jwtConfig.getExpiration()).toDate())
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

}
