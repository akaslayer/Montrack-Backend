package com.montrackjpa.JPASpringBootExercises.users.controller;


import com.montrackjpa.JPASpringBootExercises.responses.Response;
import com.montrackjpa.JPASpringBootExercises.users.dto.ForgotPasswordRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.dto.ForgotPasswordResponseDTO;
import com.montrackjpa.JPASpringBootExercises.users.dto.ProfileRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.dto.RegisterRequestDTO;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import com.montrackjpa.JPASpringBootExercises.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
    }

    

    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@RequestBody RegisterRequestDTO registerRequestDto) {
        return Response.successfulResponse("User registered successfully", userService.register(registerRequestDto));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<Response<ForgotPasswordResponseDTO>> forgotPassword(@RequestBody @Validated ForgotPasswordRequestDTO forgotPasswordRequestDTO){
        ForgotPasswordResponseDTO response = new ForgotPasswordResponseDTO();
        User userData = userService.forgotPassword(forgotPasswordRequestDTO);

        response.setEmail(userData.getEmail());
        response.setPassword(userData.getHash());
        return Response.successfulResponse("User password has been changed", response);
    }


    @PostMapping("/profile")
    public ResponseEntity<?> profile(@RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileRequestDTO profileData = userService.updateProfile(profileRequestDTO);
        return Response.successfulResponse("Profile data has been changed", profileData);
    }
}
