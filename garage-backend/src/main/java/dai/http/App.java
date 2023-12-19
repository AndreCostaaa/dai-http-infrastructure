package dai.http;

import io.javalin.*;
public class App {
    public static void main(String[] args){
        try(Javalin app = Javalin.create().start(7000);){

            //RoleController
            RoleController roleController = new RoleController();
            app.get("/api/roles", roleController::getAll);
            app.get("/api/roles/{roleId}", roleController::getOne);
            app.post("/api/roles/", roleController::create);
            app.patch("/api/roles/", roleController::update);
            app.delete("/api/roles/{roleId}", roleController::delete);

            //ServiceController
            ServiceController serviceController = new ServiceController();
            app.get("/api/services", serviceController::fetchServices);
            app.get("/api/services/{serviceId}", serviceController::fetchServiceById);
            app.get("/api/services/{carId}", serviceController::fetchServiceByCar);
            app.get("/api/services/{carId}/{stateId}", serviceController::fetchServiceByCarState);
            app.get("/api/services/{mechanicId}", serviceController::fetchServiceByMechanic);
            app.get("/api/services/{mechanicId}/{stateId}", serviceController::fetchServiceByMechanicState);
            app.get("/api/services/{mechanicId}", serviceController::fetchServiceByMechanicOrFree);
            app.get("/api/services/{stateId}", serviceController::getServiceByState);
            app.post("/api/services/", serviceController::create);
            app.patch("/api/services/", serviceController::update);
            app.patch("/api/services/incrementState/{serviceId}", serviceController::incrementState);
            app.delete("/api/services/{serviceId}", serviceController::delete);

            //PersonController
            PersonController personController = new PersonController();
            app.get("/api/person", personController::getAll);
            app.get("/api/person/{personId}", personController::getOne);
            app.post("/api/person/", personController::create);
            app.patch("/api/person/{personId}", personController::update);
            app.delete("/api/person/{personId}", personController::delete);
        }
    }
}
