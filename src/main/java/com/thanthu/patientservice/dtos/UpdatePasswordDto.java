package com.thanthu.patientservice.dtos;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdatePasswordDto {
	
	@NotBlank
	private String currentPassword;
	
	@NotBlank
	@Length(min = 8, max = 16)
	private String newPassword;

}
