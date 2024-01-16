package dai.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class RoleTest extends GarageTest {

    @Test
    void fetchOne() throws SQLException {
        Role role = Role.fetchOne(1);

        assertNotNull(role);
        assertEquals(role, new Role(1, "Director", true, true, false));
    }

    @Test
    void fetchAll() throws SQLException {
        Role[] roles = Role.fetchAll();

        assertNotNull(roles);
        assertEquals(5, roles.length);
        for (Role role : roles) {
            assertNotNull(role);
            assertInstanceOf(Role.class, role);
            assertNotNull(role.id());
            assertTrue(role.id() > 0);
        }
    }

    @Test
    void save() throws SQLException {
        Role role = new Role(ghostId, "Cook", false, false, false);

        Role savedRole = role.save();
        assertNotNull(savedRole);
        assertEquals(savedRole,
                new Role(6, role.name(), role.canCreate(), role.canAssignOthers(), role.isMechanic()));
    }

    @Test
    void update() throws SQLException {
        Role originalRole = Role.fetchOne(1);
        Role updatedRole = new Role(originalRole.id(), originalRole.name(), originalRole.canCreate(), originalRole.canAssignOthers(), true);
        assertNotEquals(updatedRole, originalRole);
        assertEquals(updatedRole.update(), updatedRole);

        Role retrievedRole = Role.fetchOne(1);
        assertNotNull(retrievedRole);
        assertEquals(updatedRole, retrievedRole);
        assertNotEquals(retrievedRole, originalRole);
    }

    @Test
    void delete() throws SQLException {
        save();
        Role role = Role.fetchOne(6);
        assertNotNull(role);
        assertTrue(Role.delete(6));
        assertNull(Role.fetchOne(6));
    }
}
