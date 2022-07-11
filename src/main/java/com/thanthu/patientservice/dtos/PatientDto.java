package com.thanthu.patientservice.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.thanthu.patientservice.enums.RoleName;
import com.thanthu.patientservice.validation.groups.OnCreatePatient;
import com.thanthu.patientservice.validation.groups.OnUpdatePatientDob;
import com.thanthu.patientservice.validation.groups.OnUpdatePatientEmail;
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

	private LocalDateTime createdDateTime;

	private LocalDateTime updateDateTime;

	@NotBlank(groups = { OnCreatePatient.class, OnUpdatePatientName.class })
	private String firstName;

	@NotBlank(groups = { OnCreatePatient.class, OnUpdatePatientName.class })
	private String lastName;

	@NotNull(groups = { OnCreatePatient.class, OnUpdatePatientDob.class })
	@Past(message = "dob should be past date.")
	private LocalDate dob;

	@NotBlank(groups = { OnCreatePatient.class, OnUpdatePatientEmail.class })
	@Email
	private String email;

	private Set<RoleName> roles = new HashSet<>();

}
