package dai.http;

import dai.database.Person;
import io.javalin.http.Context;

import java.sql.SQLException;

public class PersonController {

    public void fetchOne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        Person person = Person.fetchOne(id);

        if(person == null){
            ctx.status(404);
            return;
        }

        ctx.json(person);
    }

    public void fetchAll(Context ctx) throws SQLException {
        Person[] people = Person.fetchAll();

        if (people == null){
            ctx.status(404);
            return;
        }

        ctx.json(people);
    }

    public void save(Context ctx) throws SQLException {
        Person person = ctx.bodyAsClass(Person.class);

        if(person.save()){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void update(Context ctx) throws SQLException {
        Person person = ctx.bodyAsClass(Person.class);

        if(person.update()){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx){
        int personId = Integer.parseInt(ctx.pathParam("personId"));

        if(Person.delete(personId)){
            ctx.status(204);
            return;
        }
        ctx.status(400);
    }


}
