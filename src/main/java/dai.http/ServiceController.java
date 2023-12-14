package dai.http;

import io.javalin.http.Context;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceController {
    private ConcurrentHashMap<Integer, Service> services = new ConcurrentHashMap<>();

    public ServiceController(){

    }

    public void getOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));
        ctx.json(/* SQL request */ services.get(id));
    }

    public void getAll(Context ctx){
        ctx.json(/* SQL request */ services);
    }

    public void create(Context ctx){
        Service service = ctx.bodyAsClass(Service.class);

        /* SQL request */
        services.put(service.id(), service);
        /**/

        ctx.status(201);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        /* SQL request */
        services.remove(id);
        /**/

        ctx.status(204);
    }

    public void update(Context ctx){
        Service service = ctx.bodyAsClass(Service.class);

        /* SQL request */
        services.put(service.id(), service);
        /**/

        ctx.status(200);
    }
}
