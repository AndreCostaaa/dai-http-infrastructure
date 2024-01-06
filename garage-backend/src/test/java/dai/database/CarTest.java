package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class CarTest extends GarageTest {

    private static final int ghostId = 0;

    void checkAttributes(Car car, int id, int ownerId, String chassisNo, String recType, String brand, String model, String color) {
        assertEquals(car.id(), id);
        assertEquals(car.ownerId(), ownerId);
        assertEquals(car.chassisNo(), chassisNo);
        assertEquals(car.recType(), recType);
        assertEquals(car.brand(), brand);
        assertEquals(car.model(), model);
        assertEquals(car.color(), color);
    }

    @Test
    void fetchOne() throws SQLException {
        Car car = Car.fetchById(1);

        assertNotNull(car);
        checkAttributes(car, 1, 2, "3VWAX7AJ1AM117565", "1M4011", "Citroën", "C3", "red");
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
        Car car = new Car(ghostId, 3, "8AFA8EFZH9FWFH9FW", "FH8239", "Mercedes", "AMG GT Coupé", "grey");

        Car savedCar = car.save();
        assertNotNull(savedCar);
        checkAttributes(savedCar, 13, 3, "8AFA8EFZH9FWFH9FW", "FH8239", "Mercedes", "AMG GT Coupé", "grey");
    }

    @Test
    void update() throws SQLException {
        Car car = Car.fetchById(13);

        assertNotNull(car);
        checkAttributes(car, 13, 3, "8AFA8EFZH9FWFH9FW", "FH8239", "Mercedes", "AMG GT Coupé", "grey");

        Car updatedCar = new Car(car.id(), car.ownerId(), car.chassisNo(), car.recType(), car.brand(), car.model(), "green");
        assertTrue(updatedCar.update());

        Car retrievedCar = Car.fetchById(13);
        assertNotNull(retrievedCar);
        checkAttributes(retrievedCar, 13, 3, "8AFA8EFZH9FWFH9FW", "FH8239", "Mercedes", "AMG GT Coupé", "green");
    }

    @Test
    void delete() {
    }
}