package com.montrackjpa.JPASpringBootExercises.auth.controller;


import com.montrackjpa.JPASpringBootExercises.users.dto.ForgotPasswordResponseDTO;
import com.montrackjpa.JPASpringBootExercises.auth.dto.LoginRequestDto;
import com.montrackjpa.JPASpringBootExercises.auth.dto.LoginResponseDto;
import com.montrackjpa.JPASpringBootExercises.auth.entity.UserAuth;
import com.montrackjpa.JPASpringBootExercises.auth.service.AuthService;
import com.montrackjpa.JPASpringBootExercises.responses.Response;
import com.montrackjpa.JPASpringBootExercises.users.dto.ForgotPasswordRequestDTO;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

        Cookie cookie = new Cookie("Sid", token);
        cookie.setMaxAge(6000); // Cookie will expire in 1 hour
        cookie.setPath("/");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(Response.successfulResponse( "Login succesfull", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        authService.logout();
        return Response.successfulResponse("User has been succesfully logout");
    }



}
