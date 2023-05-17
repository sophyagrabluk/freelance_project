package com.tms.repository;

import com.tms.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> findAllByToWhichServiceIdOrderByCreatedDesc(int toWhichUserId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE feedback_table SET is_deleted = true WHERE id = :id",
            countQuery = "SELECT * FROM feedback_table WHERE id = :id")
    void deleteFeedback(int id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE services_table SET rating = (SELECT round(avg(rating),1) FROM feedback_table WHERE to_which_service_id = :toWhichServiceId) WHERE id = :toWhichServiceId",
            countQuery = "SELECT * FROM services_table WHERE id = :toWhichServiceId")
    void updateRating(int toWhichServiceId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE feedback_table SET comment = :newComment WHERE id = :id",
            countQuery = "SELECT * FROM feedback_table WHERE id = :id")
    void updateFeedback(int id, String newComment);
}