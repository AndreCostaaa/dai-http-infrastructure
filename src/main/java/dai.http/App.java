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
            app.get("/api/services", roleController::getAll);
            app.get("/api/services/{serviceId}", roleController::getOne);
            app.post("/api/services/", roleController::create);
            app.patch("/api/services/{serviceId}", roleController::update);
            app.delete("/api/services/{serviceId}", roleController::delete);

            
        }
    }
}
