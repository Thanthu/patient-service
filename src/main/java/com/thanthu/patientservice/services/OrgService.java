package com.thanthu.patientservice.services;

import com.thanthu.patientservice.dtos.clients.org.PracticeDto;
import com.thanthu.patientservice.dtos.clients.org.UserDto;

public interface OrgService {

	public PracticeDto findPracticeById(Long id, boolean showUsers, boolean showOrganization);

	public UserDto findUserById(Long id, boolean showPractices, boolean showOrganization);

}
