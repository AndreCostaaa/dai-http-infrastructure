package dai.http;

import dai.database.Employee;
import io.javalin.http.Context;

public class EmployeeController {

    public void fetchOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("employeeId"));
        Employee employee = Employee.fetchOne(id);

        if(employee == null){
            ctx.status(404);
            return;
        }

        ctx.json(employee);
    }

    public void fetchAll(Context ctx){
        Employee[] employees = Employee.fetchAll();

        if(employees == null){
            ctx.status(404);
            return;
        }

        ctx.json(employees);
    }

    public void fetchMechanics(Context ctx){
        Employee[] employees = Employee.fetchMechanics();

        if(employees == null){
            ctx.status(404);
            return;
        }

        ctx.json(employees);
    }

    public void create(Context ctx){
        Employee employee = ctx.bodyAsClass(Employee.class);

        if(Employee.create(employee)){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void update(Context ctx){
        Employee employee = ctx.bodyAsClass(Employee.class);

        if(Employee.update(employee)){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("employeeId"));

        if(Employee.delete(id)){
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
