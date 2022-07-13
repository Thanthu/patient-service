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
public class PracticeDto {
	
	private Long id;

	private LocalDateTime createdDateTime;

	private LocalDateTime updateDateTime;

	private String name;

	private OrganizationDto organization;

	@Builder.Default
	private Set<UserDto> users = new HashSet<>();

}
