package com.thanthu.patientservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.thanthu.patientservice.converters.PatientDtoToPatientConverter;
import com.thanthu.patientservice.converters.PatientToPatientDtoConverter;
import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.dtos.UpdatePasswordDto;
import com.thanthu.patientservice.models.Patient;
import com.thanthu.patientservice.repositories.PatientRepository;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {
	
	private static final Long ID = 1L;
	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final LocalDate DATE = LocalDate.now();
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private static final String EMAIL = "test@test.com";
	private static final String PASSWORD = "password";
	
	@Spy
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Spy
	private PatientDtoToPatientConverter patientDtoToPatientConverter = new PatientDtoToPatientConverter(passwordEncoder);
	
	@Spy
	private PatientToPatientDtoConverter patientToPatientDtoConverter;

	@Mock
	private PatientRepository patientRepository;
	
	@InjectMocks
	private PatientServiceImpl patientService;
	
	private PatientDto patientDto;
	private Patient patient;
	
	@BeforeEach
	void setUp() throws Exception {
		patientDto = PatientDto.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.email(EMAIL)
				.password(PASSWORD)
				.build();
		
		patient = Patient.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.email(EMAIL)
				.password(passwordEncoder.encode(PASSWORD))
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME).build();
	}

	@Test
	void testCreatePatient() {
		when(patientRepository.save(any())).thenReturn(patient);

		PatientDto createdPatient = patientService.createPatient(patientDto);
		
		verify(patientRepository, times(1)).save(any());
		assertEquals(FIRST_NAME, createdPatient.getFirstName());
		assertEquals(LAST_NAME, createdPatient.getLastName());
		assertEquals(DATE, createdPatient.getDob());
		assertEquals(EMAIL, createdPatient.getEmail());
	}

	@Test
	void testGetPatients() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Patient> patients = ids.stream().map(id -> Patient.builder().id(id).build()).collect(Collectors.toList());
		
		when(patientRepository.findAllById(ids)).thenReturn(patients);
		
		Set<PatientDto> patientsDtos = patientService.getPatients(ids);
		
		verify(patientRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), patientsDtos.size());
	}

	@Test
	void testUpdateName() {
		String updatedFirstName = "John";
		String updateLastName = "Doe";
		
		PatientDto updatedPatientDto = PatientDto.builder()
				.id(ID)
				.firstName(updatedFirstName)
				.lastName(updateLastName)
				.build();
		
		Optional<Patient> patientOptional = Optional.of(patient);
		
		when(patientRepository.findById(ID)).thenReturn(patientOptional);
		when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());
		
		PatientDto savedPatientDto = patientService.updateName(updatedPatientDto);
		
		verify(patientRepository, times(1)).findById(ID);
		verify(patientRepository, times(1)).save(any());
		
		assertEquals(updatedFirstName, savedPatientDto.getFirstName());
		assertEquals(updateLastName, savedPatientDto.getLastName());
		assertEquals(DATE, savedPatientDto.getDob());
		assertEquals(EMAIL, savedPatientDto.getEmail());
	}

	@Test
	void testUpdateDob() {
		LocalDate updatedDob = LocalDate.of(2000, 12, 1);
		patientDto.setId(ID);
		patientDto.setDob(updatedDob);
		
		Optional<Patient> patientOptional = Optional.of(patient);
		
		when(patientRepository.findById(ID)).thenReturn(patientOptional);
		when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());
		
		PatientDto savedPatientDto = patientService.updateDob(patientDto);
		
		verify(patientRepository, times(1)).findById(ID);
		verify(patientRepository, times(1)).save(any());
		
		assertEquals(updatedDob, savedPatientDto.getDob());
		assertEquals(FIRST_NAME, savedPatientDto.getFirstName());
		assertEquals(LAST_NAME, savedPatientDto.getLastName());
		assertEquals(EMAIL, savedPatientDto.getEmail());
	}

	@Test
	void testFindPatientsByName() {
		List<Patient> patients = Arrays.asList(patient);
		
		String searchName = (FIRST_NAME + " " + LAST_NAME).toLowerCase();
		
		when(patientRepository.findAllByName(searchName)).thenReturn(patients);
		
		Set<PatientDto> patientDtos = patientService.findPatientsByName(searchName);
		
		verify(patientRepository, times(1)).findAllByName(searchName);
		
		assertEquals(1, patientDtos.size());
		
	}

	@Test
	void testFindPatientById() {
		when(patientRepository.findById(ID)).thenReturn(Optional.of(patient));
		
		PatientDto savedPatientDto = patientService.findPatientById(ID);
		
		verify(patientRepository, times(1)).findById(ID);
		
		assertEquals(patient.getId(), savedPatientDto.getId());
	}
	
	@Test
	void testUpdateEmail() {
		String updatedEmail = "updatedemail@test.com";
		patientDto.setId(ID);
		patientDto.setEmail(updatedEmail);
		
		Optional<Patient> patientOptional = Optional.of(patient);
		
		when(patientRepository.findById(ID)).thenReturn(patientOptional);
		when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());
		
		PatientDto savedPatientDto = patientService.updateEmail(patientDto);
		
		verify(patientRepository, times(1)).findById(ID);
		verify(patientRepository, times(1)).save(any());
		
		assertEquals(FIRST_NAME, savedPatientDto.getFirstName());
		assertEquals(LAST_NAME, savedPatientDto.getLastName());
		assertEquals(DATE, savedPatientDto.getDob());
		assertEquals(updatedEmail, savedPatientDto.getEmail());
	}
	
	@Test
	void testUpdatePassword() {
		String newPassword = "newPassword";
		UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder().newPassword(newPassword).currentPassword(PASSWORD).build();
		
		Optional<Patient> patientOptional = Optional.of(patient);
		
		when(patientRepository.findById(ID)).thenReturn(patientOptional);
		when(patientRepository.save(any(Patient.class))).then(returnsFirstArg());
		
		PatientDto savedPatientDto = patientService.updatePassword(updatePasswordDto, ID);
		
		verify(patientRepository, times(1)).findById(ID);
		verify(patientRepository, times(1)).save(any());
		
		assertTrue(passwordEncoder.matches(newPassword, patient.getPassword()));
		assertEquals(FIRST_NAME, savedPatientDto.getFirstName());
		assertEquals(LAST_NAME, savedPatientDto.getLastName());
		assertEquals(DATE, savedPatientDto.getDob());
		assertEquals(EMAIL, savedPatientDto.getEmail());
	}

}
