
package dai.http;

import dai.database.Client;
import io.javalin.http.Context;
import java.sql.SQLException;

public class ClientController {

    public void fetchAll(Context ctx) throws SQLException {
        Client[] clients = Client.fetchAll();

        if (clients.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(clients);
    }

    public void fetchOne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("clientId"));
        Client client = Client.fetchById(id);

        if (client == null) {
            ctx.status(404);
            return;
        }

        ctx.json(client);
    }

    public void fetchByPhoneNo(Context ctx) throws SQLException {
        String phoneNo = ctx.pathParam("phoneNo");
        Client[] client = Client.fetchByPhoneNo(phoneNo);

        if (client == null) {
            ctx.status(404);
            return;
        }

        ctx.json(client);
    }

    public void saveNotKnowingId(Context ctx) throws SQLException {
        Client client = ctx.bodyAsClass(Client.class);

        if (client.saveNotKnowingId() != null) {
            ctx.json(client);
            return;
        }

        ctx.status(400);
    }

    public void saveKnowingId(Context ctx) throws SQLException {
        Client client = ctx.bodyAsClass(Client.class);

        if (client.saveKnowingId() != null) {
            ctx.json(client);
            return;
        }

        ctx.status(400);
    }


    public void update(Context ctx) throws SQLException {
        Client client = ctx.bodyAsClass(Client.class);

        if (client.update() != null) {
            ctx.json(client);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx) throws SQLException {
        int clientId = Integer.parseInt(ctx.pathParam("clientId"));

        if (Client.delete(clientId)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
