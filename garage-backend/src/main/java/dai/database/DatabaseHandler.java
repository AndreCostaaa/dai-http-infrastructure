package dai.database;

import java.sql.*;

public class DatabaseHandler {

    public interface IStatement<T> {
        void completeStatement(T element, NamedParameterStatement statement) throws SQLException;
    }

    static private String addReturningToQuery(String query) {
        return query.replace(";", " RETURNING *;");
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

    static public <T> T[] fetchAllBy(String stringQuery, String key, String value,
            ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            statement.setString(key, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                var resultSetHandler = new ResultSetHandler<>(resultSet, iresultSetHandler);

                return resultSetHandler.fetchAll();
            }
        }
    }

    static public <T> T[] fetchAllBy(String stringQuery, String key, int value,
                                     ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            statement.setInt(key, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                var resultSetHandler = new ResultSetHandler<>(resultSet, iresultSetHandler);

                return resultSetHandler.fetchAll();
            }
        }
    }

    static public <T> T[] fetchAllByTwoParams(String stringQuery, String key1, int value1,
                                     String key2, int value2,
                                     ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            statement.setInt(key1, value1);
            statement.setInt(key2, value2);

            try (ResultSet resultSet = statement.executeQuery()) {
                var resultSetHandler = new ResultSetHandler<>(resultSet, iresultSetHandler);

                return resultSetHandler.fetchAll();
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

    static public <T extends IEntity> T executeUpdateStatement(String stringQuery, T element,
            ResultSetHandler.IResultSetHandler<T> iresultSetHandler)
            throws SQLException {
        stringQuery = addReturningToQuery(stringQuery);
        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            element.completeUpdateStatement(statement);

            try (ResultSet resultSet = statement.executeQuery()) {
                return iresultSetHandler.fetchNext(resultSet);
            }
        }
    }

    static public <T extends IEntity> T executeCreateStatement(String stringQuery, T element,
            ResultSetHandler.IResultSetHandler<T> iresultSetHandler)
            throws SQLException {
        stringQuery = addReturningToQuery(stringQuery);

        try (NamedParameterStatement statement = new NamedParameterStatement(ConnectionHandler.getConnection(),
                (stringQuery))) {
            element.completeCreateStatement(statement);

            try (ResultSet resultSet = statement.executeQuery()) {
                return iresultSetHandler.fetchNext(resultSet);
            }
        }
    }

}
