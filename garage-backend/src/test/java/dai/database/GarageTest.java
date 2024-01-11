package dai.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class GarageTest {

    static protected Connection connection;
    static protected final int ghostId = 0;

    @BeforeAll
    static void createDbConnection() throws SQLException {
        ConnectionHandler.setupConnectionForTesting();
        connection = ConnectionHandler.getConnection();
    }

    @BeforeEach
    void insertDefaultValues() throws SQLException, IOException {
        var statement = connection.createStatement();
        final String tearDownQuery = Files.readString(Path.of("./src/test/resources/teardown.sql"));
        final String createSchemaQuery = Files.readString(Path.of("./src/test/resources/create_testing_schema.sql"));
        final String createTablesQuery = Files.readString(Path.of("./src/test/resources/create_tables.sql"));
        final String createViewsAndTriggersQuery = Files
                .readString(Path.of("./src/test/resources/views_and_triggers.sql"));
        final String insertValuesQuery = Files.readString(Path.of("./src/test/resources/insert_values.sql"));

        String[] queries = { tearDownQuery, createSchemaQuery, createTablesQuery, createViewsAndTriggersQuery,
                insertValuesQuery };

        for (var query : queries) {
            statement.executeUpdate(query);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void closeDbConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

}