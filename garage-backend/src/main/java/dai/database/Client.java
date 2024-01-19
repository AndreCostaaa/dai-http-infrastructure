package dai.database;

import java.sql.*;
import java.util.Objects;

public class Client extends Person {
    private String email;
    private String street;
    private String country;
    private Integer streetNo;
    private Integer npa;

    public Client(){
        super();
    }

    public Client(Integer id,
            String firstName,
            String lastName,
            String phoneNo,
            String email,
            String street,
            Integer streetNo,
            Integer npa,
            String country) {
        super(id, firstName, lastName, phoneNo);
        this.email = email;
        this.street = street;
        this.streetNo = streetNo;
        this.npa = npa;
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNo(Integer streetNo) {
        this.streetNo = streetNo;
    }

    public void setNpa(Integer npa) {
        this.npa = npa;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public Integer getStreetNo() {
        return streetNo;
    }

    public Integer getNpa() {
        return npa;
    }

    public String getCountry() {
        return country;
    }

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
                    && getEmail().equals(otherClient.getEmail())
                    && getStreet().equals(otherClient.getStreet())
                    && getStreetNo().equals(otherClient.getStreetNo())
                    && getNpa().equals(otherClient.getNpa())
                    && getCountry().equals(otherClient.getCountry());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNo(), getEmail(), getStreet(), getStreetNo(),
                getNpa(),
                getCountry());
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
        Integer streetNo = resultSet.getObject("street_no", Integer.class);
        Integer npa = resultSet.getObject("npa", Integer.class);
        String country = resultSet.getString("country");

        return new Client(client.getId(), client.getFirstName(), client.getLastName(), client.getPhoneNo(),
                email, street, streetNo, npa, country);
    }

    private void completeStatementCommon(NamedParameterStatement statement) throws SQLException {
        statement.setString("email", getEmail());
        statement.setString("street", getStreet());
        DatabaseHandler.setNullOrValue(streetNo, getStreetNo(), statement, "street_no", Types.INTEGER);
        DatabaseHandler.setNullOrValue(npa, getNpa(), statement, "npa", Types.INTEGER);
        statement.setString("country", getCountry());
    }

    public void completeCreateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);

        if (getId() == 0) // saveNotKnowingId()
            super.completeCommonStatement(statement);
        else // saveKnowingId()
            statement.setInt("id", getId());
    }

    public void completeUpdateStatement(NamedParameterStatement statement) throws SQLException {
        completeStatementCommon(statement);
        statement.setInt("id", getId());
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
    static public Client fetchById(Integer id) throws SQLException {
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

        // create a person first in the db
        Person person = new Person(getId(), getFirstName(), getLastName(), getPhoneNo());
        person = person.save();

        Client client = new Client(person.getId(), person.getFirstName(), person.getLastName(), person.getPhoneNo(),
                email,
                street,
                streetNo, npa, country);

        // we now know the id
        return client.saveKnowingId();
    }

    /**
     * Save the Client in the database knowing the id.
     *
     * @return Client or null
     */
    public Client saveKnowingId() throws SQLException {
        DatabaseHandler.executeCreateStatement(createClientKnowingIdQuery,
                this,
                (ResultSet resultSet) -> {
                    resultSet.next();
                    return this;
                });
        return fetchById(this.getId());
    }

    /**
     * Update the Client in the database.
     *
     * @return Client or null
     */
    public Client update() throws SQLException {
        // create a person first in the db
        Person person = new Person(getId(), getFirstName(), getLastName(), getPhoneNo());
        person = person.update();

        Client client = new Client(person.getId(), person.getFirstName(), person.getLastName(), person.getPhoneNo(),
                email,
                street,
                streetNo, npa, country);

        return DatabaseHandler.executeUpdateStatement(updateClientQuery,
                client,
                (ResultSet resultSet) -> {
                    resultSet.next();
                    return client;
                });
    }

    /**
     * Delete a Client from the database matching the given id.
     *
     * @param id the id of the Client to delete
     * @return true if successful
     */
    static public boolean delete(Integer id) throws SQLException {
        return DatabaseHandler.deleteById(deleteClientQuery, id);
    }
}
