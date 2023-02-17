package Service;

import org.Service;

import java.sql.*;

public class ServiceCrudService {
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Service getServiceById(int id) {
        Service service = new Service();
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
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return service;
    }

    public boolean createService(String name, String section, String description) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO services_table (id, name, section, description, is_deleted)" +
                    "VALUES (DEFAULT,?, ?, ?, DEFAULT)");
            statement.setString(1, name);
            statement.setString(2, section);
            statement.setString(3, description);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }

    public boolean updateService(int id, String name, String section, String description) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/freelance_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("UPDATE services_table SET name=?, section=?, description=? WHERE id =?)");
            statement.setString(1, name);
            statement.setString(2, section);
            statement.setString(3, description);
            statement.setInt(4, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ooops! It's error..." + e);
        }
        return result == 1;
    }

    public boolean deleteService (int id){
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
