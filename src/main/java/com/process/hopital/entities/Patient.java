package com.process.hopital.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
* @Data: génère les getters & setters
 */

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty // annotation de la dépendance validation
    @Size(min = 4, max = 40) // le nbre de caractère du nom doit-etre entre 4 et 40
    private String nom;
    @Column(name = "date_de_naissance")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd") // le format auquel on veut récupérer la date choisi par l'utilisateur
    private Date dateDeNaissance;
    private boolean malade;
    @DecimalMin("100") // on accepte pas un score < 100
    private int score;
}
