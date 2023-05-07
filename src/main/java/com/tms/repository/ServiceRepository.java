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

    ArrayList<Service> findServicesByOrderByRatingDesc();

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE services_table SET is_deleted = true WHERE id = :id",
            countQuery = "SELECT * FROM services_table WHERE id = :id")
    void deleteService (int id);

}
