package dai.database;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionHandler {

    private static Connection connection;

    // Private constructor to avoid creating objects of this class
    private ConnectionHandler() {

    }

    private static Connection createConnection(String dbms, String ip, String port, String dbName, String user,
            String password, String schema) throws SQLException {
        return DriverManager.getConnection(String.format(
                "jdbc:%s://%s:%s/%s?currentSchema=%s",
                dbms, ip, port, dbName, schema),
                user,
                password);
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            Dotenv dotenv = Dotenv.configure().load();
            String dbms = dotenv.get("JDBC_DBMS");
            String ip = dotenv.get("JDBC_IP");
            String port = dotenv.get("JDBC_PORT");
            String dbName = dotenv.get("JDBC_DATABASE_NAME");
            String user = dotenv.get("JDBC_USER");
            String password = dotenv.get("JDBC_PASSWORD");

            String schema = dotenv.get("JDBC_SCHEMA");

            connection = createConnection(dbms, ip, port, dbName, user, password, schema);
        }
        return connection;
    }

    public static void setupConnectionForTesting() throws SQLException {

        Dotenv dotenv = Dotenv.configure().load();
        String dbms = "postgresql";
        String ip = "localhost";
        String port = "5434";
        String dbName = dotenv.get("JDBC_DATABASE_NAME");
        String user = dotenv.get("JDBC_USER");
        String password = dotenv.get("JDBC_PASSWORD");
        String schema = dotenv.get("JDBC_SCHEMA");

        connection = createConnection(dbms, ip, port, dbName, user, password, schema);
    }
}
