package dai.http;

import io.javalin.*;
import io.javalin.http.Context;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(80);

        // CarController
        CarController carController = new CarController();
        app.get("/api/cars", carController::fetchAll);
        app.get("/api/cars/{carId}", carController::fetchOne);
        app.post("/api/cars", carController::save);
        app.patch("/api/cars", carController::update);
        app.delete("/api/cars/{carId}", carController::delete);

        // CarPartController
        CarPartController carPartController = new CarPartController();
        app.get("/api/carParts", carPartController::fetchAll);
        app.get("/api/carParts/{carPartId}", carPartController::fetchOne);
        app.post("/api/carParts", carPartController::save);
        app.patch("/api/carParts", carPartController::update);
        app.delete("/api/carParts/{carPartId}", carPartController::delete);

        // ClientController
        ClientController clientController = new ClientController();
        app.get("/api/clients", clientController::fetchAll);
        app.get("/api/clients/{clientId}", clientController::fetchOne);
        app.get("/api/clients/phoneNo/{phoneNo}", clientController::fetchByPhoneNo);
        app.post("/api/clients", clientController::saveNotKnowingId);
        app.post("/api/clients/knownId", clientController::saveKnowingId);
        app.patch("/api/clients/update", clientController::update);
        app.delete("/api/clients/{clientId}", clientController::delete);

        // EmployeeController
        EmployeeController employeeController = new EmployeeController();
        app.get("/api/employees", employeeController::fetchAll);
        app.get("/api/employees/{employeeId}", employeeController::fetchOne);
        app.get("/api/mechanics", employeeController::fetchMechanics);
        app.post("/api/employees", employeeController::saveNotKnowingId);
        app.post("/api/employees/knownId/", employeeController::saveKnowingId);
        app.patch("/api/employees", employeeController::update);
        app.delete("/api/employees/{employeeId}", employeeController::delete);

        // PersonController
        PersonController personController = new PersonController();
        app.get("/api/person", personController::fetchAll);
        app.get("/api/person/{personId}", personController::fetchOne);
        app.post("/api/person", personController::save);
        app.patch("/api/person", personController::update);
        app.delete("/api/person/{personId}", personController::delete);

        // RoleController
        RoleController roleController = new RoleController();
        app.get("/api/roles", roleController::fetchAll);
        app.get("/api/roles/{roleId}", roleController::fetchOne);
        app.post("/api/roles", roleController::save);
        app.patch("/api/roles", roleController::update);
        app.delete("/api/roles/{roleId}", roleController::delete);

        // ServiceController
        ServiceController serviceController = new ServiceController();
        app.get("/api/services", serviceController::fetchAll);
        app.get("/api/services/{serviceId}", serviceController::fetchOne);
        app.get("/api/services/car/{carId}", serviceController::fetchServiceByCar);
        app.get("/api/services/car/{carId}/{stateId}", serviceController::fetchServiceByCarState);
        app.get("/api/services/mechanic/{mechanicId}", serviceController::fetchServiceByMechanic);
        app.get("/api/services/mechanic/{mechanicId}/{stateId}", serviceController::fetchServiceByMechanicState);
        app.get("/api/services/state/{stateId}", serviceController::fetchServiceByState);
        app.get("/api/services/processing/{mechanicId}", serviceController::fetchServiceByMechanicProcessing);
        app.post("/api/services", serviceController::save);
        app.patch("/api/services", serviceController::update);
        app.patch("/api/services/{serviceId}", serviceController::incrementState);
        app.delete("/api/services/{serviceId}", serviceController::delete);

        // ServiceBillController
        ServiceBillController serviceBillController = new ServiceBillController();
        app.get("/api/serviceBills", serviceBillController::fetchAll);
        app.get("/api/serviceBills/{serviceBillId}", serviceBillController::fetchOne);
        app.patch("/api/serviceBills", serviceBillController::update);
        app.delete("/api/serviceBills/{serviceBillId}", serviceBillController::delete);

        app.get("/api/stickySessionTest", (Context ctx) -> {
            System.out.println(String.format("%d Sticky Session test !", System.currentTimeMillis()));
        });
    }
}
