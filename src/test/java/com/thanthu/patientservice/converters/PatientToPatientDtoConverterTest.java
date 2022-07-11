package com.thanthu.patientservice.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.enums.RoleName;
import com.thanthu.patientservice.models.Patient;
import com.thanthu.patientservice.models.Role;

@ExtendWith(MockitoExtension.class)
class PatientToPatientDtoConverterTest {
	
	private static final Long ID = 1L;
	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final LocalDate DATE = LocalDate.now();
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private static final String EMAIL = "test@test.com";
	
	private PatientToPatientDtoConverter converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new PatientToPatientDtoConverter();
	}

	@Test
	void testConvert() {
		Role role1 = Role.builder().id(1L).roleName(RoleName.CREATE_PATIENT).build();
		Role role2 = Role.builder().id(2L).roleName(RoleName.PATIENT).build();
		Set<Role> roles = Stream.of(role1, role2).collect(Collectors.toSet());
		
		Patient patient = Patient.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.email(EMAIL)
				.roles(roles)
				.build();
		PatientDto patientDto = converter.convert(patient);
		
		assertEquals(ID, patientDto.getId());
		assertEquals(FIRST_NAME, patientDto.getFirstName());
		assertEquals(LAST_NAME, patientDto.getLastName());
		assertEquals(DATE, patientDto.getDob());
		assertEquals(patient.getCreatedDateTime(), patientDto.getCreatedDateTime());
		assertEquals(patient.getUpdateDateTime(), patientDto.getUpdateDateTime());
		assertEquals(patient.getEmail(), patientDto.getEmail());
		assertEquals(2, patientDto.getRoles().size());
	}
	
	@Test
	void testConvertNull() {
		PatientDto patientDto = converter.convert(null);
		assertNull(patientDto);
	}

}
