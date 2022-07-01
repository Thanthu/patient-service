package com.thanthu.patientservice.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.services.PatientService;
import com.thanthu.patientservice.validation.groups.OnCreatePatient;
import com.thanthu.patientservice.validation.groups.OnUpdatePatientDob;
import com.thanthu.patientservice.validation.groups.OnUpdatePatientName;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
@RestController
@Validated
public class PatientController {

	private final PatientService patientService;

	@PostMapping
	@Validated(OnCreatePatient.class)
	public PatientDto createPatient(@Valid @RequestBody PatientDto patientDto) {
		return patientService.createPatient(patientDto);
	}

	@GetMapping("/list-by-ids")
	public Set<PatientDto> getPatients(@RequestParam(required = true) List<Long> ids) {
		return patientService.getPatients(ids);
	}

	@PutMapping("/{id}/name")
	@Validated(OnUpdatePatientName.class)
	public PatientDto updateName(@Valid @RequestBody PatientDto patientDto, @PathVariable Long id) {
		patientDto.setId(id);
		return patientService.updateName(patientDto);
	}

	@PutMapping("/{id}/dob")
	@Validated(OnUpdatePatientDob.class)
	public PatientDto updateDob(@Valid @RequestBody PatientDto patientDto, @PathVariable Long id) {
		patientDto.setId(id);
		return patientService.updateDob(patientDto);
	}

	@GetMapping("/list-by-name")
	public Set<PatientDto> findPatientsByName(@RequestParam(required = true) String name) {
		return patientService.findPatientsByName(name);
	}

	@GetMapping("/{id}")
	public PatientDto findPatientById(@PathVariable Long id) {
		return patientService.findPatientById(id);
	}

}
