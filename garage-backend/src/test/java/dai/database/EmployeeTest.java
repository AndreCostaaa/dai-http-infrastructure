package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class EmployeeTest extends GarageTest {

    @Test
    void fetchOne() throws SQLException {
        Employee employee = Employee.fetchById(1);

        assertNotNull(employee);
        assertEquals(employee, new Employee(1, "René", "Rentsch", "+41244303309", 5, null));
        assertEquals(employee, new Employee(1, "René", "Rentsch", "+41244303309", 5, 0));
    }

    @Test
    void fetchAll() throws SQLException {
        Employee[] employees = Employee.fetchAll();

        assertNotNull(employees);
        assertEquals(10, employees.length);
        for (Employee employee : employees) {
            assertNotNull(employee);
            assertInstanceOf(Employee.class, employee);
            assertNotNull(employee.getId());
            assertTrue(employee.getId() > 0);
            assertNotNull(employee.getRoleId());
            assertTrue(employee.getRoleId() > 0);
        }
    }

    @Test
    void fetchEveryMechanic() throws SQLException {
        Employee[] employees = Employee.fetchEveryMechanic();

        assertNotNull(employees);
        assertEquals(7, employees.length);
        for (Employee employee : employees) {
            assertNotNull(employee);
            assertInstanceOf(Employee.class, employee);
            assertTrue(employee.getId() > 0);
            assertTrue(employee.getRoleId() > 0);
        }
    }

    @Test
    void saveNotKnowingId() throws SQLException {
        Employee employee = new Employee(ghostId, "Itachi", "Uchiwa", "+41835619384", 5, null);

        Employee savedEmployee = employee.saveNotKnowingId();
        assertNotNull(savedEmployee);
        assertEquals(savedEmployee,
                new Employee(13, employee.getFirstName(), employee.getLastName(), employee.getPhoneNo(),
                        employee.getRoleId(),
                        employee.getSpecializationId()));
    }

    @Test
    void saveKnowingId() throws SQLException {
        Person person = Person.fetchById(4);
        Employee employee = new Employee(person.getId(), person.getFirstName(), person.getLastName(),
                person.getPhoneNo(),
                5,
                null);

        Employee savedEmployee = employee.saveKnowingId();
        assertNotNull(savedEmployee);
        assertEquals(savedEmployee,
                new Employee(person.getId(), employee.getFirstName(), employee.getLastName(), employee.getPhoneNo(),
                        employee.getRoleId(), employee.getSpecializationId()));
    }

    @Test
    void update() throws SQLException {
        Employee originalEmployee = Employee.fetchById(2);
        Employee updatedEmployee = new Employee(originalEmployee.getId(), originalEmployee.getFirstName(),
                originalEmployee.getLastName(), "+689283742398", originalEmployee.getRoleId(),
                originalEmployee.getSpecializationId());
        assertNotEquals(updatedEmployee, originalEmployee);
        assertEquals(updatedEmployee.update(), updatedEmployee);

        Employee retrievedEmployee = Employee.fetchById(2);
        assertNotNull(retrievedEmployee);
        assertEquals(updatedEmployee, retrievedEmployee);
    }

    @Test
    void delete() throws SQLException {
        saveNotKnowingId();
        Employee employee = Employee.fetchById(13);
        assertNotNull(employee);
        assertTrue(Employee.delete(13));
        assertNull(Employee.fetchById(13));
    }

}
