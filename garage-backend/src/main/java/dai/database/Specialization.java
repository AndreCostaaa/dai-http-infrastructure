package dai.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Specialization(int id,
                             String name,
                             double hourlyRate) implements IEntity{

    static final String getAllQuery = "SELECT * FROM specialization;",
            getSpecializationByIdQuery = "SELECT * FROM specialization WHERE id = :id;",
            createSpecializationQuery = "INSERT INTO specialization (name, hourly_rate) VALUES (:name, :hourly_rate);",
            updateSpecializationQuery = "UPDATE specialization SET name = :name, hourly_rate = :hourly_rate WHERE id = :id;",
            deleteSpecializationQuery = "DELETE FROM specialization WHERE id = :id;";

    private static Specialization fetchNext(ResultSet resultSet) throws SQLException{
        if(!resultSet.next()){
            return null;
        }
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double hourlyRate = resultSet.getDouble("hourly_rate");

        return new Specialization(id, name, hourlyRate);
    }

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException{
        statement.setString("name", name);
        statement.setDouble("hourly_rate", hourlyRate);
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

    static public Specialization[] fetchAll() throws SQLException{
        return DatabaseHandler.fetchAll(getAllQuery, Specialization::fetchNext);
    }

    static public Specialization fetchById(int id) throws SQLException {
        return DatabaseHandler.fetchById(getSpecializationByIdQuery, id, Specialization::fetchNext);
    }

    public Specialization create() throws SQLException{
        return DatabaseHandler.executeCreateStatement(createSpecializationQuery, this, Specialization::fetchNext);
    }

    public Specialization update() throws SQLException{
        return DatabaseHandler.executeUpdateStatement(updateSpecializationQuery, this, Specialization::fetchNext);
    }

    static public boolean delete(int id) throws SQLException {
        return DatabaseHandler.deleteById(deleteSpecializationQuery, id);
    }
    
}
