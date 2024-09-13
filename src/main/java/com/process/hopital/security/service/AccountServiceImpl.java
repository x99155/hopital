package com.process.hopital.security.service;

import com.process.hopital.security.entities.AppRole;
import com.process.hopital.security.entities.AppUser;
import com.process.hopital.security.repo.AppRoleRepository;
import com.process.hopital.security.repo.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String confirmPassword, String email) {
        // Vérifie que l'user n'existe pas déjà et que les mots de passe sont pareils
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) throw new RuntimeException("This user already exists");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Password don't match");

        // Crée un nouvel user
        appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        // enregistre le nouvel user
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole != null) throw new RuntimeException("This role already exists");

        appRole = AppRole.builder()
                .role(role)
                .build();

        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUsername(String username, String role) {
        // on récupère l'utilisateur et le role
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).orElse(null);

        // verifie que le username et le role existent
        if (appUser == null) throw new RuntimeException("User not found");
        if (appRole == null) throw new RuntimeException("Role not found");

        // vérifier si l'utilisateur possède déjà ce rôle
        if (appUser.getRoles().contains(appRole)) {
            throw new RuntimeException("User already has this role");
        }

        // ajouter le role à l'utilisateur
        appUser.getRoles().add(appRole);
        appUserRepository.save(appUser);
    }


    @Override
    public void removeRoleFromUser(String username, String role) {
        // on récupère l'utilisateur et le rôle
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).orElse(null);

        // vérifier que l'utilisateur et le rôle existent
        if (appUser == null) throw new RuntimeException("User not found");
        if (appRole == null) throw new RuntimeException("Role not found");

        // vérifier si l'utilisateur possède ce rôle avant de le supprimer
        if (!appUser.getRoles().contains(appRole)) {
            throw new RuntimeException("User does not have this role");
        }

        // supprimer le rôle de l'utilisateur
        appUser.getRoles().remove(appRole);

        // sauvegarder les modifications
        appUserRepository.save(appUser);
    }


    @Override
    public AppUser loadUserByUsername(String username) {

        return appUserRepository.findByUsername(username);
    }
}
