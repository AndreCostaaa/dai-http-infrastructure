package dai.database;

import java.sql.*;

public record ServiceBill(int id,
        int price,
        boolean delivered,
        boolean paid,
        int discountPercentage) implements IEntity {

    static final String getAllQuery = "SELECT * FROM service_bill;",
            getByIdQuery = "SELECT * FROM service_bill WHERE id = :id;",
            updateQuery = "UPDATE service_bill SET price = :price, delivered = :delivered, paid = :paid, discount_percentage = :discount_percentage WHERE id = :id;",
            deleteQuery = "DELETE FROM service_bill WHERE id = :id;";

    private static ServiceBill fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        int id = resultSet.getInt("id");
        int price = resultSet.getInt("price");
        boolean delivered = resultSet.getBoolean("delivered");
        boolean paid = resultSet.getBoolean("paid");
        int discountPercentage = resultSet.getInt("discount_percentage");

        return new ServiceBill(id, price, delivered, paid, discountPercentage);
    }

    @Override
    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
    }

    @Override
    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        statement.setInt("id", id());
        statement.setInt("price", price());
        statement.setBoolean("delivered", delivered());
        statement.setBoolean("paid", paid());
        statement.setInt("discount_percentage", discountPercentage());
    }

    static public ServiceBill[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllQuery, ServiceBill::fetchNext);
    }

    static public ServiceBill fetchOne(int id) throws SQLException {
        return DatabaseHandler.fetchById(getByIdQuery, id, ServiceBill::fetchNext);
    }

    public ServiceBill update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateQuery, this, ServiceBill::fetchNext);
    }

    static public boolean delete(int id) throws SQLException {
        return DatabaseHandler.deleteById(deleteQuery, id);
    }

}
