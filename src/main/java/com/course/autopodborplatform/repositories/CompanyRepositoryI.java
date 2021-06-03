package com.course.autopodborplatform.repositories;

import com.course.autopodborplatform.models.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepositoryI extends CrudRepository<Company, Long> {

}
