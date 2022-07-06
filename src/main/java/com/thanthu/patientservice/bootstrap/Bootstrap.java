package com.thanthu.patientservice.bootstrap;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.thanthu.patientservice.enums.RoleName;
import com.thanthu.patientservice.models.Patient;
import com.thanthu.patientservice.models.Role;
import com.thanthu.patientservice.repositories.PatientRepository;
import com.thanthu.patientservice.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
	
	private final PatientRepository patientRepository;
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		
		log.info("Loading bootrap data started");
		
		Role patientRole = Role.builder().roleName(RoleName.PATIENT).build();
		patientRole = roleRepository.save(patientRole);
		
		Role createPatientRole = Role.builder().roleName(RoleName.CREATE_PATIENT).build();
		createPatientRole = roleRepository.save(createPatientRole);
		
		Patient patientWithCreatePatient = Patient.builder()
				.firstName("Create")
				.lastName("Patient")
				.dob(LocalDate.of(2000, 12, 12))
				.email("thanthu@test.com")
				.password(passwordEncoder.encode("password"))
				.roles(Stream.of(createPatientRole).collect(Collectors.toSet()))
				.build();
		
		patientRepository.save(patientWithCreatePatient);
		
		Patient patient = Patient.builder()
				.firstName("John")
				.lastName("Doe")
				.dob(LocalDate.of(2000, 12, 12))
				.email("johndoe@test.com")
				.password(passwordEncoder.encode("johnspassword"))
				.roles(Stream.of(patientRole).collect(Collectors.toSet()))
				.build();
		
		patientRepository.save(patient);
		
		log.info("Loading bootrap data completed");
		
	}

}
