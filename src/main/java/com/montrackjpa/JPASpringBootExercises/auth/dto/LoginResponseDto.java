package com.montrackjpa.JPASpringBootExercises.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginResponseDto {
    private String message;
    private String token;
}
