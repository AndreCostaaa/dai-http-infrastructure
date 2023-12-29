package dai.database;

import java.sql.*;

@SuppressWarnings("DuplicatedCode")
public record CarPart(int id,
                      int serviceId,
                      String supplier,
                      int supplierRef,
                      String name,
                      String description,
                      double buyPrice,
                      double sellPrice) {

    static Connection con;

    static final String getAllQuery = "SELECT * FROM car_part;",
                        getCarPartByIdQuery = "SELECT * FROM car_part WHERE id = :id;",
                        createCarPartQuery = "INSERT INTO car_part(service_id, supplier, supplier_ref, name, description, buy_price, sell_price) VALUES (:service_id, :supplier, :supplier_ref, :name, :description, :buy_price, :sell_price);",
                        updateCarPartQuery = "UPDATE car_part SET service_id = :service_id, supplier = :supplier, supplier_ref = :supplier_ref, name = :name, description = :description, buy_price = :buy_price, sell_price = :sell_price WHERE id = :id;",
                        deleteCarPartQuery = "DELETE FROM car_part WHERE id = :id;";

    /**
     * Fetch a CarPart from the database matching the given id.
     * @param id the id of the CarPart to fetch
     * @return CarPart or null
     */
    static public CarPart fetchOne(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getCarPartByIdQuery)) {
            callableStatement.setInt(1, id);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    int serviceId = resultSet.getInt("service_id");
                    String supplier = resultSet.getString("supplier");
                    int supplierRef = resultSet.getInt("supplier_ref");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    double buyPrice = resultSet.getDouble("buy_price");
                    double sellPrice = resultSet.getDouble("sell_price");

                    return new CarPart(id, serviceId, supplier, supplierRef, name, description, buyPrice, sellPrice);
                } else
                    return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all CarParts from the database.
     * @return CarPart[] or null
     */
    static public CarPart[] fetchAll() throws SQLException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(getAllQuery)) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                CarPart[] carParts = new CarPart[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int serviceId = resultSet.getInt("service_id");
                    String supplier = resultSet.getString("supplier");
                    int supplierRef = resultSet.getInt("supplier_ref");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    double buyPrice = resultSet.getDouble("buy_price");
                    double sellPrice = resultSet.getDouble("sell_price");

                    carParts[i++] = new CarPart(id, serviceId, supplier, supplierRef, name, description, buyPrice, sellPrice);
                }

                return carParts;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Save the CarPart in the database.
     * @return true if successful
     */
    public boolean save() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(createCarPartQuery)) {
            callableStatement.setInt(1, serviceId());
            callableStatement.setString(2, supplier());
            callableStatement.setInt(3, supplierRef());
            callableStatement.setString(4, name());
            callableStatement.setString(5, description());
            callableStatement.setDouble(6, buyPrice());
            callableStatement.setDouble(7, sellPrice());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Update the CarPart in the database.
     * @return true if successful
     */
    public boolean update() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(updateCarPartQuery)) {
            callableStatement.setInt(1, serviceId());
            callableStatement.setString(2, supplier());
            callableStatement.setInt(3, supplierRef());
            callableStatement.setString(4, name());
            callableStatement.setString(5, description());
            callableStatement.setDouble(6, buyPrice());
            callableStatement.setDouble(7, sellPrice());
            callableStatement.setInt(8, id());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Delete a CarPart from the database matching the given id.
     * @param id the id of the CarPart to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(deleteCarPartQuery)) {
            callableStatement.setInt(1, id);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
