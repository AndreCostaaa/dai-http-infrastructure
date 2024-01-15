package dai.database;

import javax.xml.crypto.Data;
import java.sql.*;

public record Service(Integer id,
        Integer mechanicId,
        Integer clientId,
        Integer carId,
        Integer hoursWorked,
        String comments,
        boolean hasPictures,
        Integer stateId,
        Date dateCreated,
        Date dateCarArrival,
        Date dateCarProcessing,
        Date dateCarDone,
        Date dateCarLeft) implements IEntity {

    static final String getAllQuery = "SELECT * FROM service;",
            getServiceByIdQuery = "SELECT * FROM service WHERE id = :id;",
            getServiceByCarQuery = "SELECT * FROM service WHERE car_id = :car_id;",
            getServiceByCarStateQuery = "SELECT * FROM service WHERE car_id = :car_id AND state_id = :state_id;",
            getServiceByMechanicQuery = "SELECT * FROM service WHERE mechanic_id = :mechanic_id;",
            getServiceByMechanicStateQuery = "SELECT * FROM service WHERE mechanic_id = :mechanic_id AND state_id = :state_id;",
            getServiceByStateQuery = "SELECT * FROM service WHERE state_id = :state_id;",
            createServiceQuery = "INSERT INTO service (mechanic_id, client_id, car_id, hours_worked, comments, has_pictures, state_id, date_car_arrival, date_car_processing, date_car_done, date_car_left) VALUES (:mechanic_id, :client_id, :car_id, 0, '', false, 0, now(), NULL, NULL, NULL);",
            updateServiceQuery = "UPDATE service SET mechanic_id  = :mechanic_id, hours_worked = :hours_worked, comments = :comments, has_pictures = :has_pictures WHERE id = :id;",
            incrementStateQuery = "UPDATE service SET state_id = state_id + 1 WHERE id = :id;",
            deleteServiceQuery = "DELETE FROM service WHERE id = :id;";


    private static Service fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        Integer id = resultSet.getObject("id", Integer.class);
        Integer mechanicId = resultSet.getObject("mechanic_id", Integer.class);
        Integer clientId = resultSet.getObject("client_id", Integer.class);
        Integer carId = resultSet.getObject("car_id", Integer.class);
        Integer hoursWorked = resultSet.getObject("hours_worked", Integer.class);
        String comments = resultSet.getString("comments");
        boolean hasPictures = resultSet.getBoolean("has_pictures");
        Integer stateId = resultSet.getObject("state_id", Integer.class);
        Date dateCreated = new Date(resultSet.getTimestamp("date_created").getTime());
        Date dateCarArrival = new Date(resultSet.getTimestamp("date_car_arrival").getTime());
        Date dateCarProcessing = new Date(resultSet.getTimestamp("date_car_processing").getTime());
        Date dateCarDone = new Date(resultSet.getTimestamp("date_car_done").getTime());
        Date dateCarLeft = new Date(resultSet.getTimestamp("date_car_left").getTime());

        return new Service(id,
                mechanicId,
                clientId,
                carId,
                hoursWorked,
                comments,
                hasPictures,
                stateId,
                dateCreated,
                dateCarArrival,
                dateCarProcessing,
                dateCarDone,
                dateCarLeft);
    }

    @Override
    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        DatabaseHandler.checkIfNull(mechanicId, mechanicId(), statement, "mechanic_id", Types.INTEGER);
        DatabaseHandler.checkIfNull(clientId, clientId(), statement, "client_id", Types.INTEGER);
        DatabaseHandler.checkIfNull(carId, carId(), statement, "car_id", Types.INTEGER);
    }

    @Override
    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        statement.setInt("id", id());
        DatabaseHandler.checkIfNull(mechanicId, mechanicId(), statement, "mechanic_id", Types.INTEGER);
        DatabaseHandler.checkIfNull(hoursWorked, hoursWorked(), statement, "hours_worked", Types.INTEGER);
        statement.setString("comments", comments());
        statement.setBoolean("has_pictures", hasPictures());
    }

    /**
     * Fetch all Services from the database.
     *
     * @return Service[] or null
     */
    static public Service[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllQuery, Service::fetchNext);
    }

    /**
     * Fetch a Service from the database matching the given id.
     *
     * @param id the id of the Service to fetch
     * @return Service or null
     */
    static public Service fetchById(Integer id) throws SQLException {
        return DatabaseHandler.fetchById(getServiceByIdQuery, id, Service::fetchNext);
    }

    /**
     * Fetch all Services from the database matching the given carId.
     *
     * @param carId the id of the Car to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByCar(Integer carId) throws SQLException {
        return DatabaseHandler.fetchAllBy(getServiceByCarQuery,
                "car_id",
                carId,
                Service::fetchNext);
    }

    /**
     * Fetch all Services from the database matching the given carId and stateId.
     *
     * @param carId   the id of the Car to fetch Services for
     * @param stateId the id of the State to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByCarState(Integer carId, Integer stateId) throws SQLException {
        return DatabaseHandler.fetchAllByTwoParams(getServiceByCarStateQuery,
                "car_id",
                carId,
                "state_id",
                stateId,
                Service::fetchNext);
    }

    /**
     * Fetch all Services from the database matching the given mechanicId.
     *
     * @param mechanicId the id of the mechanic Employee to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByMechanic(Integer mechanicId) throws SQLException {
        return DatabaseHandler.fetchAllBy(getServiceByMechanicQuery,
                "mechanic_id",
                mechanicId,
                Service::fetchNext);
    }

    /**
     * Fetch all Services from the database matching the given mechanicId and
     * stateId.
     *
     * @param mechanicId the id of the mechanic Employee to fetch Services for
     * @param stateId    the id of the State to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByMechanicState(Integer mechanicId, Integer stateId) throws SQLException {
        return DatabaseHandler.fetchAllByTwoParams(getServiceByMechanicStateQuery,
                "mechanic_id",
                mechanicId,
                "state_id",
                stateId,
                Service::fetchNext);
    }

    /**
     * Fetch all Services from the database matching the given stateId.
     *
     * @param stateId the id of the State to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByState(Integer stateId) throws SQLException {
        return DatabaseHandler.fetchAllBy(getServiceByStateQuery,
                "state_id",
                stateId,
                Service::fetchNext);
    }

    /**
     * Save the Service in the database.
     *
     * @return Service or null
     */
    public Service save() throws SQLException {
        return DatabaseHandler.executeCreateStatement(createServiceQuery, this, Service::fetchNext);
    }

    /**
     * Update the Service in the database.
     *
     * @return Service or null
     */
    public Service update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateServiceQuery, this, Service::fetchNext);
    }

    /**
     * Increment the state of the Service.
     *
     * @return Service or null
     */
    public Service incrementState() throws SQLException {
        return DatabaseHandler.executeIncrementStateStatement(incrementStateQuery, this.id(), Service::fetchNext);
    }

    /**
     * Delete a Service from the database matching the given id.
     *
     * @param id the id of the Service to delete
     * @return true if successful
     */
    static public boolean delete(Integer id) throws SQLException {
        return DatabaseHandler.deleteById(deleteServiceQuery, id);
    }

}