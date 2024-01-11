package dai.http;

import dai.database.ServiceBill;
import io.javalin.http.Context;
import java.sql.SQLException;

public class ServiceBillController {

    public void fetchAll(Context ctx) throws SQLException {
        ServiceBill[] bills = ServiceBill.fetchAll();

        if (bills.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(bills);
    }

    public void fetchOne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("serviceBillId"));
        ServiceBill bill = ServiceBill.fetchOne(id);

        if (bill == null) {
            ctx.status(404);
            return;
        }

        ctx.json(bill);
    }

    public void update(Context ctx) throws SQLException {
        ServiceBill bill = ctx.bodyAsClass(ServiceBill.class);

        if (bill.update() != null) {
            ctx.json(bill);
            return;
        }

        ctx.status(400);
    }

    public void delete(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("serviceBillId"));

        if (ServiceBill.delete(id)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }
}
