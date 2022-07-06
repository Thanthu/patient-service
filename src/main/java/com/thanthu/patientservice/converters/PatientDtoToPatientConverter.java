package com.thanthu.patientservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.models.Patient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PatientDtoToPatientConverter implements Converter<PatientDto, Patient> {
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Patient convert(PatientDto patientDto) {
		if (patientDto == null) return null;
		
		return Patient.builder()
				.firstName(patientDto.getFirstName())
				.lastName(patientDto.getLastName())
				.dob(patientDto.getDob())
				.email(patientDto.getEmail())
				.password(passwordEncoder.encode(patientDto.getPassword()))
				.build();
	}

}
