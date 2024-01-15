package dai.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public record ServiceDisplay(int id,
                      int mechanicId,
                      String mechanicFirstName,
                      String mechanicLastName,
                      String mechanicPhone,
                      int roleId,
                      int specializationId,
                      int clientId,
                      String clientFirstName,
                      String clientLastName,
                      String clientPhone,
                      String clientEmail,
                      String street,
                      int streetNo,
                      int npa,
                      String country,
                      int carId,
                      int ownerId,
                      String chassisNo,
                      String recType,
                      String brand,
                      String model,
                      String color,
                      int hoursWorked,
                      String comments,
                      boolean hasPictures,
                      int stateId,
                      String title,
                      String description,
                      Date dateCreated,
                      Date dateCarArrival,
                      Date dateCarProcessing,
                      Date dateCarDone,
                      Date dateCarLeft)  {
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

        int clientId = resultSet.getInt("client_id");

        String clientFirstName = resultSet.getString("client_fname");
        String clientLastName = resultSet.getString("client_lname");
        String clientPhone = resultSet.getString("client_phone");
        String clientEmail = resultSet.getString("email");
        String street = resultSet.getString("street");
        int streetNo = resultSet.getInt("street_no");
        int npa = resultSet.getInt("npa");
        String country = resultSet.getString("country");

        int carId = resultSet.getInt("car_id");

        int ownerId = resultSet.getInt("owner_id");
        String chassisNo = resultSet.getString("chassis_no");
        String recType = resultSet.getString("rec_type");
        String brand = resultSet.getString("brand");
        String model = resultSet.getString("model");
        String color = resultSet.getString("color");

        int hoursWorked = resultSet.getInt("hours_worked");
        String comments = resultSet.getString("comments");
        boolean hasPictures = resultSet.getBoolean("has_pictures");
        int stateId = resultSet.getInt("state_id");

        String title = resultSet.getString("title");
        String description = resultSet.getString("description");

        Date dateCreated = resultSet.getDate("date_created");
        Date dateCarArrival = resultSet.getDate("date_car_arrival");
        Date dateCarProcessing = resultSet.getDate("date_car_processing");
        Date dateCarDone = resultSet.getDate("date_car_done");
        Date dateCarLeft = resultSet.getDate("date_car_left");

        return new ServiceDisplay(id,
                mechanicId,
                mechanicFirstName,
                mechanicLastName,
                mechanicPhone,
                roleId,
                specializationId,
                clientId,
                clientFirstName,
                clientLastName,
                clientPhone,
                clientEmail,
                street,
                streetNo,
                npa,
                country,
                carId,
                ownerId,
                chassisNo,
                recType,
                brand,
                model,
                color,
                hoursWorked,
                comments,
                hasPictures,
                stateId,
                title,
                description,
                dateCreated,
                dateCarArrival,
                dateCarProcessing,
                dateCarDone,
                dateCarLeft);
    }
}
