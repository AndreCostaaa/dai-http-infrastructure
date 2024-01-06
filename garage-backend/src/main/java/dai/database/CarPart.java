package dai.database;

import java.sql.*;

public record CarPart(int id,
        int serviceId,
        String supplier,
        int supplierRef,
        String name,
        String description,
        double buyPrice,
        double sellPrice) implements IEntity {

    static final String getAllQuery = "SELECT * FROM car_part;",
            getCarPartByIdQuery = "SELECT * FROM car_part WHERE id = :id;",
            createCarPartQuery = "INSERT INTO car_part(service_id, supplier, supplier_ref, name, description, buy_price, sell_price) VALUES (:service_id, :supplier, :supplier_ref, :name, :description, :buy_price, :sell_price);",
            updateCarPartQuery = "UPDATE car_part SET service_id = :service_id, supplier = :supplier, supplier_ref = :supplier_ref, name = :name, description = :description, buy_price = :buy_price, sell_price = :sell_price WHERE id = :id;",
            deleteCarPartQuery = "DELETE FROM car_part WHERE id = :id;";

    static private CarPart fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        int id = resultSet.getInt("id");
        int serviceId = resultSet.getInt("service_id");
        String supplier = resultSet.getString("supplier");
        int supplierRef = resultSet.getInt("supplier_ref");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        double buyPrice = resultSet.getDouble("buy_price");
        double sellPrice = resultSet.getDouble("sell_price");

        return new CarPart(id, serviceId, supplier, supplierRef, name, description, buyPrice, sellPrice);
    }

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException {
        statement.setInt("service_id", serviceId());
        statement.setString("supplier", supplier());
        statement.setInt("supplier_ref", supplierRef());
        statement.setString("name", name());
        statement.setString("description", description());
        statement.setDouble("buy_price", buyPrice());
        statement.setDouble("sell_price", sellPrice());
    }

    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
        statement.setInt("id", id());
    }

    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
    }

    /**
     * Fetch a CarPart from the database matching the given id.
     *
     * @param id the id of the CarPart to fetch
     * @return CarPart or null
     */
    static public CarPart fetchOne(int id) throws SQLException {
        return DatabaseHandler.fetchById(getCarPartByIdQuery, id, CarPart::fetchNext);
    }

    /**
     * Fetch all CarParts from the database.
     *
     * @return CarPart[] or null
     */
    static public CarPart[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllQuery, CarPart::fetchNext);
    }

    /**
     * Save the CarPart in the database.
     *
     * @return true if successful
     */
    public boolean save() throws SQLException {
        return DatabaseHandler.executeCreateStatement(updateCarPartQuery, this);

    }

    /**
     * Update the CarPart in the database.
     *
     * @return true if successful
     */
    public boolean update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateCarPartQuery, this);
    }

    /**
     * Delete a CarPart from the database matching the given id.
     *
     * @param id the id of the CarPart to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        return DatabaseHandler.deleteById(deleteCarPartQuery, id);
    }
}
