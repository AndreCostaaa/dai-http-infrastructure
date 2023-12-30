package dai.database;

import java.sql.*;

public class Person {
    private final int id;
    private final String firstName, lastName, phoneCode, phoneNo;
    static Connection con;

    public Person(int id,
                  String firstName,
                  String lastName,
                  String phoneCode,
                  String phoneNo){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneCode = phoneCode;
        this.phoneNo = phoneNo;
    }

    public int id(){
        return id;
    }
    public String firstName(){
        return firstName;
    }
    public String lastName(){
        return lastName;
    }
    public String phoneCode(){
        return phoneCode;
    }
    public String phoneNo(){
        return phoneNo;
    }

    static final String getAllQuery = "SELECT * FROM person;",
                        getPersonByIdQuery = "SELECT * FROM person WHERE id = :id;",
                        createPersonQuery = "INSERT INTO person(fname, lname, phone_code, phone_no) VALUES (:fname, :lname, :phone_code, :phone_no);",
                        updatePersonQuery = "UPDATE person SET fname = :fname, lname = :lname, phone_code = :phone_code, phone_no = :phone_no WHERE id = :id;",
                        deletePersonQuery = "DELETE FROM person WHERE id = :id;";

    /**
     * Fetch a Person from the database matching the given id.
     * @param id the id of the Person to fetch
     * @return Person or null
     */
    static public Person fetchOne(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getPersonByIdQuery)) {
            callableStatement.setInt("id", id);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("fname");
                    String lastName = resultSet.getString("lname");
                    String phoneCode = resultSet.getString("phone_code");
                    String phoneNo = resultSet.getString("phone_no");

                    return new Person(id, firstName, lastName, phoneCode, phoneNo);
                } else
                    return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Fetch all Persons from the database.
     * @return Person[] or null
     */
    static public Person[] fetchAll() throws SQLException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(getAllQuery)) {
                resultSet.last();
                int count = resultSet.getRow();
                resultSet.beforeFirst();

                Person[] persons = new Person[count];
                int i = 0;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("fname");
                    String lastName = resultSet.getString("lname");
                    String phoneCode = resultSet.getString("phone_code");
                    String phoneNo = resultSet.getString("phone_no");

                    persons[i++] = new Person(id, firstName, lastName, phoneCode, phoneNo);
                }

                return persons;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Save the Person in the database.
     * @return true if successful
     */
    public boolean save() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(createPersonQuery)) {
            callableStatement.setString("fname", firstName());
            callableStatement.setString("lname", lastName());
            callableStatement.setString("phone_code", phoneCode());
            callableStatement.setString("phone_no", phoneNo());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Update the Person in the database.
     * @return true if successful
     */
    public boolean update() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(updatePersonQuery)) {
            callableStatement.setString("fname", firstName());
            callableStatement.setString("lname", lastName());
            callableStatement.setString("phone_code", phoneCode());
            callableStatement.setString("phone_no", phoneNo());
            callableStatement.setInt("id", id());

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Delete a Person from the database matching the given id.
     * @param id the id of the Person to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(deletePersonQuery)) {
            callableStatement.setInt("id", id);

            return callableStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
