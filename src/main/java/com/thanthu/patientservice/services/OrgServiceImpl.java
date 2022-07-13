package com.thanthu.patientservice.services;

import org.springframework.stereotype.Service;

import com.thanthu.patientservice.dtos.clients.org.PracticeDto;
import com.thanthu.patientservice.dtos.clients.org.UserDto;
import com.thanthu.patientservice.services.clients.OrgClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrgServiceImpl implements OrgService {

	private final OrgClient orgClient;

	@Override
	public PracticeDto findPracticeById(Long id, boolean showUsers, boolean showOrganization) {
		return orgClient.findPracticeById(id, false, true);
	}

	@Override
	public UserDto findUserById(Long id, boolean showPractices, boolean showOrganization) {
		return orgClient.findUserById(id, false, false);
	}

}
