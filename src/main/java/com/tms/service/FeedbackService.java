package com.tms.service;

import com.tms.domain.Feedback;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;

@Service
public class FeedbackService {
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public Feedback getAllFeedback(int toWhichUserId) {
//        Feedback feedback = new Feedback();
//        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM feedback_table WHERE toWhichUserId=?");
//            statement.setInt(1, toWhichUserId);
//            ResultSet result = statement.executeQuery();
//
//            result.next();
//            feedback.setId(result.getInt("id"));
//            feedback.setRating(result.getDouble("rating"));
//            feedback.setComment(result.getString("comment"));
//            feedback.setCreated(result.getTimestamp("created"));
//            feedback.setToWhichUserId(result.getInt("toWhichUserId"));
//            feedback.setFromWhichUserId(result.getInt("fromWhichUserId"));
//
//        } catch (SQLException e) {
//            System.out.println("Ooops! It's error..." + e);
//        }
//        return feedback;
//    }

    public boolean createFeedback(double rating, String comment, int toWhichServiceId, int fromWhichUserId) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO feedback_table (id, rating, comment, created, toWhichServiceId, fromWhichUserId)" +
                    "VALUES (DEFAULT,?, ?, ?, ?, ?)");
            statement.setDouble(1, rating);
            statement.setString(2, comment);
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // created
            statement.setInt(4, toWhichServiceId);
            statement.setInt(5, fromWhichUserId);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }
}