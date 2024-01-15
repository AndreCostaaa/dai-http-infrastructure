package dai.http;

import dai.database.Person;
import io.javalin.http.Context;

import java.sql.SQLException;

public class PersonController {

    public void fetchAll(Context ctx) throws SQLException {
        Person[] people = Person.fetchAll();

        if (people.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(people);
    }


    public void fetchOne(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("personId"));
        Person person = Person.fetchById(id);

        if (person == null) {
            ctx.status(404);
            return;
        }

        ctx.json(person);
    }

    public void save(Context ctx) throws SQLException {
        Person person = ctx.bodyAsClass(Person.class);
        person = person.save();

        if (person == null) {
            ctx.status(400);
            return;
        }

        ctx.json(person);
    }

    public void update(Context ctx) throws SQLException {
        Person person = ctx.bodyAsClass(Person.class);

        if (person.update() == null) {
            ctx.status(400);
            return;
        }

        ctx.json(person);
    }

    public void delete(Context ctx) throws SQLException {
        Integer personId = Integer.parseInt(ctx.pathParam("personId"));
        boolean success = false;

        try {
            success = Person.delete(personId);
        } catch (SQLException e) {
            String[] errorMessage = e.getMessage().split(": ");
            ctx.result(errorMessage[0] + ": " + errorMessage[2]);
        }

        if (!success) {
            ctx.status(400);
            return;
        }

        ctx.status(204);
    }
}
