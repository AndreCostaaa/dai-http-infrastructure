package dai.database;

import java.sql.*;
import java.util.Objects;

public record CarPart(int id,
        Integer serviceId,
        String supplier,
        String supplierRef,
        String name,
        String description,
        double buyPrice,
        double sellPrice) implements IEntity {

    public CarPart(int id,
            Integer serviceId,
            String supplier,
            String supplierRef,
            String name,
            String description,
            double buyPrice,
            double sellPrice) {
        this.id = id;
        this.serviceId = Objects.requireNonNullElse(serviceId, 0);
        this.supplier = supplier;
        this.supplierRef = supplierRef;
        this.name = name;
        this.description = description;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    static final String getAllQuery = "SELECT * FROM car_part;",
            getCarPartByIdQuery = "SELECT * FROM car_part WHERE id = :id;",
            createCarPartQuery = "INSERT INTO car_part(service_id, supplier, supplier_ref, name, description, buy_price, sell_price) VALUES (:service_id, :supplier, :supplier_ref, :name, :description, :buy_price, :sell_price);",
            updateCarPartQuery = "UPDATE car_part SET service_id = :service_id, supplier = :supplier, supplier_ref = :supplier_ref, name = :name, description = :description, buy_price = :buy_price, sell_price = :sell_price WHERE id = :id;",
            deleteCarPartQuery = "DELETE FROM car_part WHERE id = :id;";

    static private CarPart fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        int id = resultSet.getInt("id");
    Integer serviceId = resultSet.getObject("service_id", Integer.class);
        String supplier = resultSet.getString("supplier");
        String supplierRef = resultSet.getString("supplier_ref");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        double buyPrice = resultSet.getDouble("buy_price");
        double sellPrice = resultSet.getDouble("sell_price");

        return new CarPart(id, serviceId, supplier, supplierRef, name, description, buyPrice, sellPrice);
    }

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException {
        if (serviceId() == 0)
            statement.setNull("service_id", Types.INTEGER);
        else
            statement.setInt("service_id", serviceId());
        statement.setString("supplier", supplier());
        statement.setString("supplier_ref", supplierRef());
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
     * @return CarPart or null
     */
    public CarPart save() throws SQLException {
        return DatabaseHandler.executeCreateStatement(createCarPartQuery, this, CarPart::fetchNext);
    }

    /**
     * Update the CarPart in the database.
     *
     * @return CarPart or null
     */
    public CarPart update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateCarPartQuery, this, CarPart::fetchNext);
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
