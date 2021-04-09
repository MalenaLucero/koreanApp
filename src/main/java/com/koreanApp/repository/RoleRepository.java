package com.koreanApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.koreanApp.entity.Role;
import com.koreanApp.enums.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleEnum name);
}
