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
                      int dateCarLeft) {

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

    /**
     * Fetch all Services from the database.
     * @return Service[] or null
     */
    static public Service[] fetchAll() throws SQLException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(getAllQuery)) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Service[] services = new Service[count];
                int i = 0;

                while (resultSet.next()) {
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

                    services[i++] = new Service(id, mechanicId, clientId, carId, hoursWorked, comments, hasPictures, stateId, dateCreated, dateCarArrival, dateCarProcessing, dateCarDone, dateCarLeft);
                }

                return services;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch a Service from the database matching the given id.
     * @param id the id of the Service to fetch
     * @return Service or null
     */
    static public Service fetchOne(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getServiceByIdQuery)) {
            callableStatement.setInt("id", id);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
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

                    return new Service(id, mechanicId, clientId, carId, hoursWorked, comments, hasPictures, stateId, dateCreated, dateCarArrival, dateCarProcessing, dateCarDone, dateCarLeft);
                } else
                    return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all Services from the database matching the given carId.
     * @param carId the id of the Car to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByCar(int carId) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getServiceByCarQuery)) {
            callableStatement.setInt("car_id", carId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Service[] services = new Service[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int mechanicId = resultSet.getInt("mechanic_id");
                    int clientId = resultSet.getInt("client_id");
                    int hoursWorked = resultSet.getInt("hours_worked");
                    String comments = resultSet.getString("comments");
                    boolean hasPictures = resultSet.getBoolean("has_pictures");
                    int stateId = resultSet.getInt("state_id");
                    int dateCreated = resultSet.getInt("date_created");
                    int dateCarArrival = resultSet.getInt("date_car_arrival");
                    int dateCarProcessing = resultSet.getInt("date_car_processing");
                    int dateCarDone = resultSet.getInt("date_car_done");
                    int dateCarLeft = resultSet.getInt("date_car_left");

                    services[i++] = new Service(id, mechanicId, clientId, carId, hoursWorked, comments, hasPictures, stateId, dateCreated, dateCarArrival, dateCarProcessing, dateCarDone, dateCarLeft);
                }

                return services;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all Services from the database matching the given carId and stateId.
     * @param carId the id of the Car to fetch Services for
     * @param stateId the id of the State to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByCarState(int carId, int stateId) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getServiceByCarStateQuery)) {
            callableStatement.setInt("car_id", carId);
            callableStatement.setInt("state_id", stateId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Service[] services = new Service[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int mechanicId = resultSet.getInt("mechanic_id");
                    int clientId = resultSet.getInt("client_id");
                    int hoursWorked = resultSet.getInt("hours_worked");
                    String comments = resultSet.getString("comments");
                    boolean hasPictures = resultSet.getBoolean("has_pictures");
                    int dateCreated = resultSet.getInt("date_created");
                    int dateCarArrival = resultSet.getInt("date_car_arrival");
                    int dateCarProcessing = resultSet.getInt("date_car_processing");
                    int dateCarDone = resultSet.getInt("date_car_done");
                    int dateCarLeft = resultSet.getInt("date_car_left");

                    services[i++] = new Service(id, mechanicId, clientId, carId, hoursWorked, comments, hasPictures, stateId, dateCreated, dateCarArrival, dateCarProcessing, dateCarDone, dateCarLeft);
                }

                return services;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all Services from the database matching the given mechanicId.
     * @param mechanicId the id of the mechanic Employee to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByMechanic(int mechanicId) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getServiceByMechanicQuery)) {
            callableStatement.setInt("mechanic_id", mechanicId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Service[] services = new Service[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
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

                    services[i++] = new Service(id, mechanicId, clientId, carId, hoursWorked, comments, hasPictures, stateId, dateCreated, dateCarArrival, dateCarProcessing, dateCarDone, dateCarLeft);
                }

                return services;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all Services from the database matching the given mechanicId and stateId.
     * @param mechanicId the id of the mechanic Employee to fetch Services for
     * @param stateId the id of the State to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByMechanicState(int mechanicId, int stateId) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getServiceByMechanicStateQuery)) {
            callableStatement.setInt("mechanic_id", mechanicId);
            callableStatement.setInt("state_id", stateId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Service[] services = new Service[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int clientId = resultSet.getInt("client_id");
                    int carId = resultSet.getInt("car_id");
                    int hoursWorked = resultSet.getInt("hours_worked");
                    String comments = resultSet.getString("comments");
                    boolean hasPictures = resultSet.getBoolean("has_pictures");
                    int dateCreated = resultSet.getInt("date_created");
                    int dateCarArrival = resultSet.getInt("date_car_arrival");
                    int dateCarProcessing = resultSet.getInt("date_car_processing");
                    int dateCarDone = resultSet.getInt("date_car_done");
                    int dateCarLeft = resultSet.getInt("date_car_left");

                    services[i++] = new Service(id, mechanicId, clientId, carId, hoursWorked, comments, hasPictures, stateId, dateCreated, dateCarArrival, dateCarProcessing, dateCarDone, dateCarLeft);
                }

                return services;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all Services from the database matching the given stateId.
     * @param stateId the id of the State to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByState(int stateId) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getServiceByStateQuery)) {
            callableStatement.setInt("state_id", stateId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Service[] services = new Service[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int mechanicId = resultSet.getInt("mechanic_id");
                    int clientId = resultSet.getInt("client_id");
                    int carId = resultSet.getInt("car_id");
                    int hoursWorked = resultSet.getInt("hours_worked");
                    String comments = resultSet.getString("comments");
                    boolean hasPictures = resultSet.getBoolean("has_pictures");
                    int dateCreated = resultSet.getInt("date_created");
                    int dateCarArrival = resultSet.getInt("date_car_arrival");
                    int dateCarProcessing = resultSet.getInt("date_car_processing");
                    int dateCarDone = resultSet.getInt("date_car_done");
                    int dateCarLeft = resultSet.getInt("date_car_left");

                    services[i++] = new Service(id, mechanicId, clientId, carId, hoursWorked, comments, hasPictures, stateId, dateCreated, dateCarArrival, dateCarProcessing, dateCarDone, dateCarLeft);
                }

                return services;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all the processing Services from the database matching the given mechanicId.
     * @param mechanicId the id of the mechanic Employee to fetch Services for
     * @return Service[] or null
     */
    static public Service[] fetchByMechanicProcessing(int mechanicId) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getServiceByMechanicProcessingQuery)) {
            callableStatement.setInt("mechanic_id", mechanicId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Service[] services = new Service[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int clientId = resultSet.getInt("client_id");
                    int carId = resultSet.getInt("car_id");
                    int hoursWorked = resultSet.getInt("hours_worked");
                    String comments = resultSet.getString("comments");
                    boolean hasPictures = resultSet.getBoolean("has_pictures");
                    int dateCreated = resultSet.getInt("date_created");
                    int dateCarArrival = resultSet.getInt("date_car_arrival");
                    int dateCarProcessing = resultSet.getInt("date_car_processing");
                    int dateCarDone = resultSet.getInt("date_car_done");
                    int dateCarLeft = resultSet.getInt("date_car_left");

                    services[i++] = new Service(id, mechanicId, clientId, carId, hoursWorked, comments, hasPictures, 2, dateCreated, dateCarArrival, dateCarProcessing, dateCarDone, dateCarLeft);
                }

                return services;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all the created Services from the database.
     * @return Service[] or null
     */
    static public Service[] fetchCreated() throws SQLException { return fetchByState(0); }

    /**
     * Fetch all the waiting Services from the database.
     * @return Service[] or null
     */
    static public Service[] fetchWaiting() throws SQLException { return fetchByState(1); }

    /**
     * Fetch all the processing Services from the database.
     * @return Service[] or null
     */
    static public Service[] fetchProcessing() throws SQLException { return fetchByState(2); }

    /**
     * Fetch all the done Services from the database.
     * @return Service[] or null
     */
    static public Service[] fetchDone() throws SQLException { return fetchByState(3); }

    /**
     * Fetch all the left Services from the database.
     * @return Service[] or null
     */
    static public Service[] fetchLeft() throws SQLException { return fetchByState(4); }

    /**
     * Save the Service in the database.
     * @return true if successful
     */
    public boolean save() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(createServiceQuery)) {
            callableStatement.setInt("mechanic_id", mechanicId());
            callableStatement.setInt("client_id", clientId());
            callableStatement.setInt("car_id", carId());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Update the Service in the database.
     * @return true if successful
     */
    public boolean update() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(updateServiceQuery)) {
            callableStatement.setInt("id", id());
            callableStatement.setInt("mechanic_id", mechanicId());
            callableStatement.setInt("hours_worked", hoursWorked());
            callableStatement.setString("comments", comments());
            callableStatement.setBoolean("has_pictures", hasPictures());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Increment the state of the Service.
     * @return true if successful
     */
    public boolean incrementState() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(incrementStateQuery)) {
            callableStatement.setInt("id", id());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Delete a Service from the database matching the given id.
     * @param id the id of the Service to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(deleteServiceQuery)) {
            callableStatement.setInt("id", id);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
