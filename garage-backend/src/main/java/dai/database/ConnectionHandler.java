package dai.database;

import java.sql.*;

public class ConnectionHandler {

    private static Connection connection;

    // Private constructor to avoid creating objects of this class
    private ConnectionHandler() {

    }

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/garage?user=jdbc&password=super_secret&currentSchema=garage",
                    "jdbc",
                    "super_secret");
        }
        return connection;
    }

}
