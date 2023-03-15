package com.tms.service;

import com.tms.domain.User;

import java.sql.*;
import java.time.LocalDateTime;

public class UserCrudService {

    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        User user = new User();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users_table WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            result.next();
            user.setId(result.getInt("id"));
            user.setFirstName(result.getString("first_name"));
            user.setLastName(result.getString("last_name"));
            user.setCountry(result.getString("country"));
            user.setCity(result.getString("city"));
            user.setLogin(result.getString("login"));
            user.setPassword(result.getString("password"));
            user.setCreated(result.getTimestamp("created"));
            user.setChanged(result.getTimestamp("changed"));
            user.setDeleted(result.getBoolean("is_deleted"));
            user.setRating(result.getDouble("rating"));
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return user;
    }

    public boolean createUser(String firstName, String lastName, String country, String city, String login, String password) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users_table (id, first_name, last_name, country, city, login, password, created, changed, is_deleted, rating)" +
                    "VALUES (DEFAULT,?, ?, ?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, country);
            statement.setString(4, city);
            statement.setString(5, login);
            statement.setString(6, password);
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); // created
            statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now())); // changed

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }


    public boolean updateUser(int id, String firstName, String lastName, String country, String city, String login, String password) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users_table SET firstName=?, lastName=?, country=?, city=?, login=?, password=?, changed=? WHERE id =?)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, country);
            statement.setString(4, city);
            statement.setString(5, login);
            statement.setString(6, password);
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); // changed
            statement.setInt(8, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }

    public boolean deleteUser(int id) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("UPDATE  users_table SET is_deleted = TRUE WHERE id =?");
            statement.setInt(1, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }
}