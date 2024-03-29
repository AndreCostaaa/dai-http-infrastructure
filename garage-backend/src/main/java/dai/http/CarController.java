package dai.http;

import dai.database.Car;
import dai.database.Service;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CarController {

    public void fetchAll(Context ctx) throws SQLException {
        Car[] cars = Car.fetchAll();

        if (cars.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(cars);
    }

    public void fetchOne(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("carId"));
        Car car = Car.fetchOne(id);

        if (car == null) {
            ctx.status(404);
            return;
        }

        ctx.json(car);
    }

    public void fetchByOwnerId(Context ctx) throws SQLException {
        Integer ownerId = Integer.parseInt(ctx.pathParam("ownerId"));
        Car[] cars = Car.fetchByOwnerId(ownerId);

        if (cars.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(cars);
    }

    public void save(Context ctx) throws SQLException {
        Car car = ctx.bodyAsClass(Car.class);

        car = car.save();
        if (car == null) {
            ctx.status(400);
            return;
        }
        ctx.json(car);
    }

    public void update(Context ctx) throws SQLException {
        Car car = ctx.bodyAsClass(Car.class);

        if (car.update() == null) {
            ctx.status(400);
            return;
        }
        ctx.json(car);
    }

    public void delete(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("carId"));

        if (Car.delete(id)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
