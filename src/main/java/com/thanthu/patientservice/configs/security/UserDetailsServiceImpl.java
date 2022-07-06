package com.thanthu.patientservice.configs.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thanthu.patientservice.models.Patient;
import com.thanthu.patientservice.repositories.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final PatientRepository patientRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Patient patient = patientRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with email " + username));
		return new User(username, patient.getPassword(), patient.getRoles());
	}

}
