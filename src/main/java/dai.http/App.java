package dai.http;

import io.javalin.*;
public class App {
    public static void main(String[] args){
        try(Javalin app = Javalin.create().start(7000);){

            RoleController roleController = new RoleController();

            app.get("/api/roles", roleController::getAll);
            app.get("/api/roles/{roleId}", roleController::getOne);
            app.post("/api/roles/", roleController::create);
            app.patch("/api/roles/{roleId}", roleController::update);
            app.delete("/api/roles/{roleId}", roleController::delete);



        }
    }
}
