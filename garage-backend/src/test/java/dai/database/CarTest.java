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
        assertEquals(2, car.ownerId());
        assertEquals("3VWAX7AJ1AM117565", car.chassisNo());
        assertEquals("1M4011", car.recType());
        assertEquals("Citroën", car.brand());
        assertEquals("C3", car.model());
        assertEquals("red", car.color());
    }

    @Test
    void fetchAll() throws SQLException {
        Car[] cars = Car.fetchAll();

        assertNotNull(cars);
        assertEquals(12, cars.length);
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