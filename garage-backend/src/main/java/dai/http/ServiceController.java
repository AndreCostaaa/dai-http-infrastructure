package dai.http;

import dai.database.Service;
import io.javalin.http.Context;
import java.sql.SQLException;

public class ServiceController {

    public void fetchAll(Context ctx) throws SQLException {
        Service[] services = Service.fetchAll();

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchOne(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("serviceId"));
        Service service = Service.fetchById(id);

        if (service == null) {
            ctx.status(404);
            return;
        }

        ctx.json(service);
    }

    public void fetchServiceByCar(Context ctx) throws SQLException {
        Integer carId = Integer.parseInt(ctx.pathParam("carId"));
        Service[] services = Service.fetchByCar(carId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByCarState(Context ctx) throws SQLException {
        Integer carId = Integer.parseInt(ctx.pathParam("carId"));
        Integer stateId = Integer.parseInt(ctx.pathParam("stateId"));
        Service[] services = Service.fetchByCarState(carId, stateId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByMechanic(Context ctx) throws SQLException {
        Integer mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));
        Service[] services = Service.fetchByMechanic(mechanicId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByMechanicState(Context ctx) throws SQLException {
        Integer mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));
        Integer stateId = Integer.parseInt(ctx.pathParam("stateId"));
        Service[] services = Service.fetchByMechanicState(mechanicId, stateId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByState(Context ctx) throws SQLException {
        Integer stateId = Integer.parseInt(ctx.pathParam("stateId"));

        if (stateId > 4 || stateId < 0) {
            ctx.status(400);
            return;
        }

        Service[] services = Service.fetchByState(stateId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void save(Context ctx) throws SQLException {
        Service service = ctx.bodyAsClass(Service.class);
        service = service.save();

        if (service == null) {
            ctx.status(400);
            return;
        }

        ctx.json(service);
    }

    public void update(Context ctx) throws SQLException {
        Service service = ctx.bodyAsClass(Service.class);
        service = service.update();

        if (service == null) {
            ctx.status(400);
            return;
        }

        ctx.json(service);
    }

    public void incrementState(Context ctx) throws SQLException {
        Service service = ctx.bodyAsClass(Service.class);
        service = service.incrementState();

        if (service == null) {
            ctx.status(400);
            return;
        }

        ctx.json(service);
    }

    public void delete(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("serviceId"));

        if (Service.delete(id)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
