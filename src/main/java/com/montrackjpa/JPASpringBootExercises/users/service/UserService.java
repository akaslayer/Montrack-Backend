package com.montrackjpa.JPASpringBootExercises.users.service;

import com.montrackjpa.JPASpringBootExercises.users.dto.ForgotPasswordRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.dto.ProfileRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.dto.RegisterRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.dto.SetupPinRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;

import java.util.List;

public interface UserService {
    User register(RegisterRequestDTO user);

    User findByEmail(String email);

    User findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    User profile();

    User forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO);

    ProfileRequestDTO updateProfile(ProfileRequestDTO profileRequestDTO);

    ProfileRequestDTO getProfile();

    User setPin(SetupPinRequestDTO setupPinRequestDTO);
    
}
