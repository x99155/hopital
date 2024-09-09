package com.process.hopital.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private String nom;
    @Column(name = "date_de_naissance")
    private Date dateDeNaissance;
    private boolean malade;
    private int score;
}
