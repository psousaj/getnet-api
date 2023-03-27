package com.psousaj.getnetapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psousaj.getnetapi.user.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
