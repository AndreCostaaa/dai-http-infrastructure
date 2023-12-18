package dai.http;

import dai.database.DatabaseHandler;
import dai.database.Service;
import io.javalin.http.Context;

public class ServiceController {

    public ServiceController(){}

    public void getOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        Service service = DatabaseHandler.getServiceById(id);

        if(service == null){
            ctx.status(404);
            return;
        }

        ctx.json(service);
    }

    public void getAll(Context ctx){
        Service[] services = DatabaseHandler.getServices();

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void getServiceByCar(Context ctx){
        int carId = Integer.parseInt(ctx.pathParam("carId"));

        Service[] services = DatabaseHandler.getServiceByCar(carId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void getServiceByCarState(Context ctx){
        int carId = Integer.parseInt(ctx.pathParam("carId"));
        int stateId = Integer.parseInt(ctx.pathParam("stateId"));

        Service[] services = DatabaseHandler.getServiceByCarState(carId, stateId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void getServiceByMechanic(Context ctx){
        int mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));

        Service[] services = DatabaseHandler.getServiceByMechanic(mechanicId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void getServiceByMechanicState(Context ctx){
        int mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));
        int stateId = Integer.parseInt(ctx.pathParam("stateId"));

        Service[] services = DatabaseHandler.getServiceByMechanicState(mechanicId, stateId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void getServiceByMechanicOrFree(Context ctx){
        int mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));
        int waitingForMechanicState = 1;

        Service[] services = DatabaseHandler.getServiceByMechanicState(mechanicId, waitingForMechanicState);

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

        Service[] services = DatabaseHandler.getServicesByState(stateId);

        if (services == null){
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void create(Context ctx){
        Service service = ctx.bodyAsClass(Service.class);

        if(DatabaseHandler.createService(service)){
            ctx.status(201);
            return;
        }
        ctx.status(400);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        if(DatabaseHandler.deleteService(id)){
            ctx.status(204);
            return;
        }
        ctx.status(400);

    }

    public void update(Context ctx){
        Service service = ctx.bodyAsClass(Service.class);

        if(DatabaseHandler.updateService(service)){
            ctx.status(200);
            return;
        }
        ctx.status(400);
    }

    public void incrementState(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        if(DatabaseHandler.incrementServiceState(id)){
            ctx.status(200);
            return;
        }
        ctx.status(400);
    }
}
