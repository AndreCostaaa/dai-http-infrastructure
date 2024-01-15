
package dai.http;

import dai.database.Employee;
import io.javalin.http.Context;
import java.sql.SQLException;

public class EmployeeController {

    public void fetchAll(Context ctx) throws SQLException {
        Employee[] employees = Employee.fetchAll();

        if (employees.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(employees);
    }

    public void fetchOne(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("employeeId"));
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
        employee = employee.saveNotKnowingId();

        if (employee == null) {
            ctx.status(400);
            return;
        }

        ctx.json(employee);
    }

    public void saveKnowingId(Context ctx) throws SQLException {
        Employee employee = ctx.bodyAsClass(Employee.class);
        employee = employee.saveKnowingId();

        if (employee == null) {
            ctx.status(400);
            return;
        }

        ctx.json(employee);
    }


    public void update(Context ctx) throws SQLException {
        Employee employee = ctx.bodyAsClass(Employee.class);

        if (employee.update() == null) {
            ctx.status(400);
            return;
        }

        ctx.json(employee);
    }

    public void delete(Context ctx) throws SQLException {
        Integer employeeId = Integer.parseInt(ctx.pathParam("employeeId"));

        if (Employee.delete(employeeId)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
