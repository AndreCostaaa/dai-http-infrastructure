package dai.http;

import dai.database.Role;
import io.javalin.http.Context;
import java.sql.SQLException;

public class RoleController {

    public void fetchAll(Context ctx) throws SQLException {
        Role[] roles = Role.fetchAll();

        if (roles.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(roles);
    }

    public void fetchOne(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("roleId"));
        Role role = Role.fetchOne(id);

        if (role == null) {
            ctx.status(404);
            return;
        }

        ctx.json(role);
    }

    public void save(Context ctx) throws SQLException {
        Role role = ctx.bodyAsClass(Role.class);
        role = role.save();

        if (role == null) {
            ctx.status(400);

            return;
        }

        ctx.json(role);
    }

    public void update(Context ctx) throws SQLException {
        Role role = ctx.bodyAsClass(Role.class);

        if (role.update() == null) {
            ctx.status(400);
            return;
        }

        ctx.json(role);
    }

    public void delete(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("roleId"));

        if (Role.delete(id)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
