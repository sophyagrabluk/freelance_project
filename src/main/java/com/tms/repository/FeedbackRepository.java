package com.tms.repository;

import com.tms.domain.Feedback;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class FeedbackRepository {

    public ArrayList<Feedback> getAllFeedback(int toWhichUserId) {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM feedback_table WHERE to_which_service_id=?");
            statement.setInt(1, toWhichUserId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(result.getInt("id"));
                feedback.setRating(result.getInt("rating"));
                feedback.setComment(result.getString("comment"));
                feedback.setCreated(result.getTimestamp("created"));
                feedback.setToWhichUserId(result.getInt("to_which_service_id"));
                feedback.setFromWhichUserId(result.getInt("from_which_user_id"));
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return feedbackList;
    }

    public boolean createFeedback(Feedback feedback) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO feedback_table (id, rating, comment, created, to_which_service_id, from_which_user_id)" +
                    "VALUES (DEFAULT,?, ?, ?, ?, ?)");
            statement.setInt(1, feedback.getRating());
            statement.setString(2, feedback.getComment());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // created
            statement.setInt(4, feedback.getToWhichUserId());
            statement.setInt(5, feedback.getFromWhichUserId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }
}