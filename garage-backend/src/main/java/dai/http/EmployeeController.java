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
        int id = Integer.parseInt(ctx.pathParam("employeeId"));
        Employee employee = Employee.fetchOne(id);

        if (employee == null) {
            ctx.status(404);
            return;
        }

        ctx.json(employee);
    }

    public void fetchEveryMechanic(Context ctx) throws SQLException {
        Employee[] employees = Employee.fetchEveryMechanic();

        if (employees.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(employees);
    }

    public void saveNotKnowingId(Context ctx) throws SQLException {
        Employee employee = ctx.bodyAsClass(Employee.class);

        if (employee.saveNotKnowingId()) {
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void saveKnowingId(Context ctx) throws SQLException {
        Employee employee = ctx.bodyAsClass(Employee.class);

        if (employee.saveKnowingId()) {
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }


    public void update(Context ctx) throws SQLException {
        Employee employee = ctx.bodyAsClass(Employee.class);

        if (employee.update()) {
            ctx.status(200);
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
