package  dai.database;
import java.sql.*;
import java.util.HashMap;

public class DatabaseHandler {

    public interface IStatement<T>{
        void completeStatement(T element, CallableStatement statement) throws SQLException;
    }
    static public boolean deleteById(String stringQuery, int id) throws SQLException {
        try (CallableStatement callableStatement = ConnectionHandler.getConnection().prepareCall(stringQuery)) {
            callableStatement.setInt("id", id);

            return callableStatement.executeUpdate() == 1;
        }
    }
    static public <T>  T[] fetchAll(String stringQuery, ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (Statement statement = ConnectionHandler.getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(stringQuery)) {
                var resultSetHandler = new ResultSetHandler<T>(resultSet, iresultSetHandler);
                return resultSetHandler.fetchAll();
            }
        }
    }
    static public <T> T fetchBy(String stringQuery, String key, String value, ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (CallableStatement statement = ConnectionHandler.getConnection().prepareCall(stringQuery)) {
            statement.setString(key, value);
            try (ResultSet resultSet = statement.executeQuery(stringQuery)) {
                return iresultSetHandler.fetchNext(resultSet);
            }
        }
    }
    static public <T>  T fetchById(String stringQuery, int id,  ResultSetHandler.IResultSetHandler<T> iresultSetHandler) throws SQLException {
        try (CallableStatement statement = ConnectionHandler.getConnection().prepareCall(stringQuery)) {
            statement.setInt("id", id);
            try (ResultSet resultSet = statement.executeQuery(stringQuery)) {
                return iresultSetHandler.fetchNext(resultSet);
            }
        }
    }
    static public <T extends IEntity> boolean executeUpdateStatement(String stringQuery, T element) throws SQLException
    {
        try(CallableStatement statement = ConnectionHandler.getConnection().prepareCall(stringQuery))
        {
            element.completeUpdateStatement(statement);
            return statement.executeUpdate() == 1;
        }
    }
    static public <T extends IEntity> boolean executeCreateStatement(String stringQuery, T element) throws SQLException
    {
        try(CallableStatement statement = ConnectionHandler.getConnection().prepareCall(stringQuery))
        {
            element.completeCreateStatement(statement);
            return statement.executeUpdate() == 1;
        }
    }


}
