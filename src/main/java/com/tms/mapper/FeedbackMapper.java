package com.tms.mapper;

import com.tms.model.Feedback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FeedbackMapper implements RowMapper<Feedback> {
    @Override
    public Feedback mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setId(resultSet.getInt("id"));
        feedback.setRating(resultSet.getInt("rating"));
        feedback.setComment(resultSet.getString("comment"));
        feedback.setCreated(resultSet.getTimestamp("created"));
        feedback.setToWhichUserId(resultSet.getInt("to_which_service_id"));
        feedback.setFromWhichUserId(resultSet.getInt("from_which_user_id"));
        return feedback;
    }
}
