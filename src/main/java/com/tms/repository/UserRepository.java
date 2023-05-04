package com.tms.repository;

import com.tms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users_table SET isDeleted = true WHERE id = :id", countQuery = "SELECT * FROM users_table WHERE id = :id")
    void deleteUser (int id);

    @Modifying
    @Query(value = "INSERT INTO l_users_services (id, user_id, service_id) VALUES (DEFAULT, :userId, :serviceId)", nativeQuery = true, countQuery = "SELECT * FROM l_users_services WHERE user_id = :userId, service_id=:serviceId")
    void addServiceToUser (@Param("userId") int userId, @Param("serviceId") int serviceId);
}
