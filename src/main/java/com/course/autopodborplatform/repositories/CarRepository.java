package com.course.autopodborplatform.repositories;

import com.course.autopodborplatform.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository extends JpaRepository<Car, Long> {

}
