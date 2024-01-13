package dai.http;

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
        int id = Integer.parseInt(ctx.pathParam("carPartId"));
        CarPart carPart = CarPart.fetchOne(id);

        if (carPart == null) {
            ctx.status(404);
            return;
        }

        ctx.json(carPart);
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
        int carPartId = Integer.parseInt(ctx.pathParam("carPartId"));

        if (CarPart.delete(carPartId)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
