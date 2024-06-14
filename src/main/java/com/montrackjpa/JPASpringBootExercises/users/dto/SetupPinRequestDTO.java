package com.montrackjpa.JPASpringBootExercises.users.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SetupPinRequestDTO {
    @Size(min = 4, max = 4, message = "PIN number must be exactly 4 characters")
    private String pin;

    @Size(min = 4, max = 4, message = "PIN number must be exactly 4 characters")
    private String pinConfirm;
}
