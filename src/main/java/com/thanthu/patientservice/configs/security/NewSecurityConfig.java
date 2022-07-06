package com.thanthu.patientservice.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.thanthu.patientservice.enums.RoleName;

@Configuration
public class NewSecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		http.csrf().disable();
		http.httpBasic();
		http.authorizeRequests()
		.mvcMatchers(HttpMethod.POST, "/api/v1/patient").hasAuthority(RoleName.CREATE_PATIENT.name())
		.mvcMatchers("/api/v1/patient/**").hasAuthority(RoleName.PATIENT.name());
        return http.build();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
