package com.tms.repository;

import com.tms.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    Optional<ArrayList<Service>> findServiceBySection (String section);

    Optional<ArrayList<Service>> findServiceByUserId (Integer id);

    @Modifying
    @Query(value = "UPDATE Service s SET s.isDeleted =true WHERE s.id = :id")
    void deleteService (int id);

}
