package dai.database;

import java.sql.*;

public class Client extends Person {
    private final String email, street, country;
    private final int streetNo, npa;

    public Client(int id,
                  String firstName,
                  String lastName,
                  String phoneCode,
                  String phoneNo,
                  String email,
                  String street,
                  int streetNo,
                  int npa,
                  String country) {
        super(id, firstName, lastName, phoneCode, phoneNo);
        this.email = email;
        this.street = street;
        this.streetNo = streetNo;
        this.npa = npa;
        this.country = country;
    }

    public String email() {
        return email;
    }

    public String street() {
        return street;
    }

    public int streetNo() {
        return streetNo;
    }

    public int npa() {
        return npa;
    }

    public String country() {
        return country;
    }

    static final String getAllQuery = "SELECT * FROM client AS c JOIN person p ON p.id = c.id;",

            getClientByIdQuery = "SELECT * FROM client AS c JOIN person p ON p.id = c.id WHERE c.id = :id;",
            getClientByPhoneNoQuery = "SELECT * FROM client AS c JOIN person p ON p.id = c.id WHERE p.phone_no = :phone_no;",
            createClientNotKnowingIdQuery = "WITH person_id AS (INSERT INTO person (fname, lname, phone_code, phone_no) VALUES (:fname, :lname, :phone_code, :phone_no) RETURNING id) INSERT INTO client (id, email, street, street_no, npa, country) VALUES (person_id, :email, :street, :street_no, :npa, :country);",
            createClientKnowingIdQuery = "INSERT INTO client (id, email, street, street_no, npa, country) VALUES (:id, :email, :street, :street_no, :npa, :country);",
            updateClientQuery = "UPDATE client SET email = :email, street = :street, street_no = :street_no, npa = :npa, country = :country WHERE id = :id;",
            deleteClientQuery = "DELETE FROM client WHERE id = :id;";

    static private Client fetchNext(ResultSet resultSet) throws SQLException {
        if (!resultSet.next())
            return null;

        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("fname");
        String lastName = resultSet.getString("lname");
        String phoneCode = resultSet.getString("phone_code");
        String phoneNo = resultSet.getString("phone_no");
        String email = resultSet.getString("email");
        String street = resultSet.getString("street");
        int streetNo = resultSet.getInt("street_no");
        int npa = resultSet.getInt("npa");
        String country = resultSet.getString("country");

        return new Client(id, firstName, lastName, phoneCode, phoneNo, email, street, streetNo, npa,
                country);
    }

    /**
     * Fetch all Clients from the database.
     *
     * @return Client[] or null
     */
    static public Client[] fetchAll() throws SQLException {
        return DatabaseHandler.fetchAll(getAllQuery, Client::fetchNext);
    }

    /**
     * Fetch a Client from the database matching the given id.
     *
     * @param id the id of the Client to fetch
     * @return Client or null
     */
    static public Client fetchById(int id) throws SQLException {
        return DatabaseHandler.fetchById(getAllQuery, id, Client::fetchNext);
    }

    /**
     * Fetch a Client from the database matching the given phone_number.
     *
     * @param phoneNo the phone_number of the Client to fetch
     * @return Client or null
     */
    static public Client fetchOneByPhoneNo(String phoneNo) throws SQLException {
        return DatabaseHandler.fetchBy(getClientByPhoneNoQuery, "phone_no", phoneNo, Client::fetchNext);
    }

    /**
     * Save the Client in the database without knowing the id.
     *
     * @return true if successful
     */
    public boolean saveNotKnowingId() throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(createClientNotKnowingIdQuery)) {
            callableStatement.setString("fname", firstName());
            callableStatement.setString("lname", lastName());
            callableStatement.setString("phone_code", phoneCode());
            callableStatement.setString("phone_no", phoneNo());
            callableStatement.setString("email", email());
            callableStatement.setString("street", street());
            callableStatement.setInt("street_no", streetNo());
            callableStatement.setInt("npa", npa());
            callableStatement.setString("country", country());

            return callableStatement.executeUpdate() == 1;
        }
    }

    /**
     * Common method for saveKnowingId() and update().
     *
     * @param query the query to execute
     * @return true if successful
     */
    private boolean saveOrUpdate(String query) throws SQLException {
        try (CallableStatement callableStatement = con.prepareCall(query)) {
            callableStatement.setString("email", email());
            callableStatement.setString("street", street());
            callableStatement.setInt("street_no", streetNo());
            callableStatement.setInt("npa", npa());
            callableStatement.setString("country", country());
            callableStatement.setInt("id", id());

            return callableStatement.executeUpdate() == 1;
        }
    }

    /**
     * Save the Client in the database knowing the id.
     *
     * @return true if successful
     */
    public boolean saveKnowingId() throws SQLException {
        return saveOrUpdate(createClientKnowingIdQuery);
    }

    /**
     * Update the Client in the database.
     *
     * @return true if successful
     */
    public boolean update() throws SQLException {
        return saveOrUpdate(updateClientQuery);
    }

    /**
     * Delete a Client from the database matching the given id.
     *
     * @param id the id of the Client to delete
     * @return true if successful
     */
    static public boolean delete(int id) throws SQLException {
        return DatabaseHandler.deleteById(deleteClientQuery, id);
    }
}
