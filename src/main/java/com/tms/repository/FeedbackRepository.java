package com.tms.repository;

import com.tms.mapper.FeedbackMapper;
import com.tms.model.Feedback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class FeedbackRepository {

    JdbcTemplate template;

    public FeedbackRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public ArrayList<Feedback> getAllFeedback(int toWhichUserId) {
        return (ArrayList<Feedback>) template.query("SELECT * FROM feedback_table WHERE to_which_service_id=?", new FeedbackMapper(), toWhichUserId);
    }

    public boolean createFeedback(Feedback feedback) {
        int result = template.update("INSERT INTO feedback_table (id, rating, comment, created, to_which_service_id, from_which_user_id)" +
                "VALUES (DEFAULT,?, ?, ?, ?, ?)", new Object[]{feedback.getRating(), feedback.getComment(),
                new Date((new java.util.Date()).getTime()), feedback.getToWhichUserId(), feedback.getFromWhichUserId()});
        return result == 1;
    }

    public boolean deleteFeedback(int id) {
        int result = template.update("UPDATE  feedback_table SET is_deleted = TRUE WHERE id =?");
        return result == 1;
    }
}