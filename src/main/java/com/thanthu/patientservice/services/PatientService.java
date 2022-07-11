package com.thanthu.patientservice.services;

import java.util.List;
import java.util.Set;

import com.thanthu.patientservice.dtos.PatientDto;

public interface PatientService {

	public PatientDto createPatient(PatientDto patientDto);

	public Set<PatientDto> getPatients(List<Long> ids);

	public PatientDto updateName(PatientDto patientDto);
	
	public PatientDto updateDob(PatientDto patientDto);

	public Set<PatientDto> findPatientsByName(String name);

	public PatientDto findPatientById(Long id);

	public PatientDto updateEmail(PatientDto patientDto);

}
