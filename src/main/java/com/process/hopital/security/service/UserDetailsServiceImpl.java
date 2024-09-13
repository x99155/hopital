package com.process.hopital.security.service;

import com.process.hopital.security.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);

        if (appUser == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        // Assurez-vous que vous extrayez les rôles corrects ici
        String[] roles = appUser.getRoles().stream()
                .map(role -> role.getRole()) // Assurez-vous que `getRole()` retourne le nom du rôle
                .toArray(String[]::new);

        UserDetails userDetails = User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .build();

        return userDetails;
    }
}
