package com.process.hopital;

import com.process.hopital.entities.Patient;
import com.process.hopital.repository.PatientRepository;
import com.process.hopital.security.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@AllArgsConstructor
@SpringBootApplication
public class HopitalApplication {

	// NB: Quand on met en commentaire l'annotation @Bean, la méthode est ignoré au demarrage,
	// c'est equivalent à mettre la totalité de la méthode en commentaire

	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(HopitalApplication.class, args);
	}

	//@Bean
	CommandLineRunner init() {
        return args -> {
			// Ajout des patients
			patientRepository.save(new Patient(null, "Jules", new Date(), true, 300));
			patientRepository.save(new Patient(null, "Marc", new Date(), false, 330));
			patientRepository.save(new Patient(null, "Ariette", new Date(), true, 103));
			patientRepository.save(new Patient(null, "Juana", new Date(), false, 150));
			patientRepository.save(new Patient(null, "Boris", new Date(), false, 280));
		};
    }

	//@Bean
	CommandLineRunner commandLineRunnerJdbc(JdbcUserDetailsManager jdbcUserDetailsManager){
		PasswordEncoder passwordEncoder = passwordEncoder();

		return args -> {
			UserDetails u1= jdbcUserDetailsManager.loadUserByUsername("user1");
			UserDetails u2= jdbcUserDetailsManager.loadUserByUsername("user2");
			UserDetails u3= jdbcUserDetailsManager.loadUserByUsername("admin");

			if (u1 == null)
				jdbcUserDetailsManager.createUser(
						User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build()
				);
			if (u2 == null)
				jdbcUserDetailsManager.createUser(
						User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build()
				);
			if (u3 == null)
				jdbcUserDetailsManager.createUser(
						User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
				);
		};
	}

	//@Bean
	CommandLineRunner commandLineRunnerUserdDetailsService(AccountService accountService) {
		return args -> {
			// Création des roles
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");

			// Création des utilisateurs
			accountService.addNewUser("user1", "1234", "1234", "user1@test.com");
			accountService.addNewUser("user2", "1234", "1234", "user2@test.com");
			accountService.addNewUser("admin", "1234", "1234", "admin@test.com");

			// Affecter les roles aux utilisateurs
			accountService.addRoleToUsername("user1", "USER");
			accountService.addRoleToUsername("user2", "USER");
			accountService.addRoleToUsername("admin", "USER");
			accountService.addRoleToUsername("admin", "ADMIN");
		};
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
