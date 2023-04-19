package com.tms.repository;

import com.tms.domain.User;
import com.tms.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class UserRepository {

    public JdbcTemplate template;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public User getUserById(int id) {
        return template.queryForObject("SELECT * FROM users_table WHERE id=?", new UserMapper(), id);
    }

    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) template.query("SELECT * FROM users_table", new UserMapper());
    }

    public boolean createUser(User user) {
        int result = template.update("INSERT INTO users_table (id, first_name, last_name, country, city, login, password, created, changed, is_deleted, rating)" +
                "VALUES (DEFAULT,?, ?, ?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT)", new Object[]{user.getFirstName(), user.getLastName(),
                user.getCountry(), user.getCity(), user.getLogin(), user.getPassword(), new Date((new java.util.Date()).getTime()), new Date((new java.util.Date()).getTime())});
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

    public boolean addServiceToUser(int userId, int serviceId) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO l_users_services (id, user_id, service_id)" +
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