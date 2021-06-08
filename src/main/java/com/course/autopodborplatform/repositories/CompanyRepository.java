package com.course.autopodborplatform.repositories;

import com.course.autopodborplatform.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Long> {
}
