package dai.http;

import dai.database.Role;
import io.javalin.http.Context;

import java.sql.SQLException;

public class RoleController {

    public void fetchOne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("roleId"));
        Role role = Role.fetchOne(id);
        if(role == null) {
            ctx.status(404);
            return;
        }
        ctx.json(role);
    }

    public void fetchAll(Context ctx) throws SQLException {
        Role[] roles = Role.fetchAll();
        if(roles == null) {
            ctx.status(404);
            return;
        }
        ctx.json(roles);
    }

    public void create(Context ctx) throws SQLException {
        Role role = ctx.bodyAsClass(Role.class);

        if(role.save()){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("roleId"));

        if(Role.delete(id)){
            ctx.status(204);
            return;
        }
        ctx.status(400);

    }

    public void update(Context ctx) throws SQLException {
        Role role = ctx.bodyAsClass(Role.class);

        if(role.update()){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }
}
