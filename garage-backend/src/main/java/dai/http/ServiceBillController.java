package dai.http;

import dai.database.ServiceBill;
import io.javalin.http.Context;

public class ServiceBillController {

    public void fetchOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("billId"));
        ServiceBill bill = ServiceBill.fetchOne(id);

        if (bill == null) {
            ctx.status(404);
            return;
        }

        ctx.json(bill);
    }

    public void update(Context ctx) {
        ServiceBill bill = ctx.bodyAsClass(ServiceBill.class);

        if (ServiceBill.update(bill)) {
            ctx.status(200);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("billId"));

        if (ServiceBill.delete(id)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
