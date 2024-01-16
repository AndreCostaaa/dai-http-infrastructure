package dai.http;

import dai.database.Car;
import dai.database.CarPart;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CarPartController {

    public void fetchAll(Context ctx) throws SQLException {
        CarPart[] carParts = CarPart.fetchAll();

        if (carParts.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(carParts);
    }

    public void fetchOne(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("carPartId"));
        CarPart carPart = CarPart.fetchOne(id);

        if (carPart == null) {
            ctx.status(404);
            return;
        }

        ctx.json(carPart);
    }

    public void fetchByServiceId(Context ctx) throws SQLException {
        Integer serviceId = Integer.parseInt(ctx.pathParam("serviceId"));
        CarPart[] carParts = CarPart.fetchByServiceId(serviceId);

        if (carParts.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(carParts);
    }

    public void save(Context ctx) throws SQLException {
        CarPart carPart = ctx.bodyAsClass(CarPart.class);
        carPart = carPart.save();

        if (carPart == null) {
            ctx.status(400);
            return;
        }
        ctx.json(carPart);
    }

    public void update(Context ctx) throws SQLException {
        CarPart carPart = ctx.bodyAsClass(CarPart.class);

        if (carPart.update() == null) {
            ctx.status(400);
            return;
        }
        ctx.json(carPart);
    }

    public void delete(Context ctx) throws SQLException {
        Integer carPartId = Integer.parseInt(ctx.pathParam("carPartId"));

        if (CarPart.delete(carPartId)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
