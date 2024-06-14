package com.montrackjpa.JPASpringBootExercises.auth.service.impl;

import com.montrackjpa.JPASpringBootExercises.auth.helpers.Claims;
import com.montrackjpa.JPASpringBootExercises.auth.repository.AuthRedisRepository;
import com.montrackjpa.JPASpringBootExercises.auth.service.AuthService;
import com.montrackjpa.JPASpringBootExercises.exceptions.InputException;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import com.montrackjpa.JPASpringBootExercises.users.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
@Log
public class AuthServiceImpl implements AuthService {


    private final JwtEncoder jwtEncoder;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthRedisRepository authRedisRepository;

    public AuthServiceImpl(JwtEncoder jwtEncoder, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthRedisRepository authRedisRepository) {
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authRedisRepository = authRedisRepository;
    }

    @Override
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var existingKey = authRedisRepository.getJwtKey(authentication.getName());
        if(existingKey != null){
            log.info("Token already exists for user: " + authentication.getName());
            return existingKey;
        }

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        if(authRedisRepository.isKeyBlacklisted(jwt)){
            throw new InputException("JWT Token has already been blacklisted");
        }
        authRedisRepository.saveJwtKey(authentication.getName(),jwt);
        return jwt;
    }

    @Override
    public void logout() {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");
        String jwt = authRedisRepository.getJwtKey(email);
        authRedisRepository.blackListJwt(email,jwt);
        authRedisRepository.deleteJwtKey(email);

    }
}
