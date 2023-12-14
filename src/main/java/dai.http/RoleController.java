package dai.http;

import io.javalin.http.Context;
import java.util.concurrent.ConcurrentHashMap;

public class RoleController {
    private ConcurrentHashMap<Integer, Role> roles = new ConcurrentHashMap<>();

    public RoleController(){
        //test values
        roles.put(5, new Role(5, "Janitor", false, false, false));
        roles.put(4, new Role(4, "Secretary", true, false, false));
        roles.put(2, new Role(2, "Manager", true, true, true));
        roles.put(1, new Role(1, "Director", true, true, false));
        roles.put(3, new Role(3, "Mechanic", false, false, true));
    }

    public void getOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("roleId"));
        ctx.json(/* SQL request */ roles.get(id));
    }

    public void getAll(Context ctx){
        ctx.json(/* SQL request */ roles);
    }

    public void create(Context ctx){
        Role role = ctx.bodyAsClass(Role.class);

        /* SQL request */
        roles.put(role.id(), role);
        /**/

        ctx.status(201);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("roleId"));

        /* SQL request */
        roles.remove(id);
        /**/

        ctx.status(204);
    }

    public void update(Context ctx){
        Role role = ctx.bodyAsClass(Role.class);

        /* SQL request */
        roles.put(role.id(), role);
        /**/

        ctx.status(200);
    }
}
