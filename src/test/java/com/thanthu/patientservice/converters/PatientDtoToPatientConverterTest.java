package com.thanthu.patientservice.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.models.Patient;

@ExtendWith(MockitoExtension.class)
class PatientDtoToPatientConverterTest {

	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final LocalDate DATE = LocalDate.now();
	private static final String EMAIL = "test@test.com";
	
	private PatientDtoToPatientConverter converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new PatientDtoToPatientConverter();
	}

	@Test
	void testConvert() {
		PatientDto patientDto = PatientDto.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.email(EMAIL)
				.build();
		Patient patient = converter.convert(patientDto);
		
		assertNull(patient.getId());
		assertEquals(FIRST_NAME, patient.getFirstName());
		assertEquals(LAST_NAME, patient.getLastName());
		assertEquals(DATE, patient.getDob());
		assertNull(patient.getCreatedDateTime());
		assertNull(patient.getUpdateDateTime());
		assertEquals(patientDto.getEmail(), patient.getEmail());
	}
	
	@Test
	void testConvertNull() {
		PatientDto patientDto = null;
		Patient patient = converter.convert(patientDto);
		assertNull(patient);
	}

}
