package dai.database;

import java.sql.*;
import java.util.ArrayList;

public class ResultSetHandler<T> {

    public interface IResultSetHandler<T> {
        T fetchNext(ResultSet resultSet) throws SQLException;
    }

    private ResultSet resultSet;
    private IResultSetHandler<T> resultSetHandler;

    public ResultSetHandler(ResultSet resultSet, IResultSetHandler<T> resultSetHandler) {
        this.resultSet = resultSet;
        this.resultSetHandler = resultSetHandler;
    }

    public T[] fetchAll() throws SQLException{
        ArrayList<T> entities = new ArrayList<>();
        while (resultSet.next()) {
            entities.add(resultSetHandler.fetchNext(resultSet));
        }
        return (T[]) entities.toArray();
    }

}
