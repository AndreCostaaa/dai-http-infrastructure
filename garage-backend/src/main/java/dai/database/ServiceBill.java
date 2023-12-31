package dai.database;

import java.sql.*;
import java.util.concurrent.Callable;

public record ServiceBill(int id,
                          int price,
                          boolean delivered,
                          boolean paid,
                          int discountPercentage) {

    static Connection con;

    static final String getAllQuery = "SELECT * FROM service_bill",
                        getByIdQuery = "SELECT * FROM service_bill WHERE id = :id",
                        updateQuery = "UPDATE service_bill SET price = :price, delivered = :delivered, paid = :paid, discount_percentage = :discount_percentage WHERE id = :id",
                        deleteQuery = "DELETE FROM service_bill WHERE id = :id";

    static public ServiceBill[] fetchAll() throws SQLException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(getAllQuery)) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                ServiceBill[] serviceBills = new ServiceBill[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int price = resultSet.getInt("price");
                    boolean delivered = resultSet.getBoolean("delivered");
                    boolean paid = resultSet.getBoolean("paid");
                    int discountPercentage = resultSet.getInt("discount_percentage");

                    serviceBills[i++] = new ServiceBill(id, price, delivered, paid, discountPercentage);
                }

                return serviceBills;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    static public ServiceBill fetchOne(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getByIdQuery)) {
            callableStatement.setInt("id", id);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    int price = resultSet.getInt("price");
                    boolean delivered = resultSet.getBoolean("delivered");
                    boolean paid = resultSet.getBoolean("paid");
                    int discountPercentage = resultSet.getInt("discount_percentage");

                    return new ServiceBill(id, price, delivered, paid, discountPercentage);
                } else
                    return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public boolean update() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(updateQuery)) {
            callableStatement.setInt("id", id());
            callableStatement.setInt("price", price());
            callableStatement.setBoolean("delivered", delivered());
            callableStatement.setBoolean("paid", paid());
            callableStatement.setInt("discountPercentage", discountPercentage());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    static public boolean delete(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(deleteQuery)) {
            callableStatement.setInt("id", id);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
