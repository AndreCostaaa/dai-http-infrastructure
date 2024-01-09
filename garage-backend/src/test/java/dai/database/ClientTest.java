package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class ClientTest extends GarageTest {

    @Test
    void fetchOne() throws SQLException {
        Client client = Client.fetchById(2);

        assertNotNull(client);
        assertEquals(client, new Client(2, "Amir", "Mouti", "+41765594551", "amir.mouti@heig-vd.ch", "Route de Bois", 14, 1200, "Switzerland"));
    }

    @Test
    void fetchAll() throws SQLException {
        Client[] clients = Client.fetchAll();

        assertNotNull(clients);
        assertEquals(4, clients.length);
        for (Client client : clients) {
            assertNotNull(client);
            assertInstanceOf(Client.class, client);
            assertTrue(client.id() > 0);
            assertTrue(client.streetNo() > 0);
            assertTrue(client.npa() > 0);
        }
    }

    @Test
    void fetchByPhoneNo() throws SQLException {
        Client[] clients = Client.fetchByPhoneNo("+41786308274");

        assertNotNull(clients);
        assertEquals(1, clients.length);
        assertEquals(clients[0], new Client(12, "André", "Costa", "+41786308274", "andremig.serzedel@heig-vd.ch", "Route de Cheseaux", 4, 1400, "Switzerland"));
    }

    /*
    @Test
    void saveNotKnowingId() throws SQLException {
        Client client = new Client(ghostId, "Itachi", "Uchiwa", "+41835619384", "itachi.uchiwa@heig-vd.ch", "Uchiwa Headquarters", 13, 8888, "Japan");

        Client savedClient = client.saveNotKnowingId();
        assertNotNull(savedClient);
        assertEquals(savedClient, new Client(5, client.firstName(), client.lastName(), client.phoneNo(), client.email(), client.street(), client.streetNo(), client.npa(), client.country()));
    }

    @Test
    void saveKnowingId() throws SQLException {
        Person person = Person.fetchById(1);
        Client client = new Client(person.id(), person.firstName(), person.lastName(), person.phoneNo(), "rene.rentsch@heig-vd.ch", "Route du Feu", 666, 1666, "Switzerland");

        Client savedClient = client.saveKnowingId();
        assertNotNull(savedClient);
        assertEquals(savedClient, new Client(client.id(), client.firstName(), client.lastName(), client.phoneNo(), client.email(), client.street(), client.streetNo(), client.npa(), client.country()));
    }

    @Test
    void update() throws SQLException {
        Client originalClient = Client.fetchById(2);
        Client updatedClient = new Client(originalClient.id(), originalClient.firstName(), originalClient.lastName(), "+689283742398", originalClient.email(), originalClient.street(), originalClient.streetNo(), originalClient.npa(), originalClient.country());
        assertNotEquals(updatedClient, originalClient);
        assertEquals(updatedClient.update(), updatedClient);

        Client retrievedClient = Client.fetchById(2);
        assertNotNull(retrievedClient);
        assertEquals(updatedClient, retrievedClient);
        assertNotEquals(retrievedClient, originalClient);
    }

    @Test
    void delete() throws SQLException {
        saveNotKnowingId();
        Client client = Client.fetchById(5);
        assertNotNull(client);
        assertTrue(Client.delete(5));
        assertNull(Client.fetchById(5));
    }
    */
}
