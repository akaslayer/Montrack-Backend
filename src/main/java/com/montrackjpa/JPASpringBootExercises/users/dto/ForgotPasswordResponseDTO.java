package com.montrackjpa.JPASpringBootExercises.users.dto;


import lombok.Data;

@Data
public class ForgotPasswordResponseDTO {
    private String email;
    private String password;
}
