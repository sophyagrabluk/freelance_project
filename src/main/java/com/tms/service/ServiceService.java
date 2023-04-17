package com.tms.service;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service
public class ServiceService {

    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public com.tms.domain.Service getServiceById(int id) {
        com.tms.domain.Service service = new com.tms.domain.Service();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM services_table WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            result.next();
            service.setId(result.getInt("id"));
            service.setName(result.getString("name"));
            service.setSection(result.getString("section"));
            service.setDescription(result.getString("description"));
            service.setDeleted(result.getBoolean("is_deleted"));
            service.setUserId(result.getInt("user_id"));
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return service;
    }

    public ArrayList<com.tms.domain.Service> getAllServices() {
        ArrayList<com.tms.domain.Service> allService = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM services_table");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                com.tms.domain.Service service = new com.tms.domain.Service();
                service.setId(result.getInt("id"));
                service.setName(result.getString("name"));
                service.setSection(result.getString("section"));
                service.setDescription(result.getString("description"));
                service.setDeleted(result.getBoolean("is_deleted"));
                service.setUserId(result.getInt("user_id"));
                allService.add(service);
            }
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return allService;
    }

    public ArrayList<com.tms.domain.Service> getServiceFromOneUser(int userId) {
        ArrayList<com.tms.domain.Service> serviceForOneUserList = new ArrayList<>();
        com.tms.domain.Service service = new com.tms.domain.Service();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM services_table WHERE user_id=?");
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                com.tms.domain.Service serviceForUser = new com.tms.domain.Service();
                serviceForUser.setId(result.getInt("id"));
                serviceForUser.setName(result.getString("name"));
                serviceForUser.setSection(result.getString("section"));
                serviceForUser.setDescription(result.getString("description"));
                serviceForUser.setDeleted(result.getBoolean("is_deleted"));
                serviceForUser.setUserId(result.getInt("user_id"));
                serviceForOneUserList.add(serviceForUser);
            }
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return serviceForOneUserList;
    }


    public boolean createService(com.tms.domain.Service service) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO services_table (id, name, section, description, is_deleted, user_id)" +
                    "VALUES (DEFAULT,?, ?, ?, DEFAULT, ?)");
            statement.setString(1, service.getName());
            statement.setString(2, service.getSection());
            statement.setString(3, service.getDescription());
            statement.setInt(4, service.getUserId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }

    public boolean updateService(com.tms.domain.Service service) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("UPDATE services_table SET name=?, section=?, description=? WHERE id =?");
            statement.setString(1, service.getName());
            statement.setString(2, service.getSection());
            statement.setString(3, service.getDescription());
            statement.setInt(4, service.getId());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }

    public boolean deleteService(int id) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("UPDATE  services_table SET is_deleted = TRUE WHERE id =?");
            statement.setInt(1, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }

}
