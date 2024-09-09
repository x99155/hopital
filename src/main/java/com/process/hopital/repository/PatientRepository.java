package com.process.hopital.repository;

import com.process.hopital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // methode qui permet de faire la recherche
    Page<Patient> findByNomContainsIgnoreCase(String keyword, Pageable pageable);
}
