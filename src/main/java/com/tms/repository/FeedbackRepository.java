package com.tms.repository;

import com.tms.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    Optional<ArrayList<Feedback>> findAllByToWhichServiceId(int toWhichUserId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE feedback_table SET is_deleted = true WHERE id = :id",
            countQuery = "SELECT * FROM feedback_table WHERE id = :id")
    void deleteFeedback (int id);
}
