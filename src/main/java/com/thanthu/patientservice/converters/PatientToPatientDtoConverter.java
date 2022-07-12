package com.thanthu.patientservice.converters;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.models.Patient;

@Component
public class PatientToPatientDtoConverter implements Converter<Patient, PatientDto> {

	@Override
	public PatientDto convert(Patient patient) {
		if(patient == null) return null;
		
		return PatientDto.builder()
				.id(patient.getId())
				.firstName(patient.getFirstName())
				.lastName(patient.getLastName())
				.dob(patient.getDob())
				.createdDateTime(patient.getCreatedDateTime())
				.updateDateTime(patient.getUpdateDateTime())
				.email(patient.getEmail())
				.roles(patient.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toSet()))
				.doctorId(patient.getDoctorId())
				.practiceId(patient.getPracticeId())
				.build();
	}

}
