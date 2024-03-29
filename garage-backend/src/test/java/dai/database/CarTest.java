package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class CarTest extends GarageTest {

    @Test
    void fetchOne() throws SQLException {
        Car car = Car.fetchOne(1);

        assertNotNull(car);
        assertEquals(car, new Car(1, 2, "3VWAX7AJ1AM117565", "1M4011", "Citroën", "C3", "red"));
    }

    @Test
    void fetchAll() throws SQLException {
        Car[] cars = Car.fetchAll();

        assertNotNull(cars);
        assertEquals(12, cars.length);
        for (Car car : cars) {
            assertNotNull(car);
            assertInstanceOf(Car.class, car);
            assertNotNull(car.id());
            assertTrue(car.id() > 0);
            assertNotNull(car.chassisNo());
            assertEquals(17, car.chassisNo().length());
            assertNotNull(car.recType());
            assertEquals(6, car.recType().length());
        }
    }

    @Test
    void fetchByOwnerId() throws SQLException {
        Car[] cars = Car.fetchByOwnerId(2);

        assertNotNull(cars);
        assertEquals(3, cars.length);
        for (Car car : cars) {
            assertNotNull(car);
            assertInstanceOf(Car.class, car);
            assertNotNull(car.id());
            assertTrue(car.id() > 0);
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
        assertEquals(savedCar,
                new Car(13, car.ownerId(), car.chassisNo(), car.recType(), car.brand(), car.model(), car.color()));
    }

    @Test
    void update() throws SQLException {
        Car originalCar = Car.fetchOne(10);
        Car updatedCar = new Car(originalCar.id(), originalCar.ownerId(), originalCar.chassisNo(),
                originalCar.recType(), originalCar.brand(), originalCar.model(),
                "green");
        assertNotEquals(updatedCar, originalCar);
        assertEquals(updatedCar.update(), updatedCar);

        Car retrievedCar = Car.fetchOne(10);
        assertNotNull(retrievedCar);
        assertEquals(updatedCar, retrievedCar);
        assertNotEquals(retrievedCar, originalCar);
    }

    @Test
    void delete() throws SQLException {
        save();
        Car originalCar = Car.fetchOne(13);
        assertNotNull(originalCar);
        assertTrue(Car.delete(13));
        assertNull(Car.fetchOne(13));
    }
}