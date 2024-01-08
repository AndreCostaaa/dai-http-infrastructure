package dai.http;

import dai.database.Client;
import io.javalin.http.Context;

public class ClientController {

    public void fetchOne(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("clientId"));
        Client client = Client.fetchOne(id);

        if(client == null){
            ctx.status(404);
            return;
        }

        ctx.json(client);
    }

    public void fetchAll(Context ctx){
        Client[] clients = Client.fetchAll();

        if(clients == null){
            ctx.status(404);
            return;
        }

        ctx.json(clients);
    }

    public void fetchByPhone(Context ctx){
        String phoneCode = ctx.pathParam("phoneCode");
        String phoneNo = ctx.pathParam("phoneNo");

        Client[] clients = Client.fetchByPhone(phoneCode, phoneNo);

        if(clients == null){
            ctx.status(404);
            return;
        }

        ctx.json(clients);
    }

    public void create(Context ctx){
        Client client = ctx.bodyAsClass(Client.class);

        if(Client.create(client)){
            ctx.status(201);
            return;
        }

        ctx.status(400);
    }

    public void update(Context ctx){
        Client client = ctx.bodyAsClass(Client.class);

        if(Client.update(client)){
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("clientId"));

        if(Client.delete(id)){
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
