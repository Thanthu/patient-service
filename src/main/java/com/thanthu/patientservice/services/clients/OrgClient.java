package com.thanthu.patientservice.services.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.thanthu.patientservice.dtos.clients.org.PracticeDto;
import com.thanthu.patientservice.dtos.clients.org.UserDto;

@FeignClient("org")
public interface OrgClient {

	@GetMapping("/api/v1/practice/{id}")
	public PracticeDto findPracticeById(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean showUsers,
			@RequestParam(defaultValue = "false") boolean showOrganization);

	@GetMapping("/api/v1/user/{id}")
	public UserDto findUserById(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean showPractices,
			@RequestParam(defaultValue = "false") boolean showOrganization);

}
