package com.tms.repository;

import com.tms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE users_table SET is_deleted = true WHERE id = :id",
            countQuery = "SELECT * FROM users_table WHERE id = :id")
    void deleteUser(int id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "INSERT INTO l_users_services (id, user_id, service_id) VALUES (DEFAULT, :userId, :serviceId)",
            countQuery = "SELECT * FROM l_users_services WHERE user_id = :userId, service_id=:serviceId")
    void addServiceToUser(int userId, int serviceId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "DELETE FROM l_users_services WHERE user_id = :userId AND service_id=:serviceId",
            countQuery = "SELECT * FROM l_users_services WHERE user_id = :userId, service_id=:serviceId")
    void removeServiceFromUser(int userId, int serviceId);

    Optional<User> findUserByLogin(String login);
}
