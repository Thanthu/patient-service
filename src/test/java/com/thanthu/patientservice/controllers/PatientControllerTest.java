package com.thanthu.patientservice.controllers;

import static com.thanthu.patientservice.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.thanthu.patientservice.dtos.PatientDto;
import com.thanthu.patientservice.exceptions.NotFoundException;
import com.thanthu.patientservice.services.PatientService;

@WebMvcTest(PatientController.class)
class PatientControllerTest {
	
	private static final String API_BASE_URL = "/api/v1/patient";
	private static final Long ID = 1L;
	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final LocalDate DATE = LocalDate.of(1900, 1, 1);
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private PatientDto patientDto;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PatientService patientService;
	
	@BeforeEach
	void setUp() throws Exception {
		patientDto = PatientDto.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE).build();
		
	}

	@Test
	void testCreatePatient() throws Exception {
		PatientDto createdPatientDto = PatientDto.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
		
		when(patientService.createPatient(any(PatientDto.class))).thenReturn(createdPatientDto);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto))
				).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(ID.toString()))
				.andExpect(jsonPath("$.firstName").value(FIRST_NAME))
				.andExpect(jsonPath("$.lastName").value(LAST_NAME))
				.andExpect(jsonPath("$.dob").exists())
				.andExpect(jsonPath("$.createdDateTime").exists())
				.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(patientService, times(1)).createPatient(any());
	}
	
	@Test
	void testCreatePatientFirstNameNull() throws Exception {
		patientDto.setFirstName(null);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto))
				).andExpect(status().isBadRequest());
		
		verify(patientService, times(0)).createPatient(any());
	}
	
	@Test
	void testCreatePatientLastNameNull() throws Exception {
		patientDto.setLastName(null);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto))
				).andExpect(status().isBadRequest());
		
		verify(patientService, times(0)).createPatient(any());
	}
	
	@Test
	void testCreatePatientDobNull() throws Exception {
		patientDto.setDob(null);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto))
				).andExpect(status().isBadRequest());
		
		verify(patientService, times(0)).createPatient(any());
	}

	@Test
	void testGetPatients() throws Exception {
		Set<PatientDto> set = new HashSet<>();
		set.add(PatientDto.builder()
				.id(ID)
				.build());
		set.add(PatientDto.builder()
				.id(2L)
				.build());
		
		when(patientService.getPatients(any())).thenReturn(set);
		
		mockMvc.perform(get(API_BASE_URL + "/list-by-ids")
				.contentType(MediaType.APPLICATION_JSON)
				.param("ids", "1", "2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[:2].id").value(containsInAnyOrder(1, 2)));
		
		verify(patientService, times(1)).getPatients(any());
	}

	@Test
	void testUpdateName() throws Exception {
		PatientDto updatedPatientDto = PatientDto.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
		
		when(patientService.updateName(any())).thenReturn(updatedPatientDto);
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID.toString()))
		.andExpect(jsonPath("$.firstName").value(FIRST_NAME))
		.andExpect(jsonPath("$.lastName").value(LAST_NAME))
		.andExpect(jsonPath("$.dob").exists())
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(patientService, times(1)).updateName(any());
	}
	
	@Test
	void testUpdateNameNotFound() throws Exception {
		when(patientService.updateName(any())).thenThrow(new NotFoundException(""));
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto)))
		.andExpect(status().isNotFound());
		
		verify(patientService, times(1)).updateName(any());
	}
	
	@Test
	void testUpdateNameFirstNameNull() throws Exception {
		patientDto.setFirstName(null);
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto)))
		.andExpect(status().isBadRequest());
		
		verify(patientService, times(0)).updateName(any());
	}
	
	@Test
	void testUpdateNameLastNameNull() throws Exception {
		patientDto.setLastName(null);
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto)))
		.andExpect(status().isBadRequest());
		
		verify(patientService, times(0)).updateName(any());
	}

	@Test
	void testUpdateDob() throws Exception {
		PatientDto updatedPatientDto = PatientDto.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
		
		when(patientService.updateDob(any())).thenReturn(updatedPatientDto);
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/dob")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID.toString()))
		.andExpect(jsonPath("$.firstName").value(FIRST_NAME))
		.andExpect(jsonPath("$.lastName").value(LAST_NAME))
		.andExpect(jsonPath("$.dob").exists())
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(patientService, times(1)).updateDob(any());
	}
	
	@Test
	void testUpdateDobNotFound() throws Exception {
		when(patientService.updateDob(any())).thenThrow(new NotFoundException(""));
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/dob")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto)))
		.andExpect(status().isNotFound());
		
		verify(patientService, times(1)).updateDob(any());
	}
	
	@Test
	void testUpdateDobNull() throws Exception {
		patientDto.setDob(null);
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/dob")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(patientDto)))
		.andExpect(status().isBadRequest());
		
		verify(patientService, times(0)).updateDob(any());
	}

	@Test
	void testFindPatientsByName() throws Exception {
		Set<PatientDto> set = new HashSet<>();
		set.add(PatientDto.builder()
				.id(ID)
				.build());
		set.add(PatientDto.builder()
				.id(2L)
				.build());
		
		when(patientService.findPatientsByName(any())).thenReturn(set);
		
		mockMvc.perform(get(API_BASE_URL + "/list-by-name")
				.contentType(MediaType.APPLICATION_JSON)
				.param("name", "Thanthu"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[:2].id").value(containsInAnyOrder(1, 2)));
		
		verify(patientService, times(1)).findPatientsByName(any());
	}

	@Test
	void testFindPatientById() throws Exception {
		PatientDto createdPatientDto = PatientDto.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dob(DATE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
		
		when(patientService.findPatientById(ID)).thenReturn(createdPatientDto);
		
		mockMvc.perform(get(API_BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(ID.toString()))
				.andExpect(jsonPath("$.firstName").value(FIRST_NAME))
				.andExpect(jsonPath("$.lastName").value(LAST_NAME))
				.andExpect(jsonPath("$.dob").exists())
				.andExpect(jsonPath("$.createdDateTime").exists())
				.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(patientService, times(1)).findPatientById(ID);
	}
	
	@Test
	void testFindPatientByIdNotFound() throws Exception {
		when(patientService.findPatientById(any())).thenThrow(new NotFoundException(""));
		
		mockMvc.perform(get(API_BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isNotFound());
		
		verify(patientService, times(1)).findPatientById(ID);
	}

}
