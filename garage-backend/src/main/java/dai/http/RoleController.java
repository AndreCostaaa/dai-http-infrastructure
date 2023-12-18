package dai.http;

import dai.database.Role;
import dai.database.DatabaseHandler;
import io.javalin.http.Context;

public class RoleController {

    public void getOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("roleId"));
        Role role = DatabaseHandler.getRole(id);
        if(role == null) {
            ctx.status(404);
            return;
        }
        ctx.json(role);
    }

    public void getAll(Context ctx){
        Role[] roles = DatabaseHandler.getRoles();
        if(roles == null) {
            ctx.status(404);
            return;
        }
        ctx.json(roles);
    }

    public void create(Context ctx){
        Role role = ctx.bodyAsClass(Role.class);

        if(DatabaseHandler.createRole(role)){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("roleId"));

        if(DatabaseHandler.deleteRole(id)){
            ctx.status(204);
            return;
        }
        ctx.status(400);

    }

    public void update(Context ctx){
        Role role = ctx.bodyAsClass(Role.class);

        if(DatabaseHandler.updateRole(role)){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }
}
