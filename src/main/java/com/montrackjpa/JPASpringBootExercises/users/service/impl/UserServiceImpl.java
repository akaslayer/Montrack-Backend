package com.montrackjpa.JPASpringBootExercises.users.service.impl;

import com.montrackjpa.JPASpringBootExercises.exceptions.InputException;
import com.montrackjpa.JPASpringBootExercises.users.dto.ForgotPasswordRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.dto.ProfileRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.dto.RegisterRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import com.montrackjpa.JPASpringBootExercises.users.repository.UserRepository;
import com.montrackjpa.JPASpringBootExercises.users.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO){
        User userData = userRepository.findByEmail(forgotPasswordRequestDTO.getEmail()).orElseThrow(() -> new InputException("User not found"));
        var password = passwordEncoder.encode(forgotPasswordRequestDTO.getPassword());
        userData.setHash(password);
        userRepository.save(userData);
        return userData;
    }

    @Override
    public ProfileRequestDTO updateProfile(ProfileRequestDTO profileRequestDTO) {
        User userData = userRepository.findByEmail(profileRequestDTO.getEmail()).orElseThrow(() -> new InputException("User not found"));
        userData.setEmail(profileRequestDTO.getEmail());
        userData.setUserImg(profileRequestDTO.getProfile_photo());
        userData.setFullname(profileRequestDTO.getName());
        userRepository.save(userData);
        return profileRequestDTO;
    }

    public User register(RegisterRequestDTO user) {
        boolean exists = userRepository.findAll()
                .stream()
                .anyMatch(data -> data.getEmail().equals(user.getEmail()));
        if(exists){
            throw new InputException("Email already exist");
        }


        if(!user.getPasswordConfirmation().equals(user.getPassword())){
            throw new InputException("Password and confirmation password doesn't match");
        }
        User newUser = user.toEntity();
        var password = passwordEncoder.encode(user.getPassword());
        newUser.setHash(password);
        return userRepository.save(newUser);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new InputException("User not found"));
    }


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new InputException("User not found"));
    }


    public List<User> findAll() {
        return null;
    }


    public void deleteById(Long id) {

    }

    public User profile() {
        return null;
    }



}
