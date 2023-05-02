package com.tms.repository;

import com.tms.model.Feedback;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public class FeedbackRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private  final SessionFactory sessionFactory;

    public FeedbackRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    public ArrayList<Feedback> getAllFeedback(int toWhichUserId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Feedback");
        ArrayList<Feedback> list = (ArrayList<Feedback>) query.getResultList();
        session.close();
        return list;
    }

    public boolean createFeedback(Feedback feedback) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            feedback.setCreated(new Timestamp(System.currentTimeMillis()));
            session.save(feedback);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteFeedback(int id) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Feedback feedback = session.get(Feedback.class, id);
            feedback.setDeleted(true);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
        }
        return false;
    }
}