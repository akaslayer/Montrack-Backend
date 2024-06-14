package com.montrackjpa.JPASpringBootExercises.auth.service;

import com.montrackjpa.JPASpringBootExercises.auth.helpers.Claims;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import org.springframework.security.core.Authentication;

public interface AuthService {
    String generateToken(Authentication authentication);

    void logout();
}
