package dai.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public record ServiceState(int id,
                           String title,
                           String description) implements IEntity {

    static final String getAllQuery = "SELECT * FROM service_state;",
            getServiceStateByIdQuery = "SELECT * FROM service_state WHERE id = :id;",
            updateServiceStateQuery = "UPDATE service_state SET title = :title, description = :description WHERE id = :id;";


    private static ServiceState fetchNext(ResultSet resultSet) throws SQLException{
        if(!resultSet.next()){
            return null;
        }
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");

        return new ServiceState(id, title, description);
    }

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException{
        statement.setString("title", title);
        statement.setString("description", description);
    }

    @Override
    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
    }

    @Override
    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
        statement.setInt("id", id());
    }

    static public ServiceState[] fetchAll() throws SQLException{
        return DatabaseHandler.fetchAll(getAllQuery, ServiceState::fetchNext);
    }

    static public ServiceState fetchById(int id) throws SQLException {
        return DatabaseHandler.fetchById(getServiceStateByIdQuery, id, ServiceState::fetchNext);
    }

    public ServiceState update() throws SQLException{
        return DatabaseHandler.executeUpdateStatement(updateServiceStateQuery, this, ServiceState::fetchNext);
    }
}
