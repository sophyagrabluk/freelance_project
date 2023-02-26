package Service;

import java.sql.*;

public class AddServiceToUserService {
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public boolean add (int userId, int serviceId){
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
