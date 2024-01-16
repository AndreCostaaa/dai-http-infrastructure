package dai.database;

import java.sql.*;

public record ServiceBill(Integer id,
        double price,
        boolean delivered,
        boolean paid,
        Integer discountPercentage) implements IEntity {

    static final String getAllQuery = "SELECT * FROM service_bill;",
            getByIdQuery = "SELECT * FROM service_bill WHERE id = :id;",
            updateQuery = "UPDATE service_bill SET price = :price, delivered = :delivered, paid = :paid, discount_percentage = :discount_percentage WHERE id = :id;",
            deleteQuery = "DELETE FROM service_bill WHERE id = :id;";

    private static ServiceBill fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        Integer id = resultSet.getObject("id", Integer.class);
        double price = resultSet.getDouble("price");
        boolean delivered = resultSet.getBoolean("delivered");
        boolean paid = resultSet.getBoolean("paid");
        Integer discountPercentage = resultSet.getObject("discount_percentage", Integer.class);

        return new ServiceBill(id, price, delivered, paid, discountPercentage);
    }

    @Override
    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
    }

    @Override
    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        statement.setInt("id", id());
        statement.setDouble("price", price());
        statement.setBoolean("delivered", delivered());
        statement.setBoolean("paid", paid());
        DatabaseHandler.checkIfNull(discountPercentage, discountPercentage(), statement, "discount_percentage", Types.INTEGER);
    }

    static public ServiceBill[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllQuery, ServiceBill::fetchNext);
    }

    static public ServiceBill fetchOne(Integer id) throws SQLException {
        return DatabaseHandler.fetchById(getByIdQuery, id, ServiceBill::fetchNext);
    }

    public ServiceBill update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateQuery, this, ServiceBill::fetchNext);
    }

    static public boolean delete(Integer id) throws SQLException {
        return DatabaseHandler.deleteById(deleteQuery, id);
    }

}
