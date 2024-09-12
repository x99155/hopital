package com.process.hopital.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor // constructeur avec argument pour l'injection des dépendaces
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    /*
        J'utilise ici la stratégie InMemoryAuthentification,

        les identifiant et les roles des utilisateurs sont stockées en mémoires et pas dans une bdd
     */
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){

        return new InMemoryUserDetailsManager(
                // spécifie les utilisateurs qui ont le droit d'accéder à l'app
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
        );
    }

    /*
        client(envoie requete) -> SecurityFilterChain(si ok) -> dispatcherServlet -> etc
     */

    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(Customizer.withDefaults()) // formulaire d'authentification par défaut
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // Toute autre requête nécessite une authentification
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/notAuthorized") // Page d'accès refusé
                )
                .build(); // Finaliser la chaîne de filtres de sécurité
    }
    */



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(form -> form
                        .loginPage("/login")  // Page de login personnalisée si nécessaire
                        .defaultSuccessUrl("/", true)  // Redirection après authentification
                        .permitAll()
                )
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/webjars/**", "/h2-console/**").permitAll() // autorise l'accès au ressources
                        .anyRequest().authenticated() // Toute autre requête nécessite une authentification
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/notAuthorized") // Page d'accès refusé
                )
                .build();
    }


}
