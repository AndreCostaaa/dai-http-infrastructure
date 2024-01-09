package dai.database;

import java.sql.*;
import java.util.Objects;

public class Person implements IEntity {
    private final int id;
    private final String firstName, lastName, phoneNo;

    public Person(int id,
            String firstName,
            String lastName,
            String phoneNo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }

    public int id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String phoneNo() {
        return phoneNo;
    }

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Person otherPerson) {
            return id() == otherPerson.id()
                    && firstName().equals(otherPerson.firstName())
                    && lastName().equals(otherPerson.lastName())
                    && phoneNo().equals(otherPerson.phoneNo());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id(), firstName(), lastName(), phoneNo());
    }

    static final String getAllQuery = "SELECT * FROM person;",
            getPersonByIdQuery = "SELECT * FROM person WHERE id = :id;",
            createPersonQuery = "INSERT INTO person(fname, lname, phone_no) VALUES (:fname, :lname, :phone_no);",
            updatePersonQuery = "UPDATE person SET fname = :fname, lname = :lname, phone_no = :phone_no WHERE id = :id;",
            deletePersonQuery = "DELETE FROM person WHERE id = :id;";

    /**
     * @param resultSet resultset returned from the execution of the query
     * @return Person or null
     */
    protected static Person fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("fname");
        String lastName = resultSet.getString("lname");
        String phoneNo = resultSet.getString("phone_no");

        return new Person(id, firstName, lastName, phoneNo);
    }

    public void completeCommonStatement(NamedParameterStatement statement) throws SQLException {
        statement.setString("fname", firstName());
        statement.setString("lname", lastName());
        statement.setString("phone_no", phoneNo());
    }

    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeCommonStatement(statement);
    }

    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeCommonStatement(statement);
        statement.setInt("id", id());
    }

    /**
     * Fetch all Persons from the database.
     *
     * @return Person[] or null
     */
    static public Person[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllQuery, Person::fetchNext);
    }

    /**
     * Fetch a Car from the database matching the given id.
     *
     * @param id the id of the Car to fetch
     * @return Car or null
     */
    static public Person fetchById(int id) throws SQLException {
        return DatabaseHandler.fetchById(getPersonByIdQuery, id, Person::fetchNext);
    }

    /**
     * Save the Person in the database.
     *
     * @return Person or null
     */
    public Person save() throws SQLException {
        return DatabaseHandler.executeCreateStatement(createPersonQuery, this, Person::fetchNext);
    }

    /**
     * Update the Person in the database.
     *
     * @return Person or null
     */
    public Person update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updatePersonQuery, this, Person::fetchNext);
    }

    /**
     * Delete a Person from the database matching the given id.
     *
     * @param id the id of the Person to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        return DatabaseHandler.deleteById(deletePersonQuery, id);
    }
}
