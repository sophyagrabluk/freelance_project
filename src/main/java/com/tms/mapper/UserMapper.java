package com.tms.mapper;

import com.tms.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet result, int rowNum) throws SQLException {
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
        return user;
    }
}
