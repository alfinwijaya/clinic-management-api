package com.restful.clinic.repository;

import com.restful.clinic.entities.MedicRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicRecordRepository extends JpaRepository<MedicRecord, String> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MedicRecord m WHERE m.patient.username = :username")
    boolean existsByPatientUsername(@Param("username") String username);
}
