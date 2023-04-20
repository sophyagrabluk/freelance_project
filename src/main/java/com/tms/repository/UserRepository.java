package com.tms.repository;

import com.tms.mapper.UserMapper;
import com.tms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        int result = template.update("INSERT INTO users_table (id, first_name, last_name, country, city, login, password, created, changed, is_deleted)" +
                "VALUES (DEFAULT,?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)", new Object[]{user.getFirstName(), user.getLastName(),
                user.getCountry(), user.getCity(), user.getLogin(), user.getPassword(), new Date((new java.util.Date()).getTime()), new Date((new java.util.Date()).getTime())});
        return result == 1;
    }

    public boolean updateUser(User user) {
        int result = template.update("UPDATE users_table SET first_name=?, last_name=?, country=?, city=?, login=?, password=?, changed=? WHERE id =?",
                new Object[]{user.getFirstName(), user.getLastName(), user.getCountry(), user.getCity(),
                        user.getLogin(), user.getPassword(), new Date((new java.util.Date()).getTime()), user.getId()});
        return result == 1;
    }

    public boolean deleteUser(int id) {
        int result = template.update("UPDATE users_table SET is_deleted = TRUE WHERE id =?", id);
        return result == 1;
    }

    public boolean addServiceToUser(int userId, int serviceId) {
        int result = template.update("INSERT INTO l_users_services (id, user_id, service_id) VALUES (DEFAULT,?, ?)",
                new Object[]{userId, serviceId});
        return result == 1;
    }
}