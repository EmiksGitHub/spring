package com.course.autopodborplatform.repositories;

import com.course.autopodborplatform.models.Status;
import com.course.autopodborplatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
}