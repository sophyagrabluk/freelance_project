package com.tms.service;

import com.tms.domain.User;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserService {
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
            user.setRating(result.getInt("rating"));
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return user;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers =new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users_table");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
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
                user.setRating(result.getInt("rating"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return allUsers;
    }

    public boolean createUser(User user) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users_table (id, first_name, last_name, country, city, login, password, created, changed, is_deleted, rating)" +
                    "VALUES (DEFAULT,?, ?, ?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT)");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getCountry());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); // created
            statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now())); // changed

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }


    public boolean updateUser(User user) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users_table SET first_name=?, last_name=?, country=?, city=?, login=?, password=?, changed=? WHERE id =?");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getCountry());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); // changed
            statement.setInt(8, user.getId());

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

    public boolean addServiceToUser (int userId, int serviceId){
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO l_users_services (id, users_id, services_id)" +
                    "VALUES (DEFAULT,?, ?)");
            statement.setInt(1, userId);
            statement.setInt(2, serviceId);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }

}
