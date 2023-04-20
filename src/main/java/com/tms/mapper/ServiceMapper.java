package com.tms.mapper;

import com.tms.model.Service;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServiceMapper implements RowMapper<Service> {
    @Override
    public Service mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Service service = new Service();
        service.setId(resultSet.getInt("id"));
        service.setName(resultSet.getString("name"));
        service.setSection(resultSet.getString("section"));
        service.setDescription(resultSet.getString("description"));
        service.setDeleted(resultSet.getBoolean("is_deleted"));
        service.setUserId(resultSet.getInt("user_id"));
        service.setRating(resultSet.getInt("rating"));
        return service;
    }
}
