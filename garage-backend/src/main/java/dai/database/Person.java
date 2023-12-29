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
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getPhoneCode(){
        return phoneCode;
    }
    public String getPhoneNo(){
        return phoneNo;
    }

    static final String getAllQuery = "SELECT * FROM person;",
                        getPersonByIdQuery = "SELECT * FROM person WHERE id = :id;",
                        createPersonQuery = "INSERT INTO person(fname, lname, phone_code, phone_no) VALUES (:fname, :lname, :phone_code, :phone_no);",
                        updatePersonQuery = "UPDATE person SET fname = :fname, lname = :lname, phone_code = :phone_code, phone_no = :phone_no WHERE id = :id;",
                        deletePersonQuery = "DELETE FROM person WHERE id = :id;";

    /**
     * fetch Person matching given id from database
     * @return Person or null
     */
    static public Person fetchOne(int personId) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(getPersonByIdQuery)) {
            callableStatement.setInt(1, personId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("fname");
                    String lastName = resultSet.getString("lname");
                    String phoneCode = resultSet.getString("phone_code");
                    String phoneNo = resultSet.getString("phone_no");

                    return new Person(personId, firstName, lastName, phoneCode, phoneNo);
                } else
                    return null;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * fetch all Persons from database
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
     * save a new Person in the database
     * @return true if successful
     */
    public boolean save() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(createPersonQuery)) {
            callableStatement.setString(1, getFirstName());
            callableStatement.setString(2, getLastName());
            callableStatement.setString(3, getPhoneCode());
            callableStatement.setString(4, getPhoneNo());

            int rowsCreated = callableStatement.executeUpdate();
            return rowsCreated > 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * update Person in database matching id of given Person
     * @return true if successful
     */
    public boolean update() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(updatePersonQuery)) {
            callableStatement.setString(1, getFirstName());
            callableStatement.setString(2, getLastName());
            callableStatement.setString(3, getPhoneCode());
            callableStatement.setString(4, getPhoneNo());
            callableStatement.setInt(5, id());

            int rowsUpdated = callableStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * delete Person from database matching id
     * @return true if successful
     */
    static public boolean delete(int personId){
        try (CallableStatement callableStatement = con.prepareCall(deletePersonQuery)) {
            callableStatement.setInt(1, personId);

            int rowsDeleted = callableStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
