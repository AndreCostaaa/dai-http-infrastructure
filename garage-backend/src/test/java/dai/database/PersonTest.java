package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class PersonTest extends GarageTest {

    @Test
    void fetchOne() throws SQLException {
        Person person = Person.fetchById(1);

        assertNotNull(person);
        assertEquals(person, new Person(1, "René", "Rentsch", "+41244303309"));
    }

    @Test
    void fetchAll() throws SQLException {
        Person[] persons = Person.fetchAll();

        assertNotNull(persons);
        assertEquals(12, persons.length);
        for (Person person : persons) {
            assertNotNull(person);
            assertInstanceOf(Person.class, person);
            assertTrue(person.id() > 0);
        }
    }

    @Test
    void save() throws SQLException {
        Person person = new Person(ghostId, "Itachi", "Uchiwa", "+41835619384");

        Person savedPerson = person.save();
        assertNotNull(savedPerson);
        assertEquals(savedPerson,
                new Person(13, person.firstName(), person.lastName(), person.phoneNo()));
    }

    @Test
    void update() throws SQLException {
        Person originalPerson = Person.fetchById(2);
        Person updatedPerson = new Person(originalPerson.id(), originalPerson.firstName(), originalPerson.lastName(),
                "+689283742398");
        assertNotEquals(updatedPerson, originalPerson);
        assertEquals(updatedPerson.update(), updatedPerson);

        Person retrievedPerson = Person.fetchById(2);
        assertNotNull(retrievedPerson);
        assertEquals(updatedPerson, retrievedPerson);
        assertNotEquals(retrievedPerson, originalPerson);
    }

    @Test
    void delete() throws SQLException {
        save();
        Person person = Person.fetchById(13);
        assertNotNull(person);
        assertTrue(Person.delete(13));
        assertNull(Person.fetchById(13));
    }
}
