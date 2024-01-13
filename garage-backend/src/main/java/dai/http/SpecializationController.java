package dai.http;

import dai.database.Specialization;
import io.javalin.http.Context;
import java.sql.SQLException;

public class SpecializationController {

    public void fetchAll(Context ctx) throws SQLException {
        Specialization[] roles = Specialization.fetchAll();

        if (roles.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(roles);
    }

    public void fetchOne(Context ctx) throws SQLException {

        Integer id = Utils.parseIntOrNull(ctx.pathParam("specializationId"));
        if (id == null) {
            ctx.status(404);
            return;
        }

        Specialization specialization = Specialization.fetchById(id);

        if (specialization == null) {
            ctx.status(404);
            return;
        }

        ctx.json(specialization);
    }

    public void save(Context ctx) throws SQLException {
        Specialization specialization = ctx.bodyAsClass(Specialization.class);
        specialization = specialization.save();
        if (specialization == null) {
            ctx.status(400);
            return;
        }
        ctx.json(specialization);
    }

    public void update(Context ctx) throws SQLException {
        Specialization specialization = ctx.bodyAsClass(Specialization.class);
        if (specialization.update() == null) {
            ctx.status(400);
            return;
        }
        ctx.json(specialization);
    }

    public void delete(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("specializationId"));

        if (Specialization.delete(id)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
