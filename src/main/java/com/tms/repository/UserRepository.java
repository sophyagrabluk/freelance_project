package com.tms.repository;

import com.tms.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public class UserRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SessionFactory sessionFactory;

    public UserRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public User getUserById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        if (user != null && !user.isDeleted()) {
            return user;
        }
        return null;
    }

    public ArrayList<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User where isDeleted = false");
        ArrayList<User> list = (ArrayList<User>) query.getResultList();
        session.close();
        return list;
    }

    public boolean createUser(User user) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            user.setCreated(new Timestamp(System.currentTimeMillis()));
            user.setChanged(new Timestamp(System.currentTimeMillis()));
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
        }
        return false;
    }

    public boolean updateUser(User user) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            user.setChanged(new Timestamp(System.currentTimeMillis()));
            session.saveOrUpdate(user);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(int id) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            user.setDeleted(true);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
        }
        return false;
    }

    public boolean addServiceToUser(int userId, int serviceId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createNativeQuery("INSERT INTO l_users_services (id, user_id, service_id) " +
                "VALUES (DEFAULT, :userId, :serviceId)");
        query.setParameter("userId", userId);
        query.setParameter("serviceId", serviceId);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result == 1;
    }
}