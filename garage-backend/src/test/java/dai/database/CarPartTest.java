package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class CarPartTest extends GarageTest {

    @Test
    void fetchOne() throws SQLException {
        CarPart carPart = CarPart.fetchOne(1);

        assertNotNull(carPart);
        assertEquals(carPart, new CarPart(1, null, "Motorex", "TS-X SAE 5W-30", "Concept TS-X SAE 5W-30",
                "Motor Oil - 1L", 19.95, 29.95));
        assertEquals(carPart, new CarPart(1, 0, "Motorex", "TS-X SAE 5W-30", "Concept TS-X SAE 5W-30", "Motor Oil - 1L",
                19.95, 29.95));
    }

    @Test
    void fetchAll() throws SQLException {
        CarPart[] carParts = CarPart.fetchAll();

        assertNotNull(carParts);
        assertEquals(18, carParts.length);
        for (CarPart carPart : carParts) {
            assertNotNull(carPart);
            assertInstanceOf(CarPart.class, carPart);
            assertNotNull(carPart.id());
            assertTrue(carPart.id() > 0);
            assertTrue(carPart.buyPrice() >= 0);
            assertTrue(carPart.sellPrice() >= 0);
        }
    }

    @Test
    void fetchByServiceId() throws SQLException {
        save();
        CarPart[] carParts = CarPart.fetchByServiceId(2);

        assertNotNull(carParts);
        assertEquals(1, carParts.length);
        for (CarPart carPart : carParts) {
            assertNotNull(carPart);
            assertInstanceOf(CarPart.class, carPart);
            assertNotNull(carPart.id());
            assertTrue(carPart.id() > 0);
            assertTrue(carPart.buyPrice() >= 0);
            assertTrue(carPart.sellPrice() >= 0);
        }
    }

    @Test
    void save() throws SQLException {
        CarPart carPart = new CarPart(ghostId, 2, "Motorex", "TS-X SAE 5W-30", "Concept TS-X SAE 5W-30",
                "Motor Oil - 1L", 19.95, 29.95);

        CarPart savedCarPart = carPart.save();
        assertNotNull(savedCarPart);
        assertEquals(savedCarPart,
                new CarPart(19, carPart.serviceId(), carPart.supplier(), carPart.supplierRef(), carPart.name(),
                        carPart.description(), carPart.buyPrice(), carPart.sellPrice()));
    }

    @Test
    void update() throws SQLException {
        CarPart originalCarPart = CarPart.fetchOne(8);
        CarPart updatedCarPart = new CarPart(originalCarPart.id(), originalCarPart.serviceId(),
                originalCarPart.supplier(), originalCarPart.supplierRef(), originalCarPart.name(),
                originalCarPart.description(), 14.95, originalCarPart.sellPrice());
        assertNotEquals(updatedCarPart, originalCarPart);
        assertEquals(updatedCarPart.update(), updatedCarPart);

        CarPart retrievedCarPart = CarPart.fetchOne(8);
        assertNotNull(retrievedCarPart);
        assertEquals(updatedCarPart, retrievedCarPart);
        assertNotEquals(retrievedCarPart, originalCarPart);
    }

    @Test
    void delete() throws SQLException {
        save();
        CarPart originalCarPart = CarPart.fetchOne(19);
        assertNotNull(originalCarPart);
        assertTrue(CarPart.delete(19));
        assertNull(CarPart.fetchOne(19));
    }
}
