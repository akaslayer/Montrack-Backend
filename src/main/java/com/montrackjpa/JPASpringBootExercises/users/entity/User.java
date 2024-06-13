package com.montrackjpa.JPASpringBootExercises.users.entity;

import com.montrackjpa.JPASpringBootExercises.currencies.entitity.Currency;
import com.montrackjpa.JPASpringBootExercises.goals.Goals;
import com.montrackjpa.JPASpringBootExercises.languages.entity.Languages;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallets_id_gen")
    @SequenceGenerator(name = "wallets_id_gen", sequenceName = "wallets_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Fullname is required")
    @Size(max = 100, message = "Fullname must be less than or equal to 100 characters")
    @Column(name = "fullname")
    private String fullname;


    @Column(name = "salt")
    private String salt;


    @Column(name = "hash")
    private String hash;


    @Column(name = "salt_pin")
    private String saltPin;


    @Column(name = "hash_pin")
    private String hashPin;

    @Column(name = "quotes")
    private String quotes;

    @Column(name = "user_img")
    private String userImg;


    private String activeCurrency;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency  currency;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Languages languages;



    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;



    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;


    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Wallet> wallets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Goals> goals = new LinkedHashSet<>();
}
