package dai.http;

import dai.database.Car;
import io.javalin.http.Context;

public class CarController {

    public void fetchOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("carId"));
        Car car = Car.fetchOne(id);
        if(car == null) {
            ctx.status(404);
            return;
        }
        ctx.json(car);
    }

    public void fetchAll(Context ctx){
        Car[] cars = Car.fetchAll();
        if(cars == null) {
            ctx.status(404);
            return;
        }
        ctx.json(cars);
    }

    public void create(Context ctx){
        Car car = ctx.bodyAsClass(Car.class);

        if(Car.create(car)){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("carId"));

        if(Car.delete(id)){
            ctx.status(204);
            return;
        }
        ctx.status(400);

    }

    public void update(Context ctx){
        Car car = ctx.bodyAsClass(Car.class);

        if(Car.update(car)){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }
}
