package com.process.hopital;

import com.process.hopital.entities.Patient;
import com.process.hopital.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@AllArgsConstructor
@SpringBootApplication
public class HopitalApplication implements CommandLineRunner {

	private PatientRepository patientRepository;

	/*
	* Voici trois méthodes pour créer un nouveau patient
	 */
	@Override
	public void run(String... args) throws Exception {
		patientRepository.save(new Patient(null, "Jules", new Date(), true, 300));
		patientRepository.save(new Patient(null, "Marc", new Date(), false, 330));
		patientRepository.save(new Patient(null, "Ariette", new Date(), true, 103));
		patientRepository.save(new Patient(null, "Juana", new Date(), false, 150));
		patientRepository.save(new Patient(null, "Boris", new Date(), false, 280));


		/*
		// Méthode 1:
		Patient patient = new Patient();
		patient.setId(null);
		patient.setNom("Pierre");
		patient.setDateDeNaissance(new Date());
		patient.setMalade(true);
		patient.setScore(3);

		// Méthode 2:
		Patient patient2 = new Patient(null, "Thomas", new Date(), false, 23);

		// Méthode 3: En utilisant Builder
		Patient patient3 = Patient.builder()
				.id(null)
				.nom("Marie")
				.dateDeNaissance(new Date())
				.malade(false)
				.score(3)
				.build();

		patientRepository.save(patient);

		 */

	}

	public static void main(String[] args) {
		SpringApplication.run(HopitalApplication.class, args);
	}

}
