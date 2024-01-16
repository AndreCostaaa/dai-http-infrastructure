package dai.http;

import dai.database.Service;
import dai.database.ServiceBill;
import dai.smtp.EmailSender;
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

    public void send(Context ctx) throws SQLException{
        int id = Integer.parseInt(ctx.pathParam("serviceBillId"));

        ServiceBill bill = ServiceBill.fetchOne(id);
        if(bill == null){
            ctx.status(404);
            return;
        }

        int price = bill.price();
        int discount = bill.discountPercentage();
        double total = price * (1 - discount/100.);
        String toEmail = Service.fetchById(bill.id()).client().getEmail();
        String subject = "pay up";
        String body = "Price: " + price + ".-\nDiscount applied: "
                + discount + "%\nTotal: " + total + ".-";

        EmailSender.tlsEmailSend(toEmail, subject, body);

        ServiceBill deliveredBill = new ServiceBill(id, price, true, false, discount);

        if (deliveredBill.update() == null) {
            ctx.status(400);
            return;
        }
        ctx.json(deliveredBill);
    }
}
