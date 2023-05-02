package com.tms.repository;

import com.tms.model.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ServiceRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SessionFactory sessionFactory;

    public ServiceRepository() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public com.tms.model.Service getServiceById(int id) {
        Session session = sessionFactory.openSession();
        Service service = session.get(Service.class, id);
        session.close();
        if (service != null && !service.isDeleted()) {
            return service;
        }
        return null;
    }

    public ArrayList<Service> getAllServices() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Service where isDeleted = false");
        ArrayList<Service> list = (ArrayList<Service>) query.getResultList();
        session.close();
        return list;
    }

    public ArrayList<com.tms.model.Service> getServiceFromOneUser(int userId) {
        Session session = sessionFactory.openSession();
        Query query = session.createNativeQuery("SELECT s.id, s.name, s.section, s.description, s.rating, s.user_id FROM services_table AS s WHERE user_id = :userId AND is_deleted = FALSE");
        query.setParameter("userId", userId);
        session.close();
        return (ArrayList<Service>) query.getResultList();
   }

    public ArrayList<com.tms.model.Service> getServicesFromOneSection(String section) {
        Session session = sessionFactory.openSession();
        Query query = session.createNativeQuery("SELECT s.id, s.name, s.section, s.description, s.rating, s.user_id FROM services_table AS s WHERE section = :section AND is_deleted = FALSE");
        query.setParameter("section", section);
        session.close();
        return (ArrayList<Service>) query.getResultList();
    }

    public boolean createService(com.tms.model.Service service) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(service);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
        }
        return false;
    }

    public boolean updateService(com.tms.model.Service service) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(service);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteService(int id) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Service service = session.get(Service.class, id);
            service.setDeleted(true);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            logger.warn("There is exception: " + e.getMessage());
        }
        return false;
    }
}