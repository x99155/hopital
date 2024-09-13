package com.process.hopital.security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class AppRole {
    @Id
    private String role;
}
