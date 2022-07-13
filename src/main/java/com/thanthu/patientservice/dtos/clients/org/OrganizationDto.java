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
public class OrganizationDto {
	
	private Long id;
	
	private LocalDateTime createdDateTime;
	
	private LocalDateTime updateDateTime;
	
	private String name;
	
	@Builder.Default
	private Set<PracticeDto> practices = new HashSet<>();

}
