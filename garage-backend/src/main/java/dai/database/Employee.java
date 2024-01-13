package dai.database;

import java.sql.*;

public class Employee extends Person {
    private int roleId;
    private Integer specializationId;

    public Employee() {

    }

    public Employee(int id,
            String firstName,
            String lastName,
            String phoneNo,
            int roleId,
            Integer specializationId) {
        super(id, firstName, lastName, phoneNo);
        this.roleId = roleId;
        this.specializationId = specializationId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public int getRoleId() {
        return roleId;
    }

    public Integer getSpecializationId() {
        return specializationId;
    }

    static final String getAllQuery = "SELECT * FROM employee AS e JOIN person p ON e.id = p.id JOIN role r ON e.role_id = r.id;",
            getEmployeeByIdQuery = "SELECT * FROM employee AS e JOIN person p ON e.id = p.id JOIN role r ON e.role_id = r.id WHERE e.id = :id;",
            getEveryMechanicQuery = "SELECT * FROM employee AS e JOIN person p ON e.id = p.id WHERE role_id IN (SELECT id FROM role WHERE is_mechanic = true);",
            createEmployeeNotKnowingIdQuery = "WITH person_id AS (INSERT INTO person (fname, lname, phone_no) VALUES (:fname, :lname, :phone_no) RETURNING id) INSERT INTO employee (id, role_id, specialization_id) VALUES (person_id, :role_id, :specialization_id);",
            createEmployeeKnowingIdQuery = "INSERT INTO employee (id, role_id, specialization_id) VALUES (:id, :role_id, :specialization_id);",
            updateEmployeeQuery = "UPDATE employee SET role_id = :role_id, specialization_id = :specialization_id WHERE id = :id;",
            deleteEmployeeQuery = "DELETE FROM employee WHERE id = :id;";

    /**
     * @param resultSet resultset returned from the execution of the query
     * @return Employee or null
     */
    protected static Employee fetchNext(ResultSet resultSet) throws SQLException {
        Person employee = Person.fetchNext(resultSet);

        if (employee == null)
            return null;

        int roleId = resultSet.getInt("role_id");
        Integer specializationId = resultSet.getObject("specialization_id", Integer.class);

        return new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getPhoneNo(),
                roleId, specializationId);
    }

    public void completeCommonStatement(NamedParameterStatement statement) throws SQLException {
        statement.setInt("role_id", getRoleId());
        if (specializationId == null || getSpecializationId() == 0)
            statement.setNull("specialization_id", Types.INTEGER);
        else
            statement.setInt("specialization_id", getSpecializationId());
    }

    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeCommonStatement(statement);

        if (getId() == 0) // saveNotKnowingId()
            super.completeCommonStatement(statement);
        else // saveKnowingId()
            statement.setInt("id", getId());
    }

    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeCommonStatement(statement);
        statement.setInt("id", getId());
    }

    /**
     * Fetch all Employees from the database.
     * 
     * @return Employee[] or null
     */
    static public Employee[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllQuery, Employee::fetchNext);
    }

    /**
     * Fetch an Employee from the database matching the given id.
     * 
     * @param id the id of the Employee to fetch
     * @return Employee or null
     */
    static public Employee fetchById(int id) throws SQLException {
        return DatabaseHandler.fetchById(getEmployeeByIdQuery, id, Employee::fetchNext);
    }

    /**
     * Fetch every Employee which has mechanic Role from the database.
     * 
     * @return Employee[] or null
     */
    static public Employee[] fetchEveryMechanic() throws SQLException {
        return DatabaseHandler.fetchAll(getEveryMechanicQuery, Employee::fetchNext);
    }

    /**
     * Save the Employee in the database without knowing the id.
     * 
     * @return Employee or null
     */
    public Employee saveNotKnowingId() throws SQLException {
        // Create a person first
        Person person = new Person(getId(), getFirstName(), getLastName(), getPhoneNo());
        person = person.save();
        // We can know complete the employee with all the values
        Employee employee = new Employee(person.getId(), person.getFirstName(), person.getLastName(),
                person.getPhoneNo(),
                roleId,
                specializationId);

        // we now know the id
        return employee.saveKnowingId();
    }

    /**
     * Save the Employee in the database knowing the id.
     * 
     * @return Employee or null
     */
    public Employee saveKnowingId() throws SQLException {
        DatabaseHandler.executeUpdateStatement(createEmployeeKnowingIdQuery, this,
                (ResultSet resultSet) -> {
                    resultSet.next();
                    return this;
                });
        return fetchById(this.getId());
    }

    /**
     * Update the Employee in the database.
     * 
     * @return Employee or null
     */
    public Employee update() throws SQLException {
        Person person = new Person(getId(), getFirstName(), getLastName(), getPhoneNo());
        person = person.update();

        Employee employee = new Employee(person.getId(), person.getFirstName(), person.getLastName(),
                person.getPhoneNo(),
                roleId,
                specializationId);

        return DatabaseHandler.executeUpdateStatement(updateEmployeeQuery,
                employee,
                (ResultSet resultSet) -> {
                    resultSet.next();
                    return employee;
                });

    }

    /**
     * Delete an Employee from the database matching the given id.
     * 
     * @param id the id of the Employee to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        return DatabaseHandler.deleteById(deleteEmployeeQuery, id);
    }
}