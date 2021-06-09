package com.course.autopodborplatform.repositories;

import com.course.autopodborplatform.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("select r from Request r where r.status.ID=0 and r.company.id=:id")
    List<Request> getCompaniesRequestByDefaultStatus(@Param("id") long id);
    @Query("select r from Request r where r.status.ID=1 and r.company.id=:id")
    List<Request> getCompaniesRequestByInProcessStatus(@Param("id") long id);
    @Query("select r from Request r where r.status.ID=3 and r.company.id=:id")
    List<Request> getCompaniesRequestByFinishStatus(@Param("id") long id);
}
