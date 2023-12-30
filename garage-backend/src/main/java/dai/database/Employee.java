package dai.database;

import java.sql.*;

public class Employee extends Person {
    private final int roleId, specializationId;

    public Employee(int id,
                    String firstName,
                    String lastName,
                    String phoneCode,
                    String phoneNo,
                    int roleId,
                    int specializationId) {
        super(id, firstName, lastName, phoneCode, phoneNo);
        this.roleId = roleId;
        this.specializationId = specializationId;
    }

    public int roleId(){
        return roleId;
    }
    public int specializationId(){
        return specializationId;
    }

    static final String getAllQuery = "SELECT * FROM employee AS e JOIN person p ON e.id = p.id JOIN role r ON e.role_id = r.id;",
                        getEmployeeByIdQuery = "SELECT * FROM employee AS e JOIN person p ON e.id = p.id JOIN role r ON e.role_id = r.id WHERE e.id = :id;",
                        getEveryMechanicQuery = "SELECT * FROM employee AS e JOIN person p ON e.id = p.id WHERE role_id IN (SELECT id FROM role WHERE is_mechanic = true);",
                        createEmployeeNotKnowingIdQuery = "WITH person_id AS (INSERT INTO person (fname, lname, phone_code, phone_no) VALUES (:fname, :lname, :phone_code, :phone_no) RETURNING id) INSERT INTO employee (id, role_id, specialization_id) VALUES (person_id, :role_id, :specialization_id);",
                        createEmployeeKnowingIdQuery = "INSERT INTO employee (id, role_id, specialization_id) VALUES (:id, :role_id, :specialization_id);",
                        updateEmployeeQuery = "UPDATE employee SET role_id = :role_id, specialization_id = :specialization_id WHERE id = :id;",
                        deleteEmployeeQuery = "DELETE FROM employee WHERE id = :id;";

    /**
     * Common method for fetchAll() and fetchEveryMechanic().
     * @param query the query to execute
     * @return Employee[] or null
     */
    static private Employee[] getEmployees(String query) throws SQLException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Employee[] employees = new Employee[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("fname");
                    String lastName = resultSet.getString("lname");
                    String phoneCode = resultSet.getString("phone_code");
                    String phoneNo = resultSet.getString("phone_no");
                    int roleId = resultSet.getInt("role_id");
                    int specializationId = resultSet.getInt("specialization_id");

                    employees[i++] = new Employee(id, firstName, lastName, phoneCode, phoneNo, roleId, specializationId);
                }

                return employees;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all Employees from the database.
     * @return Employee[] or null
     */
    static public Employee[] fetchAll() throws SQLException {
        return getEmployees(getAllQuery);
    }

    /**
     * Fetch an Employee from the database matching the given id.
     * @param id the id of the Employee to fetch
     * @return Employee or null
     */
    static public Employee fetchOne(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getEmployeeByIdQuery)) {
            callableStatement.setInt("id", id);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("fname");
                    String lastName = resultSet.getString("lname");
                    String phoneCode = resultSet.getString("phone_code");
                    String phoneNo = resultSet.getString("phone_no");
                    int roleId = resultSet.getInt("role_id");
                    int specializationId = resultSet.getInt("specialization_id");

                    return new Employee(id, firstName, lastName, phoneCode, phoneNo, roleId, specializationId);
                } else
                    return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch every Employee which has mechanic Role from the database.
     * @return Employee[] or null
     */
    static public Employee[] fetchEveryMechanic() throws SQLException {
        return getEmployees(getEveryMechanicQuery);
    }

    /**
     * Save the Employee in the database without knowing the id.
     * @return true if successful
     */
    public boolean saveNotKnowingId() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(createEmployeeNotKnowingIdQuery)) {
            callableStatement.setString("fname", firstName());
            callableStatement.setString("lname", lastName());
            callableStatement.setString("phone_code", phoneCode());
            callableStatement.setString("phone_no", phoneNo());
            callableStatement.setInt("role_id", roleId());
            callableStatement.setInt("specialization_id", specializationId());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Common method for saveKnowingId() and update().
     * @param query the query to execute
     * @return true if successful
     */
    private boolean saveOrUpdate(String query) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(query)) {
            callableStatement.setInt("id", id());
            callableStatement.setInt("role_id", roleId());
            callableStatement.setInt("specialization_id", specializationId());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Save the Employee in the database knowing the id.
     * @return true if successful
     */
    public boolean saveKnowingId() throws SQLException {
        return saveOrUpdate(createEmployeeKnowingIdQuery);
    }

    /**
     * Update the Employee in the database.
     * @return true if successful
     */
    public boolean update() throws SQLException {
        return saveOrUpdate(updateEmployeeQuery);
    }

    /**
     * Delete an Employee from the database matching the given id.
     * @param id the id of the Employee to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(deleteEmployeeQuery)) {
            callableStatement.setInt("id", id);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}