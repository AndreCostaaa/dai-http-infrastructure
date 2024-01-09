package dai.database;

import java.sql.*;

public record Service(int id,
        int mechanicId,
        int clientId,
        int carId,
        int hoursWorked,
        String comments,
        boolean hasPictures,
        int stateId,
        int dateCreated,
        int dateCarArrival,
        int dateCarProcessing,
        int dateCarDone,
        int dateCarLeft) implements IEntity {

    static Connection con;

    static final String getAllQuery = "SELECT * FROM service;",
            getServiceByIdQuery = "SELECT * FROM service WHERE id = :id;",
            getServiceByCarQuery = "SELECT * FROM service WHERE car_id = :car_id;",
            getServiceByCarStateQuery = "SELECT * FROM service WHERE car_id = :car_id AND state_id = :state_id;",
            getServiceByMechanicQuery = "SELECT * FROM service WHERE mechanic_id = :mechanic_id;",
            getServiceByMechanicStateQuery = "SELECT * FROM service WHERE mechanic_id = :mechanic_id AND state_id = :state_id;",
            getServiceByStateQuery = "SELECT * FROM service WHERE state_id = :state_id;",
            getServiceByMechanicProcessingQuery = "SELECT * FROM service WHERE mechanic_id = :mechanic_id AND state_id = 2;",
            createServiceQuery = "INSERT INTO service (mechanic_id, client_id, car_id, hours_worked, comments, has_pictures, state_id, date_car_arrival, date_car_processing, date_car_done, date_car_left) VALUES (:mechanic_id, :client_id, :car_id, 0, '', false, 0, now(), NULL, NULL, NULL);",
            updateServiceQuery = "UPDATE service SET mechanic_id  = :mechanic_id, hours_worked = :hours_worked, comments = :comments, has_pictures = :has_pictures WHERE id = :id;",
            incrementStateQuery = "WITH service_state AS (SELECT state_id FROM service WHERE id = :id) UPDATE service SET state_id = service_state + 1 WHERE id = :id;",
            deleteServiceQuery = "DELETE FROM service WHERE id = :id;";


    @Override
    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
    }

    @Override
    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
        statement.setInt("id", id());
    }

    private static Service fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        int id = resultSet.getInt("id");
        int mechanicId = resultSet.getInt("mechanic_id");
        int clientId = resultSet.getInt("client_id");
        int carId = resultSet.getInt("car_id");
        int hoursWorked = resultSet.getInt("hours_worked");
        String comments = resultSet.getString("comments");
        boolean hasPictures = resultSet.getBoolean("has_pictures");
        int stateId = resultSet.getInt("state_id");
        int dateCreated = resultSet.getInt("date_created");
        int dateCarArrival = resultSet.getInt("date_car_arrival");
        int dateCarProcessing = resultSet.getInt("date_car_processing");
        int dateCarDone = resultSet.getInt("date_car_done");
        int dateCarLeft = resultSet.getInt("date_car_left");

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

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException {
        statement.setInt("mechanic_id", mechanicId());
        statement.setInt("client_id", clientId());
        statement.setInt("car_id", carId());
        statement.setInt("hours_worked", hoursWorked());
        statement.setString("comments", comments());
        statement.setBoolean("has_pictures", hasPictures());
        statement.setInt("state_id", stateId());
        statement.setInt("date_created", dateCreated());
        statement.setInt("date_car_arrival", dateCarArrival());
        statement.setInt("date_car_processing", dateCarProcessing());
        statement.setInt("date_car_done", dateCarDone());
        statement.setInt("date_car_left", dateCarLeft());
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
    static public Service fetchById(int id) throws SQLException {
        return DatabaseHandler.fetchById(getServiceByIdQuery, id, Service::fetchNext);
    }

    /**
     * Fetch all Services from the database matching the given carId.
     * 
     * @param carId the id of the Car to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByCar(int carId) throws SQLException {
        return DatabaseHandler.fetchAllBy(getServiceByCarQuery,
                "car_id",
                String.valueOf(carId),
                Service::fetchNext);
    }

    /**
     * Fetch all Services from the database matching the given carId and stateId.
     * 
     * @param carId   the id of the Car to fetch Services for
     * @param stateId the id of the State to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByCarState(int carId, int stateId) throws SQLException {
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
    static public Service[] fetchByMechanic(int mechanicId) throws SQLException {
        return DatabaseHandler.fetchAllBy(getServiceByMechanicQuery,
                "mechanic_id",
                String.valueOf(mechanicId),
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
    static public Service[] fetchByMechanicState(int mechanicId, int stateId) throws SQLException {
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
    static public Service[] fetchByState(int stateId) throws SQLException {
        return DatabaseHandler.fetchAllBy(getServiceByStateQuery,
                "state_id",
                String.valueOf(stateId),
                Service::fetchNext);
    }

    /**
     * Fetch all the processing Services from the database matching the given
     * mechanicId.
     * 
     * @param mechanicId the id of the mechanic Employee to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByMechanicProcessing(int mechanicId) throws SQLException {
        return DatabaseHandler.fetchAllBy(getServiceByMechanicProcessingQuery,
                "mechanic_id",
                String.valueOf(mechanicId),
                Service::fetchNext);
    }

    /**
     * Save the Service in the database.
     * 
     * @return true if successful
     */
    public Service save() throws SQLException {
        return DatabaseHandler.executeCreateStatement(createServiceQuery, this, Service::fetchNext);
    }

    /**
     * Update the Service in the database.
     * 
     * @return true if successful
     */
    public Service update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateServiceQuery, this, Service::fetchNext);
    }

    /**
     * Increment the state of the Service.
     * 
     * @return true if successful
     */
    public Service incrementState() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(incrementStateQuery, this,Service::fetchNext);
    }

    /**
     * Delete a Service from the database matching the given id.
     * 
     * @param id the id of the Service to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        return DatabaseHandler.deleteById(deleteServiceQuery, id);
    }


}
