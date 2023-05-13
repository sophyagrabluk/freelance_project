package com.tms.repository;

import com.tms.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(Integer userId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE orders_table SET status = 'FINISHED' WHERE id = :id",
            countQuery = "SELECT * FROM orders_table WHERE id = :id")
    void finishOrder (int id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "SELECT login FROM users_table WHERE id = (SELECT user_id FROM orders_table WHERE id = :id)",
            countQuery = "SELECT * FROM orders_table WHERE id = :id")
    String getUserLogin (int id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "SELECT user_login FROM services_table WHERE id = (SELECT service_id FROM orders_table WHERE id = :id)",
            countQuery = "SELECT * FROM orders_table WHERE id = :id")
    String getOwnerUserLogin (int id);
}
