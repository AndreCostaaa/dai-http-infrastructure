package dai.http;

import dai.database.Role;
import io.javalin.http.Context;

public class RoleController {

    public void fetchOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("roleId"));
        Role role = Role.fetchOne(id);
        if(role == null) {
            ctx.status(404);
            return;
        }
        ctx.json(role);
    }

    public void fetchAll(Context ctx){
        Role[] roles = Role.fetchAll();
        if(roles == null) {
            ctx.status(404);
            return;
        }
        ctx.json(roles);
    }

    public void create(Context ctx){
        Role role = ctx.bodyAsClass(Role.class);

        if(Role.create(role)){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("roleId"));

        if(Role.delete(id)){
            ctx.status(204);
            return;
        }
        ctx.status(400);

    }

    public void update(Context ctx){
        Role role = ctx.bodyAsClass(Role.class);

        if(Role.update(role)){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }
}
