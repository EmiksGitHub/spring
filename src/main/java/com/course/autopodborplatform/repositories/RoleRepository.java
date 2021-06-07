package com.course.autopodborplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.course.autopodborplatform.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
