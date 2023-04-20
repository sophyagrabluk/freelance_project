package com.tms.repository;

import com.tms.mapper.ServiceMapper;
import com.tms.model.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;

@Repository
public class ServiceRepository {

    JdbcTemplate template;

    public ServiceRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public com.tms.model.Service getServiceById(int id) {
        return template.queryForObject("SELECT * FROM services_table WHERE id=?", new ServiceMapper(), id);
    }

    public ArrayList<Service> getAllServices() {
        return (ArrayList<Service>) template.query("SELECT * FROM services_table", new ServiceMapper());
    }

    public ArrayList<com.tms.model.Service> getServiceFromOneUser(int userId) {
        return (ArrayList<Service>)template.query("SELECT * FROM services_table WHERE user_id=?", new ServiceMapper(), userId);
    }

    public ArrayList<com.tms.model.Service> getServicesFromOneSection(String section) {
        return (ArrayList<Service>)template.query("SELECT * FROM services_table WHERE section=?", new ServiceMapper(), section);
    }

    public boolean createService(com.tms.model.Service service) {
        int result = template.update("INSERT INTO services_table (id, name, section, description, is_deleted, user_id)" +
                "VALUES (DEFAULT,?, ?, ?, DEFAULT, ?)", new Object[]{service.getName(), service.getSection(),service.getDescription(), service.getUserId()});
        return result == 1;
    }

    public boolean updateService(com.tms.model.Service service) {
        int result = template.update("UPDATE services_table SET name=?, section=?, description=? WHERE id =?",
                new Object[]{service.getName(), service.getSection(),service.getDescription(), service.getId()});
        return result == 1;
    }

    public boolean deleteService(int id) {
        int result = template.update("UPDATE  services_table SET is_deleted = TRUE WHERE id =?", id);
        return result == 1;
    }
}