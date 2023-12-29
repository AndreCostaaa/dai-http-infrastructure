package dai.database;

import java.sql.*;

@SuppressWarnings("DuplicatedCode")
public record Car(int id,
                  int ownerId,
                  String chassisNo,
                  String recType,
                  String brand,
                  String model,
                  String color) {

    static Connection con;

    static final String getAllQuery = "SELECT * FROM car;",
                        getCarByIdQuery = "SELECT * FROM car WHERE id = :id;",
                        createCarQuery = "INSERT INTO car(owner_id, chassis_no, rec_type, brand, model, color) VALUES (:owner_id, :chassis_no, :rec_type, :brand, :model, :color);",
                        updateCarQuery = "UPDATE car SET owner_id = :owner_id, chassis_no = :chassis_no, rec_type = :rec_type, brand = :brand, model = :model, color = :color WHERE id = :id;",
                        deleteCarQuery = "DELETE FROM car WHERE id = :id;";

    static public Car fetchOne(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getCarByIdQuery)) {
            callableStatement.setInt(1, id);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    int ownerId = resultSet.getInt("owner_id");
                    String chassisNo = resultSet.getString("chassis_no");
                    String recType = resultSet.getString("rec_type");
                    String brand = resultSet.getString("brand");
                    String model = resultSet.getString("model");
                    String color = resultSet.getString("color");

                    return new Car(id, ownerId, chassisNo, recType, brand, model, color);
                } else
                    return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    static public Car[] fetchAll() throws SQLException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(getAllQuery)) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Car[] cars = new Car[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int ownerId = resultSet.getInt("owner_id");
                    String chassisNo = resultSet.getString("chassis_no");
                    String recType = resultSet.getString("rec_type");
                    String brand = resultSet.getString("brand");
                    String model = resultSet.getString("model");
                    String color = resultSet.getString("color");

                    cars[i++] = new Car(id, ownerId, chassisNo, recType, brand, model, color);
                }

                return cars;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public boolean save() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(createCarQuery)) {
            callableStatement.setInt(1, ownerId);
            callableStatement.setString(2, chassisNo);
            callableStatement.setString(3, recType);
            callableStatement.setString(4, brand);
            callableStatement.setString(5, model);
            callableStatement.setString(6, color);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public boolean update() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(updateCarQuery)) {
            callableStatement.setInt(1, ownerId);
            callableStatement.setString(2, chassisNo);
            callableStatement.setString(3, recType);
            callableStatement.setString(4, brand);
            callableStatement.setString(5, model);
            callableStatement.setString(6, color);
            callableStatement.setInt(7, id);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    static public boolean delete(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(deleteCarQuery)) {
            callableStatement.setInt(1, id);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
