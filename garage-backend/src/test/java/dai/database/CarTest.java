package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class CarTest extends GarageTest {

    @Test
    void fetchOne() throws SQLException {
        Car car = Car.fetchById(1);

        assertNotNull(car);
        assertEquals(1, car.id());
    }

    @Test
    void fetchAll() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}