package dai.http;

import io.javalin.*;

public class App {
    public static void main(String[] args) {
        try (Javalin app = Javalin.create().start(7000)) {

            //CarController
            CarController carController = new CarController();
            app.get("/api/cars", carController::fetchAll);
            app.get("/api/cars/{carId}", carController::fetchOne);
            app.post("/api/cars/", carController::save);
            app.patch("/api/cars/update/", carController::update);
            app.delete("/api/cars/{serviceId}", carController::delete);

            //CarPartController
            CarPartController carPartController = new CarPartController();
            app.get("/api/carParts", carPartController::fetchAll);
            app.get("/api/carParts/{carPartId}", carPartController::fetchOne);
            app.post("/api/carParts/", carPartController::save);
            app.patch("/api/carParts/update/", carPartController::update);
            app.delete("/api/carParts/{carPartId}", carPartController::delete);

            //ClientController
            ClientController clientController = new ClientController();
            app.get("/api/clients", clientController::fetchAll);
            app.get("/api/clients/{clientId}", clientController::fetchOneById);
            app.get("/api/clients/{phoneNo}", clientController::fetchOneByPhoneNo);
            app.post("/api/clients/", clientController::saveNotKnowingId);
            app.post("/api/clients/", clientController::saveKnowingId);
            app.patch("/api/clients/update/", clientController::update);
            app.delete("/api/clients/{clientId}", clientController::delete);

            //EmployeeController
            EmployeeController employeeController = new EmployeeController();
            app.get("/api/employees", employeeController::fetchAll);
            app.get("/api/employees/{employeeId}", employeeController::fetchOne);
            app.get("/api/employees/{employeeId}", employeeController::fetchEveryMechanic);
            app.post("/api/employees/", employeeController::saveNotKnowingId);
            app.post("/api/employees/", employeeController::saveKnowingId);
            app.patch("/api/employees/update/", employeeController::update);
            app.delete("/api/employees/{employeeId}", employeeController::delete);

            //PersonController
            PersonController personController = new PersonController();
            app.get("/api/person", personController::fetchAll);
            app.get("/api/person/{personId}", personController::fetchOne);
            app.post("/api/person/", personController::save);
            app.patch("/api/person/update/", personController::update);
            app.delete("/api/person/{personId}", personController::delete);

            //RoleController
            RoleController roleController = new RoleController();
            app.get("/api/roles", roleController::fetchAll);
            app.get("/api/roles/{roleId}", roleController::fetchOne);
            app.post("/api/roles/", roleController::save);
            app.patch("/api/roles/update/", roleController::update);
            app.delete("/api/roles/{roleId}", roleController::delete);

            //ServiceController
            ServiceController serviceController = new ServiceController();
            app.get("/api/services", serviceController::fetchAll);
            app.get("/api/services/{serviceId}", serviceController::fetchOne);
            app.get("/api/services/{carId}", serviceController::fetchServiceByCar);
            app.get("/api/services/{carId}/{stateId}", serviceController::fetchServiceByCarState);
            app.get("/api/services/{mechanicId}", serviceController::fetchServiceByMechanic);
            app.get("/api/services/{mechanicId}/{stateId}", serviceController::fetchServiceByMechanicState);
            app.get("/api/services/{stateId}", serviceController::fetchServiceByState);
            app.get("/api/services/{mechanicId}", serviceController::fetchServiceByMechanicProcessing);
            app.get("/api/services/0", serviceController::fetchServiceCreated);
            app.get("/api/services/1", serviceController::fetchServiceWaiting);
            app.get("/api/services/2", serviceController::fetchServiceProcessing);
            app.get("/api/services/3", serviceController::fetchServiceDone);
            app.get("/api/services/4", serviceController::fetchServiceLeft);
            app.post("/api/services/", serviceController::save);
            app.patch("/api/services/update/", serviceController::update);
            app.patch("/api/services/incrementState/", serviceController::incrementState);
            app.delete("/api/services/{serviceId}", serviceController::delete);
                    
            //ServiceBillController
            ServiceBillController serviceBillController = new ServiceBillController();
            app.get("/api/serviceBills", serviceBillController::fetchAll);
            app.get("/api/serviceBills/{serviceBillId}", serviceBillController::fetchOne);
            app.post("/api/serviceBills/update", serviceBillController::update);
            app.delete("/api/serviceBills/{serviceBillId}", serviceBillController::delete);
        }
    }
}
