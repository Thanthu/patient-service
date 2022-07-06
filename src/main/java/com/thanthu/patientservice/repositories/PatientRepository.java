package com.thanthu.patientservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thanthu.patientservice.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>  {

	@Query("select p from Patient p where lower(concat(p.firstName, ' ', p.lastName)) like %:name%")
	public List<Patient> findAllByName(@Param("name") String name);
	
	public Optional<Patient> findByEmail(String email);

}
