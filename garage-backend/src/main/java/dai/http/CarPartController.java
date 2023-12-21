package dai.http;

import dai.database.CarPart;
import io.javalin.http.Context;

public class CarPartController {

    public void fetchOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("carPartId"));

        CarPart carPart = CarPart.fetchOne(id);
        if(carPart == null) {
            ctx.status(404);
            return;
        }
        ctx.json(carPart);
    }

    public void create(Context ctx){
        CarPart carPart = ctx.bodyAsClass(CarPart.class);

        if(CarPart.create(carPart)){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx){
        int carPartId = Integer.parseInt(ctx.pathParam("carPartId"));

        if(CarPart.delete(carPartId)){
            ctx.status(204);
            return;
        }
        ctx.status(400);
    }

    public void update(Context ctx){
        CarPart carPart = ctx.bodyAsClass(CarPart.class);

        if(CarPart.update(carPart)){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }
}
