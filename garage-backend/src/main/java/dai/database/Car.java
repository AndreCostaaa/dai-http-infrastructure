package dai.database;

import java.sql.*;

public record Car(Integer id,
        Integer ownerId,
        String chassisNo,
        String recType,
        String brand,
        String model,
        String color) implements IEntity {

    static final String getAllCarsQuery = "SELECT * FROM car;",
            getCarByIdQuery = "SELECT * FROM car WHERE id = :id;",
            getCarByOwnerIdQuery = "SELECT * FROM car WHERE owner_id = :owner_id;",
            createCarQuery = "INSERT INTO car(owner_id, chassis_no, rec_type, brand, model, color) VALUES (:owner_id, :chassis_no, :rec_type, :brand, :model, :color);",
            updateCarQuery = "UPDATE car SET owner_id = :owner_id, chassis_no = :chassis_no, rec_type = :rec_type, brand = :brand, model = :model, color = :color WHERE id = :id;",
            deleteCarQuery = "DELETE FROM car WHERE id = :id;";

    /**
     * @param resultSet resultset returned from the execution of the query
     * @return the car currently pointed at by result set
     */
    private static Car fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        Integer id = resultSet.getObject("id", Integer.class);
        Integer ownerId = resultSet.getObject("owner_id", Integer.class);
        String chassisNo = resultSet.getString("chassis_no");
        String recType = resultSet.getString("rec_type");
        String brand = resultSet.getString("brand");
        String model = resultSet.getString("model");
        String color = resultSet.getString("color");

        return new Car(id, ownerId, chassisNo, recType, brand, model, color);
    }

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException {
        DatabaseHandler.checkIfNull(ownerId, ownerId(), statement, "owner_id", Types.DOUBLE);
        statement.setString("chassis_no", chassisNo());
        statement.setString("rec_type", recType());
        statement.setString("brand", brand());
        statement.setString("model", model());
        statement.setString("color", color());
    }

    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
    }

    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
        statement.setInt("id", id());
    }

    /**
     * Fetch all Cars from the database.
     *
     * @return Car[] with all cars in the database
     */
    static public Car[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllCarsQuery, Car::fetchNext);
    }

    /**
     * Fetch a Car from the database matching the given id.
     *
     * @param id the id of the Car to fetch
     * @return Car or null
     */
    static public Car fetchOne(Integer id) throws SQLException {
        return DatabaseHandler.fetchById(getCarByIdQuery, id, Car::fetchNext);
    }

    /**
     * Fetch all Cars from the database matching the given owenerId.
     *
     * @param ownerId the id of the Client to fetch Cars for
     * @return Car[] or null
     */
    static public Car[] fetchByOwnerId(Integer ownerId) throws SQLException {
        return DatabaseHandler.fetchAllBy(getCarByOwnerIdQuery, "owner_id", ownerId, Car::fetchNext);
    }

    /**
     * Save the Car in the database.
     *
     * @return Car or null
     */
    public Car save() throws SQLException {
        return DatabaseHandler.executeCreateStatement(createCarQuery, this, Car::fetchNext);
    }

    /**
     * Update the Car in the database.
     *
     * @return Car or null
     */
    public Car update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateCarQuery, this, Car::fetchNext);
    }

    /**
     * Delete a Car from the database matching the given id.
     *
     * @param id the id of the Car to delete
     * @return true if successful
     */
    static public boolean delete(Integer id) throws SQLException {
        return DatabaseHandler.deleteById(deleteCarQuery, id);
    }

}
