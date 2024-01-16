package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ServiceTest extends GarageTest {

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

    @Test
    void fetchOne() throws SQLException, ParseException {
        Service service = Service.fetchById(1);

        assertNotNull(service);

        java.util.Date dateCreated = format.parse("2023-06-01 08:19:15.000000");
        java.util.Date dateCarArrival = format.parse("2023-06-01 08:46:26.000000");
        java.util.Date dateCarProcessing = format.parse("2023-06-01 12:01:03.000000");
        java.util.Date dateCarDone = format.parse("2023-06-04 01:01:03.000000");
        java.util.Date dateCarLeft = format.parse("2023-06-05 01:01:03.000000");

        java.sql.Date sqlDateCreated = new java.sql.Date(dateCreated.getTime());
        java.sql.Date sqlDateCarArrival = new java.sql.Date(dateCarArrival.getTime());
        java.sql.Date sqlDateCarProcessing = new java.sql.Date(dateCarProcessing.getTime());
        java.sql.Date sqlDateCarDone = new java.sql.Date(dateCarDone.getTime());
        java.sql.Date sqlDateCarLeft = new java.sql.Date(dateCarLeft.getTime());

        assertEquals(service, new Service(1, 6, 4, 10, 61, "RAS", false, 4, sqlDateCreated, sqlDateCarArrival, sqlDateCarProcessing, sqlDateCarDone, sqlDateCarLeft));
    }

    @Test
    void fetchAll() throws SQLException {
        Service[] services = Service.fetchAll();

        assertNotNull(services);
        assertEquals(362, services.length);
        for (Service service : services) {
            assertNotNull(service);
            assertInstanceOf(Service.class, service);
            assertTrue(service.id() > 0);
            assertTrue(service.mechanicId() > 0);
            assertTrue(service.clientId() > 0);
            assertTrue(service.carId() > 0);
            assertTrue(service.hoursWorked() >= 0);
            assertNotNull(service.comments());
            assertTrue(service.comments().length() >= 0);
            assertNotNull(service.hasPictures());
            assertNotNull(service.stateId());
            assertTrue(service.stateId() >= 0);
            assertNotNull(service.dateCreated());
            assertNotNull(service.dateCarArrival());
            assertNotNull(service.dateCarProcessing());
            assertNotNull(service.dateCarDone());
            assertNotNull(service.dateCarLeft());
        }
    }
}
