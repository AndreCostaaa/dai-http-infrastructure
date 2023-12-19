package dai.http;

import dai.database.Service;
import io.javalin.http.Context;

public class ServiceController {

    public ServiceController(){}

    public void fetchServiceById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        Service service = Service.fetchById(id);

        if(service == null){
            ctx.status(404);
            return;
        }

        ctx.json(service);
    }

    public void fetchServices(Context ctx){
        Service[] services = Service.fetchAll();

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByCar(Context ctx){
        int carId = Integer.parseInt(ctx.pathParam("carId"));

        Service[] services = Service.fetchByCar(carId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByCarState(Context ctx){
        int carId = Integer.parseInt(ctx.pathParam("carId"));
        int stateId = Integer.parseInt(ctx.pathParam("stateId"));

        Service[] services = Service.fetchByCarState(carId, stateId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByMechanic(Context ctx){
        int mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));

        Service[] services = Service.fetchByMechanic(mechanicId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByMechanicState(Context ctx){
        int mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));
        int stateId = Integer.parseInt(ctx.pathParam("stateId"));

        Service[] services = Service.fetchByMechanicState(mechanicId, stateId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByMechanicOrFree(Context ctx){
        int mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));
        int waitingForMechanicState = 1;

        Service[] services = Service.fetchByMechanicState(mechanicId, waitingForMechanicState);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void getServiceByState(Context ctx){
        int stateId = Integer.parseInt(ctx.pathParam("stateId"));

        if(stateId > 4 || stateId < 0){
            ctx.status(400);
            return;
        }

        Service[] services = Service.fetchByState(stateId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void create(Context ctx){
        Service service = ctx.bodyAsClass(Service.class);

        if(Service.create(service)){
            ctx.status(201);
            return;
        }
        ctx.status(400);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        if(Service.delete(id)){
            ctx.status(204);
            return;
        }
        ctx.status(400);

    }

    public void update(Context ctx){
        Service service = ctx.bodyAsClass(Service.class);

        if(Service.update(service)){
            ctx.status(200);
            return;
        }
        ctx.status(400);
    }

    public void incrementState(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        if(Service.incrementState(id)){
            ctx.status(200);
            return;
        }
        ctx.status(400);
    }
}
