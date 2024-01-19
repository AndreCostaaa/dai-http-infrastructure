package dai.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public record ServiceDisplay(int id,
                      Employee mechanic,
                      Client client,
                      Car car,
                      int hoursWorked,
                      String comments,
                      boolean hasPictures,
                      ServiceState state,
                      Date dateCreated,
                      Date dateCarArrival,
                      Date dateCarProcessing,
                      Date dateCarDone,
                      Date dateCarLeft,
                      ServiceState nextState)  {
    static ServiceDisplay fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        int id = resultSet.getInt("id");

        int mechanicId = resultSet.getInt("mechanic_id");
        String mechanicFirstName = resultSet.getString("mechanic_fname");
        String mechanicLastName = resultSet.getString("mechanic_lname");
        String mechanicPhone = resultSet.getString("mechanic_phone");
        int roleId = resultSet.getInt("role_id");
        int specializationId = resultSet.getInt("specialization_id");

        Employee mechanic = mechanicId == 0 ? null : new Employee(mechanicId, mechanicFirstName, mechanicLastName, mechanicPhone, roleId, specializationId);

        int clientId = resultSet.getInt("client_id");
        String clientFirstName = resultSet.getString("client_fname");
        String clientLastName = resultSet.getString("client_lname");
        String clientPhone = resultSet.getString("client_phone");
        String clientEmail = resultSet.getString("email");
        String street = resultSet.getString("street");
        int streetNo = resultSet.getInt("street_no");
        int npa = resultSet.getInt("npa");
        String country = resultSet.getString("country");

        Client client = new Client (clientId, clientFirstName, clientLastName, clientPhone, clientEmail, street, streetNo, npa, country);

        int carId = resultSet.getInt("car_id");
        int ownerId = resultSet.getInt("owner_id");
        String chassisNo = resultSet.getString("chassis_no");
        String recType = resultSet.getString("rec_type");
        String brand = resultSet.getString("brand");
        String model = resultSet.getString("model");
        String color = resultSet.getString("color");

        Car car = new Car(carId, ownerId, chassisNo, recType, brand, model, color);

        int hoursWorked = resultSet.getInt("hours_worked");
        String comments = resultSet.getString("comments");
        boolean hasPictures = resultSet.getBoolean("has_pictures");

        int stateId = resultSet.getInt("state_id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");

        ServiceState state = new ServiceState(stateId, title, description);

        Date[] dates = DatabaseHandler.getTimestampDates(resultSet);
        ServiceState nextState = ServiceState.fetchById(stateId + 1);

        return new ServiceDisplay(id,
                mechanic,
                client,
                car,
                hoursWorked,
                comments,
                hasPictures,
                state,
                dates[0],
                dates[1],
                dates[2],
                dates[3],
                dates[4],
                nextState);
    }
}
