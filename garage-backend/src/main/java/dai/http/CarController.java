package dai.http;

import dai.database.Car;
import io.javalin.http.Context;

import java.sql.SQLException;

public class CarController {

    public void fetchOne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("carId"));
        Car car = Car.fetchOne(id);
        if(car == null) {
            ctx.status(404);
            return;
        }
        ctx.json(car);
    }

    public void fetchAll(Context ctx) throws SQLException {
        Car[] cars = Car.fetchAll();
        if(cars == null) {
            ctx.status(404);
            return;
        }
        ctx.json(cars);
    }

    public void create(Context ctx) throws SQLException {
        Car car = ctx.bodyAsClass(Car.class);

        if(car.save()){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("carId"));

        if(Car.delete(id)){
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }

    public void update(Context ctx) throws SQLException {
        Car car = ctx.bodyAsClass(Car.class);

        if(car.update()){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }
}
