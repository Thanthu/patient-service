package com.thanthu.patientservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.thanthu.patientservice.models.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
