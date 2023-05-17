package com.tms.repository;

import com.tms.model.Service;
import com.tms.utils.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    List<Service> findServiceByUserId (Integer id);

    List<Service> findServicesByOrderByRatingDesc();

    List<Service> findServicesBySectionOrderByRatingDesc(SectionType section);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE services_table SET is_deleted = true WHERE id = :id",
            countQuery = "SELECT * FROM services_table WHERE id = :id")
    void deleteService (int id);
}