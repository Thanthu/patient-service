package com.thanthu.patientservice.dtos.clients.org;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserDto {

	private Long id;

	private String firstName;

	private String lastName;

	private LocalDateTime createdDateTime;

	private LocalDateTime updateDateTime;

	@Builder.Default
	private Set<PracticeDto> practices = new HashSet<>();
	
	private String type;

}
