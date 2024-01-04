package dai.database;

import java.sql.*;
import java.util.HashMap;

public class DatabaseHandler {

    public interface IStatement<T> {
        void completeStatement(T element, NamedParameterStatement wrapper) throws SQLException;
    }

    static public boolean deleteById(String stringQuery, int id) throws SQLException {
        try (NamedParameterStatement wrapper = new NamedParameterStatement(ConnectionHandler.getConnection(), (stringQuery))) {
            wrapper.setInt("id", id);

            return wrapper.executeUpdate() == 1;
        }
    }

    static public <T> T[] fetchAll(String stringQuery, ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (Statement wrapper = ConnectionHandler.getConnection().createStatement()) {
            try (ResultSet resultSet = wrapper.executeQuery(stringQuery)) {
                var resultSetHandler = new ResultSetHandler<>(resultSet, iresultSetHandler);

                return resultSetHandler.fetchAll();
            }
        }
    }

    static public <T> T fetchBy(String stringQuery, String key, String value, ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (NamedParameterStatement wrapper = new NamedParameterStatement(ConnectionHandler.getConnection(), (stringQuery))) {
            wrapper.setString(key, value);

            try (ResultSet resultSet = wrapper.executeQuery()) {
                return iresultSetHandler.fetchNext(resultSet);
            }
        }
    }

    static public <T> T fetchById(String stringQuery, int id, ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (NamedParameterStatement wrapper = new NamedParameterStatement(ConnectionHandler.getConnection(), (stringQuery))) {
            wrapper.setInt("id", id);

            try (ResultSet resultSet = wrapper.executeQuery()) {
                return iresultSetHandler.fetchNext(resultSet);
            }
        }
    }

    static public <T extends IEntity> boolean executeUpdateStatement(String stringQuery, T element) throws SQLException {
        try (NamedParameterStatement wrapper = new NamedParameterStatement(ConnectionHandler.getConnection(), (stringQuery))) {
            element.completeUpdateStatement(wrapper);

            return wrapper.executeUpdate() == 1;
        }
    }

    static public <T extends IEntity> boolean executeCreateStatement(String stringQuery, T element) throws SQLException {
        try (NamedParameterStatement wrapper = new NamedParameterStatement(ConnectionHandler.getConnection(), (stringQuery))) {
            element.completeCreateStatement(wrapper);

            return wrapper.executeUpdate() == 1;
        }
    }

}
