package dai.database;

import java.sql.*;
import java.util.Objects;

public class Client extends Person {
    private final String email, street, country;
    private final int streetNo, npa;

    public Client(int id,
                  String firstName,
                  String lastName,
                  String phoneNo,
                  String email,
                  String street,
                  int streetNo,
                  int npa,
                  String country) {
        super(id, firstName, lastName, phoneNo);
        this.email = email;
        this.street = street;
        this.streetNo = streetNo;
        this.npa = npa;
        this.country = country;
    }

    public String email() { return email; }

    public String street() { return street; }

    public int streetNo() { return streetNo; }

    public int npa() { return npa; }

    public String country() { return country; }

    static final String getAllQuery = "SELECT * FROM client AS c JOIN person p ON p.id = c.id;",

            getClientByIdQuery = "SELECT * FROM client AS c JOIN person p ON p.id = c.id WHERE c.id = :id;",
            getClientByPhoneNoQuery = "SELECT * FROM client AS c JOIN person p ON p.id = c.id WHERE p.phone_no = :phone_no;",
            createClientNotKnowingIdQuery = "WITH person_id AS (INSERT INTO person (fname, lname, phone_no) VALUES (:fname, :lname, :phone_no) RETURNING id) INSERT INTO client (id, email, street, street_no, npa, country) VALUES ((SELECT id FROM person_id), :email, :street, :street_no, :npa, :country);",
            createClientKnowingIdQuery = "INSERT INTO client (id, email, street, street_no, npa, country) VALUES (:id, :email, :street, :street_no, :npa, :country);",
            updateClientQuery = "UPDATE client SET email = :email, street = :street, street_no = :street_no, npa = :npa, country = :country WHERE id = :id;",
            deleteClientQuery = "DELETE FROM client WHERE id = :id;";

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Client otherClient) {
            return super.equals(otherClient)
                    && email().equals(otherClient.email())
                    && street().equals(otherClient.street())
                    && streetNo() == otherClient.streetNo()
                    && npa() == otherClient.npa()
                    && country().equals(otherClient.country());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id(), firstName(), lastName(), phoneNo(), email(), street(), streetNo(), npa(), country());
    }

    /**
     * @param resultSet resultset returned from the execution of the query
     * @return Client or null
     */
    protected static Client fetchNext(ResultSet resultSet) throws SQLException {
        Person client = Person.fetchNext(resultSet);

        if (client == null)
            return null;

        String email = resultSet.getString("email");
        String street = resultSet.getString("street");
        int streetNo = resultSet.getInt("street_no");
        int npa = resultSet.getInt("npa");
        String country = resultSet.getString("country");

        return new Client(client.id(), client.firstName(), client.lastName(), client.phoneNo(),
                email, street, streetNo, npa, country);
    }

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException {
        statement.setString("email", email());
        statement.setString("street", street());
        statement.setInt("street_no", streetNo());
        statement.setInt("npa", npa());
        statement.setString("country", country());
    }

    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);

        if (id() == 0) // saveNotKnowingId()
            super.completeCommonStatement(statement);
        else // saveKnowingId()
            statement.setInt("id", id());
    }

    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
        statement.setInt("id", id());
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
        return DatabaseHandler.fetchById(getClientByIdQuery, id, Client::fetchNext);
    }

    /**
     * Fetch a Client from the database matching the given phone_number.
     *
     * @param phoneNo the phone_number of the Client to fetch
     * @return Client or null
     */
    static public Client[] fetchByPhoneNo(String phoneNo) throws SQLException {
        return DatabaseHandler.fetchAllBy(getClientByPhoneNoQuery, "phone_no", phoneNo, Client::fetchNext);
    }

    /**
     * Save the Client in the database without knowing the id.
     *
     * @return Client or null
     */
    public Client saveNotKnowingId() throws SQLException {
        return DatabaseHandler.executeCreateStatement(createClientNotKnowingIdQuery, this, Client::fetchNext);
    }

    /**
     * Save the Client in the database knowing the id.
     *
     * @return Client or null
     */
    public Client saveKnowingId() throws SQLException {
        return DatabaseHandler.executeCreateStatement(createClientKnowingIdQuery, this, Client::fetchNext);
    }

    /**
     * Update the Client in the database.
     *
     * @return Client or null
     */
    public Client update() throws SQLException {
        return DatabaseHandler.executeUpdateStatement(updateClientQuery, this, Client::fetchNext);
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
