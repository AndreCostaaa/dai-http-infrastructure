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

     static final String getAllQuery = "SELECT *, p1.fname AS mechanic_fname, p1.lname AS mechanic_lname, p1.phone_no AS mechanic_phone, p2.fname AS client_fname, p2.lname AS client_lname, p2.phone_no AS client_phone FROM service s LEFT JOIN employee e on s.mechanic_id = e.id LEFT JOIN person p1 ON e.id = p1.id JOIN client cl on s.client_id = cl.id JOIN person p2 ON cl.id = p2.id JOIN car c on s.car_id = c.id JOIN service_state ss on s.state_id = ss.id;",
               getServiceByIdQuery = "SELECT *, p1.fname AS mechanic_fname, p1.lname AS mechanic_lname, p1.phone_no AS mechanic_phone, p2.fname AS client_fname, p2.lname AS client_lname, p2.phone_no AS client_phone FROM service s LEFT JOIN employee e on s.mechanic_id = e.id LEFT JOIN person p1 ON e.id = p1.id JOIN client cl on s.client_id = cl.id JOIN person p2 ON cl.id = p2.id JOIN car c on s.car_id = c.id JOIN service_state ss on s.state_id = ss.id WHERE s.id = :id;",
               getServiceByCarQuery = "SELECT *, p1.fname AS mechanic_fname, p1.lname AS mechanic_lname, p1.phone_no AS mechanic_phone, p2.fname AS client_fname, p2.lname AS client_lname, p2.phone_no AS client_phone FROM service s LEFT JOIN employee e on s.mechanic_id = e.id LEFT JOIN person p1 ON e.id = p1.id JOIN client cl on s.client_id = cl.id JOIN person p2 ON cl.id = p2.id JOIN car c on s.car_id = c.id JOIN service_state ss on s.state_id = ss.id WHERE car_id = :car_id;",
               getServiceByCarStateQuery = "SELECT *, p1.fname AS mechanic_fname, p1.lname AS mechanic_lname, p1.phone_no AS mechanic_phone, p2.fname AS client_fname, p2.lname AS client_lname, p2.phone_no AS client_phone FROM service s LEFT JOIN employee e on s.mechanic_id = e.id LEFT JOIN person p1 ON e.id = p1.id JOIN client cl on s.client_id = cl.id JOIN person p2 ON cl.id = p2.id JOIN car c on s.car_id = c.id JOIN service_state ss on s.state_id = ss.id WHERE car_id = :car_id AND state_id = :state_id;",
               getServiceByMechanicQuery = "SELECT *, p1.fname AS mechanic_fname, p1.lname AS mechanic_lname, p1.phone_no AS mechanic_phone, p2.fname AS client_fname, p2.lname AS client_lname, p2.phone_no AS client_phone FROM service s LEFT JOIN employee e on s.mechanic_id = e.id LEFT JOIN person p1 ON e.id = p1.id JOIN client cl on s.client_id = cl.id JOIN person p2 ON cl.id = p2.id JOIN car c on s.car_id = c.id JOIN service_state ss on s.state_id = ss.id WHERE mechanic_id = :mechanic_id;",
               getServiceByMechanicStateQuery = "SELECT *, p1.fname AS mechanic_fname, p1.lname AS mechanic_lname, p1.phone_no AS mechanic_phone, p2.fname AS client_fname, p2.lname AS client_lname, p2.phone_no AS client_phone FROM service s LEFT JOIN employee e on s.mechanic_id = e.id LEFT JOIN person p1 ON e.id = p1.id JOIN client cl on s.client_id = cl.id JOIN person p2 ON cl.id = p2.id JOIN car c on s.car_id = c.id JOIN service_state ss on s.state_id = ss.id WHERE mechanic_id = :mechanic_id AND state_id = :state_id;",
               getServiceByStateQuery = "SELECT *, p1.fname AS mechanic_fname, p1.lname AS mechanic_lname, p1.phone_no AS mechanic_phone, p2.fname AS client_fname, p2.lname AS client_lname, p2.phone_no AS client_phone FROM service s LEFT JOIN employee e on s.mechanic_id = e.id LEFT JOIN person p1 ON e.id = p1.id JOIN client cl on s.client_id = cl.id JOIN person p2 ON cl.id = p2.id JOIN car c on s.car_id = c.id JOIN service_state ss on s.state_id = ss.id WHERE state_id = :state_id;",
               createServiceQuery = "INSERT INTO service (mechanic_id, client_id, car_id, hours_worked, comments, has_pictures, state_id, date_car_arrival, date_car_processing, date_car_done, date_car_left) VALUES (:mechanic_id, :client_id, :car_id, 0, '', false, 0, NULL, NULL, NULL, NULL);",
               updateServiceQuery = "UPDATE service SET mechanic_id  = :mechanic_id, hours_worked = :hours_worked, comments = :comments, has_pictures = :has_pictures WHERE id = :id;",
               incrementStateQuery = "UPDATE service SET state_id = state_id + 1 WHERE id = :id;",
               deleteServiceQuery = "DELETE FROM service WHERE id = :id;";

     /**
      * creates a Service with the parameters used for the insert query
      */
     public static Service serviceForCreate(Integer mechanicId, int clientId, int carId) {
          return new Service(0,
                    mechanicId,
                    clientId,
                    carId,
                    0,
                    "",
                    false,
                    0,
                    null,
                    null,
                    null,
                    null,
                    null);
     }

     /**
      * creates a Service with the parameters used for the update query
      * only these fields can be modified through update queries, except id, which
      * tells us
      * which service to update
      */
     public static Service serviceForUpdate(int id, Integer mechanicId, int hoursWorked, String comments,
               boolean hasPictures) {
          return new Service(id,
                    mechanicId,
                    0,
                    0,
                    hoursWorked,
                    comments,
                    hasPictures,
                    0,
                    null,
                    null,
                    null,
                    null,
                    null);
     }

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

          Date[] dates = new Date[5];
          String[] keys = { "date_created", "date_car_arrival", "date_car_processing", "date_car_done",
                    "date_car_left" };

          for (int i = 0; i < dates.length; ++i) {
               Timestamp ts = resultSet.getTimestamp(keys[i]);
               if (ts == null) {
                    continue;
               }
               dates[i] = new Date(ts.getTime());
          }
          return new Service(id,
                    mechanicId,
                    clientId,
                    carId,
                    hoursWorked,
                    comments,
                    hasPictures,
                    stateId,
                    dates[0],
                    dates[1],
                    dates[2],
                    dates[3],
                    dates[4]);
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
     static public ServiceDisplay[] fetchAll() throws SQLException {
          return DatabaseHandler.fetchAll(getAllQuery, ServiceDisplay::fetchNext);
     }

     /**
      * Fetch a Service from the database matching the given id.
      *
      * @param id the id of the Service to fetch
      * @return ServiceDisplay or null
      */
     static public ServiceDisplay fetchById(int id) throws SQLException {
          return DatabaseHandler.fetchById(getServiceByIdQuery, id, ServiceDisplay::fetchNext);
     }

     /**
      * Fetch all Services from the database matching the given carId.
      *
      * @param carId the id of the Car to fetch Services for
      * @return ServiceDisplay[] or null
      */
     static public ServiceDisplay[] fetchByCar(int carId) throws SQLException {
          return DatabaseHandler.fetchAllBy(getServiceByCarQuery,
                    "car_id",
                    carId,
                    ServiceDisplay::fetchNext);
     }

     /**
      * Fetch all Services from the database matching the given carId and stateId.
      *
      * @param carId   the id of the Car to fetch Services for
      * @param stateId the id of the State to fetch Services for
      * @return ServiceDisplay[] or null
      */
     static public ServiceDisplay[] fetchByCarState(int carId, int stateId) throws SQLException {
          return DatabaseHandler.fetchAllByTwoParams(getServiceByCarStateQuery,
                    "car_id",
                    carId,
                    "state_id",
                    stateId,
                    ServiceDisplay::fetchNext);
     }

     /**
      * Fetch all Services from the database matching the given mechanicId.
      *
      * @param mechanicId the id of the mechanic Employee to fetch Services for
      * @return ServiceDisplay[] or null
      */
     static public ServiceDisplay[] fetchByMechanic(int mechanicId) throws SQLException {
          return DatabaseHandler.fetchAllBy(getServiceByMechanicQuery,
                    "mechanic_id",
                    mechanicId,
                    ServiceDisplay::fetchNext);
     }

     /**
      * Fetch all Services from the database matching the given mechanicId and
      * stateId.
      *
      * @param mechanicId the id of the mechanic Employee to fetch Services for
      * @param stateId    the id of the State to fetch Services for
      * @return ServiceDisplay[] or null
      */
     static public ServiceDisplay[] fetchByMechanicState(int mechanicId, int stateId) throws SQLException {
          return DatabaseHandler.fetchAllByTwoParams(getServiceByMechanicStateQuery,
                    "mechanic_id",
                    mechanicId,
                    "state_id",
                    stateId,
                    ServiceDisplay::fetchNext);
     }

     /**
      * Fetch all Services from the database matching the given stateId.
      *
      * @param stateId the id of the State to fetch Services for
      * @return ServiceDisplay[] or null
      */
     static public ServiceDisplay[] fetchByState(int stateId) throws SQLException {
          return DatabaseHandler.fetchAllBy(getServiceByStateQuery,
                    "state_id",
                    stateId,
                    ServiceDisplay::fetchNext);
     }

     /**
      * Save the Service in the database.
      *
      * @return ServiceDisplay or null
      */
     public ServiceDisplay save() throws SQLException {
          int newId = DatabaseHandler.executeCreateStatement(createServiceQuery, this, Service::fetchNext).id;
          return fetchById(newId);
     }

     /**
      * Update the Service in the database.
      *
      * @return ServiceDisplay or null
      */
     public ServiceDisplay update() throws SQLException {
          DatabaseHandler.executeUpdateStatement(updateServiceQuery, this, Service::fetchNext);
          return fetchById(this.id);
     }

     /**
      * Increment the state of the Service.
      *
      * @return ServiceDisplay or null
      */
     public static ServiceDisplay incrementState(int id) throws SQLException {
          DatabaseHandler.executeIncrementStateStatement(incrementStateQuery, id);
          return fetchById(id);
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
