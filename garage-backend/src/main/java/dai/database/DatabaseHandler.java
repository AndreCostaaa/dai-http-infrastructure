package dai.database;

import java.sql.*;
import java.util.HashMap;

public class DatabaseHandler {

    public interface IStatement<T> {
        void completeStatement(T element, NamedParameterStatement statement) throws SQLException;
    }

    static public boolean deleteById(String stringQuery, int id) throws SQLException {
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            statement.setInt("id", id);

            return statement.executeUpdate() == 1;
        }
    }

    static public <T> T[] fetchAll(String stringQuery, ResultSetHandler.IResultSetHandler<T> iresultSetHandler)
            throws SQLException {
        try (Statement statement = ConnectionHandler.getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(stringQuery)) {
                var resultSetHandler = new ResultSetHandler<>(resultSet, iresultSetHandler);

                return resultSetHandler.fetchAll();
            }
        }
    }

    static public <T> T fetchBy(String stringQuery, String key, String value,
            ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            statement.setString(key, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                return iresultSetHandler.fetchNext(resultSet);
            }
        }
    }

    static public <T> T fetchById(String stringQuery, int id, ResultSetHandler.IResultSetHandler<T> iresultSetHandler)
            throws SQLException {
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            statement.setInt("id", id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return iresultSetHandler.fetchNext(resultSet);
            }
        }
    }

    static public <T extends IEntity> boolean executeUpdateStatement(String stringQuery, T element)
            throws SQLException {
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            element.completeUpdateStatement(statement);

            return statement.executeUpdate() == 1;
        }
    }

    static public <T extends IEntity> T executeCreateStatement(String stringQuery, T element, ResultSetHandler.IResultSetHandler<T> iresultSetHandler)
            throws SQLException {
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            element.completeCreateStatement(statement);

            String preparedStatementQuery = statement.getStatement().toString();
            statement.executeUpdate(preparedStatementQuery, Statement.RETURN_GENERATED_KEYS);

            try (ResultSet generatedKeys = statement.getStatement().getGeneratedKeys()) {
                generatedKeys.next();
                int primaryKey = generatedKeys.getInt(1);

                return null;
            }
        }
    }

}
