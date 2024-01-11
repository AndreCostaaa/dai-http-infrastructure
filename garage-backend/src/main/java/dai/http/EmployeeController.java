
package dai.http;

import dai.database.Employee;
import io.javalin.http.Context;
import java.sql.SQLException;

public class EmployeeController {

    public void fetchOne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("employeeId"));
        Employee employee = Employee.fetchById(id);

        if (employee == null) {
            ctx.status(404);
            return;
        }

        ctx.json(employee);
    }

    public void fetchMechanics(Context ctx) throws SQLException {
        Employee[] mechanics = Employee.fetchEveryMechanic();

        if (mechanics.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(mechanics);
    }

    public void saveNotKnowingId(Context ctx) throws SQLException {
        Employee employee = ctx.bodyAsClass(Employee.class);

        if (employee.saveNotKnowingId() != null) {
            ctx.json(employee);
            return;
        }

        ctx.status(400);
    }

    public void saveKnowingId(Context ctx) throws SQLException {
        Employee employee = ctx.bodyAsClass(Employee.class);

        if (employee.saveKnowingId() != null) {
            ctx.json(employee);
            return;
        }

        ctx.status(400);
    }


    public void update(Context ctx) throws SQLException {
        Employee employee = ctx.bodyAsClass(Employee.class);

        if (employee.update() != null) {
            ctx.json(employee);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx) throws SQLException {
        int employeeId = Integer.parseInt(ctx.pathParam("employeeId"));

        if (Employee.delete(employeeId)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
