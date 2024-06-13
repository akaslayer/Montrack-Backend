package com.montrackjpa.JPASpringBootExercises.auth.controller;


import com.montrackjpa.JPASpringBootExercises.users.dto.ForgotPasswordResponseDTO;
import com.montrackjpa.JPASpringBootExercises.auth.dto.LoginRequestDto;
import com.montrackjpa.JPASpringBootExercises.auth.dto.LoginResponseDto;
import com.montrackjpa.JPASpringBootExercises.auth.entity.UserAuth;
import com.montrackjpa.JPASpringBootExercises.auth.service.AuthService;
import com.montrackjpa.JPASpringBootExercises.responses.Response;
import com.montrackjpa.JPASpringBootExercises.users.dto.ForgotPasswordRequestDTO;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
@Log
public class AuthController {


    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto userLogin) throws IllegalAccessException {
        log.info("User login request received for user: " + userLogin.getEmail());
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                userLogin.getEmail(),
                                userLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserAuth userDetails = (UserAuth) authentication.getPrincipal();
        log.info("Token requested for user :" + userDetails.getUsername() + " with roles: " + userDetails.getAuthorities().toArray()[0]);
        String token = authService.generateToken(authentication);

        LoginResponseDto response = new LoginResponseDto();
        response.setMessage("User logged in successfully");
        response.setToken(token);
        return Response.successfulResponse( response.getMessage(), response);
    }
}
