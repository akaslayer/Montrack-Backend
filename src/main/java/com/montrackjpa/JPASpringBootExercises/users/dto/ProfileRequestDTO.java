package com.montrackjpa.JPASpringBootExercises.users.dto;


import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ProfileRequestDTO {
    private String name;

    @Email
    private String email;

    private String profile_photo;
}
