package com.process.hopital.security.service;

import com.process.hopital.security.entities.AppRole;
import com.process.hopital.security.entities.AppUser;

public interface AccountService {

    // Ajouter d'un utilisateur
    AppUser addNewUser(String username, String password, String confirmPassword, String email);

    // Ajouter d'un role
    AppRole addNewRole(String role);

    // Attribuer un role à un utilisateur
    void addRoleToUsername(String username, String role);

    // Retirer un role à un utilisateur
    void removeRoleFromUser(String username, String role);

    AppUser loadUserByUsername(String username);
}
