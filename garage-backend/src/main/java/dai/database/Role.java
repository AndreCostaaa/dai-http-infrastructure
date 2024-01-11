package dai.database;

import java.sql.*;

public record Role(int id,
        String name,
        boolean canCreate,
        boolean canAssignOthers,
        boolean isMechanic) implements IEntity {

    static final String getAllQuery = "SELECT * FROM role;",
            getRoleByIdQuery = "SELECT * FROM role WHERE id = :id;",
            createRoleQuery = "INSERT INTO role(name, can_create, can_assign_others, is_mechanic) VALUES (:name, :can_create, :can_assign_others, :is_mechanic);",
            updateRoleQuery = "UPDATE role SET name = :name, can_create = :can_create, can_assign_others = :can_assign_others, is_mechanic = :is_mechanic WHERE id = :id;",
            deleteRoleQuery = "DELETE FROM role WHERE id = :id;";

    private static Role fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        boolean canCreate = resultSet.getBoolean("can_create");
        boolean canAssignOthers = resultSet.getBoolean("can_assign_others");
        boolean isMechanic = resultSet.getBoolean("is_mechanic");

        return new Role(id, name, canCreate, canAssignOthers, isMechanic);
    }

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException {
        statement.setString("name", name());
        statement.setBoolean("can_create", canCreate());
        statement.setBoolean("can_assign_others", canAssignOthers());
        statement.setBoolean("is_mechanic", isMechanic());

    }

    @Override
    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
    }

    @Override
    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
        statement.setInt("id", id());
    }

    /**
     * Fetch all Roles from the database.
     *
     * @return Role[] or null
     */
    static public Role[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllQuery, Role::fetchNext);

    }

    /**
     * Fetch a Role from the database matching the given id.
     *
     * @param id the id of the Role to fetch
     * @return Role or null
     */
    static public Role fetchOne(int id) throws SQLException {
        return DatabaseHandler.fetchById(getRoleByIdQuery, id, Role::fetchNext);
    }

    /**
     * Save the Role in the database.
     *
     * @return Role or null
     */
    public Role save() throws SQLException {
        return DatabaseHandler.executeCreateStatement(createRoleQuery, this, Role::fetchNext);
    }

    /**
     * Update the Role in the database.
     *
     * @return Role or null
     */
    public Role update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateRoleQuery, this, Role::fetchNext);

    }

    /**
     * Delete a Role from the database matching the given id.
     *
     * @param id the id of the Role to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        return DatabaseHandler.deleteById(deleteRoleQuery, id);
    }
}
