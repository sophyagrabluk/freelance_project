package com.tms.repository;

import com.tms.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<ArrayList<Order>> findAllByUserId(Integer userId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE orders_table SET status = 'FINISHED' WHERE id = :id",
            countQuery = "SELECT * FROM orders_table WHERE id = :id")
    void finishOrder (int id);
}
