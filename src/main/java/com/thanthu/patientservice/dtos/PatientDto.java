package com.thanthu.patientservice.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.thanthu.patientservice.validation.groups.OnCreatePatient;
import com.thanthu.patientservice.validation.groups.OnUpdatePatientDob;
import com.thanthu.patientservice.validation.groups.OnUpdatePatientName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PatientDto {

	private Long id;

	@NotNull(groups = { OnCreatePatient.class, OnUpdatePatientName.class })
	private String firstName;

	@NotNull(groups = { OnCreatePatient.class, OnUpdatePatientName.class })
	private String lastName;

	@NotNull(groups = { OnCreatePatient.class, OnUpdatePatientDob.class })
	@Past(message = "dob should be past date.")
	private LocalDate dob;

	@CreationTimestamp
	private LocalDateTime createdDateTime;

	@UpdateTimestamp
	private LocalDateTime updateDateTime;

}
