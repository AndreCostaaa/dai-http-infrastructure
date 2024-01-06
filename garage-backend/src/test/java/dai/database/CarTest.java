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
        for (Car car : cars) {
            assertNotNull(car);
            assertInstanceOf(Car.class, car);
            assertTrue(car.id() > 0);
            assertTrue(car.ownerId() > 0);
            assertNotNull(car.chassisNo());
            assertEquals(17, car.chassisNo().length());
            assertNotNull(car.recType());
            assertEquals(6, car.recType().length());
        }
    }

    @Test
    void save() throws SQLException {
        Car car = new Car(13, 3, "8AFA8EFZH9FWFH9FW", "FH8239", "Mercedes", "AMG GT Coupé", "grey");

        assertTrue(car.save());

        Car retrievedCar = Car.fetchById(13);

        assertNotNull(retrievedCar);
        assertEquals(13, retrievedCar.id());
        assertEquals(3, retrievedCar.ownerId());
        assertEquals("8AFA8EFZH9FWFH9FW", retrievedCar.chassisNo());
        assertEquals("FH8239", retrievedCar.recType());
        assertEquals("Mercedes", retrievedCar.brand());
        assertEquals("AMG GT Coupé", retrievedCar.model());
        assertEquals("grey", retrievedCar.color());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}