package dai.database;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionHandler {

    private static Connection connection;

    // Private constructor to avoid creating objects of this class
    private ConnectionHandler() {

    }

    private static Connection createConnection(String dbms, String url, String dbName, String user,
            String password, String schema) throws SQLException {

        String connectionString = String.format(
                "jdbc:%s://%s/%s?user=%s&password=%s&currentSchema=%s",
                dbms, url, dbName, user, password, schema);

        return DriverManager.getConnection(connectionString);
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            String dbms = System.getenv("JDBC_DBMS");
            String url = System.getenv("JDBC_URL");
            String dbName = System.getenv("JDBC_DATABASE_NAME");
            String user = System.getenv("JDBC_USER");
            String password = System.getenv("JDBC_PASSWORD");

            String schema = System.getenv("JDBC_SCHEMA");

            connection = createConnection(dbms, url, dbName, user, password, schema);
        }
        return connection;
    }

    public static void setupConnectionForTesting() throws SQLException {

        Dotenv dotenv = Dotenv.configure().load();
        String dbms = "postgresql";
        String url = "localhost:5434";
        String dbName = System.getenv("JDBC_DATABASE_NAME");
        String user = System.getenv("JDBC_USER");
        String password = System.getenv("JDBC_PASSWORD");
        String schema = System.getenv("JDBC_SCHEMA");

        connection = createConnection(dbms, url, dbName, user, password, schema);
    }
}
