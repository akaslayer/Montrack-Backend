package com.montrackjpa.JPASpringBootExercises.users.dto;


import com.montrackjpa.JPASpringBootExercises.currencies.entitity.Currency;
import com.montrackjpa.JPASpringBootExercises.languages.entity.Languages;
import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;


    @NotBlank(message = "Password confirmation is required")
    private String passwordConfirmation;

    @NotNull
    @Min(1)
    private int activeCurrency;

    public User toEntity() {
        User user = new User();
        user.setFullname(name);
        user.setEmail(email);
        user.setHash(password);
        Languages languagesData = new Languages();
        languagesData.setId(activeCurrency);
        user.setLanguages(languagesData);
        Currency currency = new Currency();
        currency.setId(activeCurrency);
        user.setCurrency(currency);
        return user;
    }
}
