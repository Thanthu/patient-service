package com.thanthu.patientservice.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thanthu.patientservice.converters.PatientDtoToPatientConverter;
import com.thanthu.patientservice.converters.PatientToPatientDtoConverter;
import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.exceptions.BadRequestException;
import com.thanthu.patientservice.exceptions.NotFoundException;
import com.thanthu.patientservice.models.Patient;
import com.thanthu.patientservice.repositories.PatientRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

	private final PatientDtoToPatientConverter patientDtoToPatientConverter;

	private final PatientToPatientDtoConverter patientToPatientDtoConverter;

	private final PatientRepository patientRepository;
	
	@Override
	public PatientDto createPatient(PatientDto patientDto) {
		patientRepository.findByEmail(patientDto.getEmail())
		.ifPresent((patient) -> {
			throw new BadRequestException("Patient with email '" + patient.getEmail() + "' already exists."); 
			});
		
		Patient patient = patientDtoToPatientConverter.convert(patientDto);
		Patient savedPatient = savePatient(patient);
		return patientToPatientDtoConverter.convert(savedPatient);
	}

	@Override
	public Set<PatientDto> getPatients(List<Long> ids) {
		List<Patient> patients = patientRepository.findAllById(ids);
		Set<PatientDto> patientDtos = convertPatientsToPatientDtos(patients);
		return patientDtos;
	}

	@Override
	public PatientDto updateName(PatientDto patientDto) {
		Patient patient = findById(patientDto.getId());
		patient.setFirstName(patientDto.getFirstName());
		patient.setLastName(patientDto.getLastName());
		Patient savedPatient = savePatient(patient);
		return patientToPatientDtoConverter.convert(savedPatient);
	}

	@Override
	public PatientDto updateDob(PatientDto patientDto) {
		Patient patient = findById(patientDto.getId());
		patient.setDob(patientDto.getDob());
		Patient savedPatient = savePatient(patient);
		return patientToPatientDtoConverter.convert(savedPatient);
	}

	@Override
	public Set<PatientDto> findPatientsByName(String name) {
		List<Patient> patients = patientRepository.findAllByName(name.toLowerCase().trim());
		Set<PatientDto> patientDtos = convertPatientsToPatientDtos(patients);
		return patientDtos;
	}

	@Override
	public PatientDto findPatientById(Long id) {
		Patient patient = findById(id);
		return patientToPatientDtoConverter.convert(patient);
	}

	private Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}

	private Patient findById(Long id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Patient not found."));
	}

	private Set<PatientDto> convertPatientsToPatientDtos(List<Patient> patients) {
		Set<PatientDto> patientDtos = patients.stream().map((patient) -> patientToPatientDtoConverter.convert(patient))
				.collect(Collectors.toSet());
		return patientDtos;
	}
	
	@Override
	public PatientDto updateEmail(PatientDto patientDto) {
		Patient patient = findById(patientDto.getId());
		patient.setEmail(patientDto.getEmail());
		Patient savedPatient = savePatient(patient);
		return patientToPatientDtoConverter.convert(savedPatient);
	}
	
}
