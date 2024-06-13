package com.montrackjpa.JPASpringBootExercises.languages.entity;


import com.montrackjpa.JPASpringBootExercises.users.entity.User;
import com.montrackjpa.JPASpringBootExercises.wallets.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;


@Data
@Table(name="languages")
@Entity
public class Languages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "languages_id_gen")
    @SequenceGenerator(name = "languages_id_gen", sequenceName = "languages_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;

    @Column( name = "language_name")
    private String languageName;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;


    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "languages",cascade = CascadeType.ALL)
    private Set<User> users = new LinkedHashSet<>();
}
