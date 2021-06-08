package com.course.autopodborplatform.repositories;

import com.course.autopodborplatform.models.Car;
import com.course.autopodborplatform.models.Request;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {

}
