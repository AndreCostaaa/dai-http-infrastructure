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
            app.patch("/api/roles/{roleId}", roleController::update);
            app.delete("/api/roles/{roleId}", roleController::delete);

            //ServiceController
            ServiceController serviceController = new ServiceController();
            app.get("/api/services", serviceController::getAll);
            app.get("/api/services/{serviceId}", serviceController::getOne);
            app.get("/api/services/{carId}", serviceController::getServiceByCar);
            app.get("/api/services/{carId}/{stateId}", serviceController::getServiceByCarState);
            app.get("/api/services/{mechanicId}", serviceController::getServiceByMechanic);
            app.get("/api/services/{mechanicId}/{stateId}", serviceController::getServiceByMechanicState);
            app.get("/api/services/{mechanicId}", serviceController::getServiceByMechanicOrFree);
            app.get("/api/services/{stateId}", serviceController::getServiceByState);
            app.post("/api/services/", serviceController::create);
            app.patch("/api/services/{serviceId}", serviceController::update);
            app.patch("/api/services/incrementState/{serviceId}", serviceController::incrementState);
            app.delete("/api/services/{serviceId}", serviceController::delete);
        }
    }
}
