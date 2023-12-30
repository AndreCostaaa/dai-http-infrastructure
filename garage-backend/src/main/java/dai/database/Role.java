package dai.database;

import java.sql.*;

public record Role(int id,
                   String name,
                   boolean canCreate,
                   boolean canAssignOthers,
                   boolean isMechanic) {

    static Connection con;

    static final String getAllQuery = "SELECT * FROM role;",
                        getRoleByIdQuery = "SELECT * FROM role WHERE id = :id;",
                        createRoleQuery = "INSERT INTO role(name, can_create, can_assign_others, is_mechanic) VALUES (:name, :can_create, :can_assign_others, :is_mechanic);",
                        updateRoleQuery = "UPDATE role SET name = :name, can_create = :can_create, can_assign_others = :can_assign_others, is_mechanic = :is_mechanic WHERE id = :id;",
                        deleteRoleQuery = "DELETE FROM role WHERE id = :id;";

    /**
     * Fetch a Role from the database matching the given id.
     * @param id the id of the Role to fetch
     * @return Role or null
     */
    static public Role fetchOne(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getRoleByIdQuery)) {
            callableStatement.setInt("id", id);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    boolean canCreate = resultSet.getBoolean("can_create");
                    boolean canAssignOthers = resultSet.getBoolean("can_assign_others");
                    boolean isMechanic = resultSet.getBoolean("is_mechanic");

                    return new Role(id, name, canCreate, canAssignOthers, isMechanic);
                } else
                    return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all Roles from the database.
     * @return Role[] or null
     */
    static public Role[] fetchAll() throws SQLException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(getAllQuery)) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Role[] roles = new Role[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    boolean canCreate = resultSet.getBoolean("can_create");
                    boolean canAssignOthers = resultSet.getBoolean("can_assign_others");
                    boolean isMechanic = resultSet.getBoolean("is_mechanic");

                    roles[i++] = new Role(id, name, canCreate, canAssignOthers, isMechanic);
                }

                return roles;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Save the Role in the database.
     * @return true if successful
     */
    public boolean save() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(createRoleQuery)) {
            callableStatement.setString("name", name());
            callableStatement.setBoolean("can_create", canCreate());
            callableStatement.setBoolean("can_assign_others", canAssignOthers());
            callableStatement.setBoolean("is_mechanic", isMechanic());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Update the Role in the database.
     * @return true if successful
     */
    public boolean update() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(updateRoleQuery)) {
            callableStatement.setString("name", name());
            callableStatement.setBoolean("can_create", canCreate());
            callableStatement.setBoolean("can_assign_others", canAssignOthers());
            callableStatement.setBoolean("is_mechanic", isMechanic());
            callableStatement.setInt("id", id());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Delete a Role from the database matching the given id.
     * @param id the id of the Role to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(deleteRoleQuery)) {
            callableStatement.setInt("id", id);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
