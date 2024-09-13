package com.process.hopital.security.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles;
}
