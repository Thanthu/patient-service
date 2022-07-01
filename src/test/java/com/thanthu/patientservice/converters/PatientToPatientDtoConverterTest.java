package com.thanthu.patientservice.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.models.Patient;

@ExtendWith(MockitoExtension.class)
class PatientToPatientDtoConverterTest {
	
	private static final Long ID = 1L;
	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final LocalDate DATE = LocalDate.now();
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	
	private PatientToPatientDtoConverter converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new PatientToPatientDtoConverter();
	}

	@Test
	void testConvert() {
		Patient patient = Patient.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
		PatientDto patientDto = converter.convert(patient);
		
		assertEquals(ID, patientDto.getId());
		assertEquals(FIRST_NAME, patientDto.getFirstName());
		assertEquals(LAST_NAME, patientDto.getLastName());
		assertEquals(DATE, patientDto.getDob());
		assertEquals(patient.getCreatedDateTime(), patientDto.getCreatedDateTime());
		assertEquals(patient.getUpdateDateTime(), patientDto.getUpdateDateTime());
		
	}
	
	@Test
	void testConvertNull() {
		PatientDto patientDto = converter.convert(null);
		assertNull(patientDto);
	}

}
