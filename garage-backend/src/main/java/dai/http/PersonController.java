package dai.http;

import dai.database.Person;
import io.javalin.http.Context;

public class PersonController {

    public void getOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("serviceId"));

        Person person = Person.fetchOne(id);

        if(person == null){
            ctx.status(404);
            return;
        }

        ctx.json(person);
    }

    public void getAll(Context ctx){
        Person[] people = Person.fetchAll();

        if (people == null){
            ctx.status(404);
            return;
        }

        ctx.json(people);
    }

    public void create(Context ctx){
        Person person = ctx.bodyAsClass(Person.class);

        if(Person.create(person)){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void update(Context ctx){
        Person person = ctx.bodyAsClass(Person.class);

        if(Person.update(person)){
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
