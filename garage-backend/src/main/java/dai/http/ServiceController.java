package dai.http;

import dai.database.Service;
import dai.database.ServiceDisplay;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;

import java.io.*;
import java.sql.SQLException;

public class ServiceController {

    public void fetchAll(Context ctx) throws SQLException {
        ServiceDisplay[] services = Service.fetchAll();

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchOne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("serviceId"));
        ServiceDisplay service = Service.fetchById(id);

        if (service == null) {
            ctx.status(404);
            return;
        }

        ctx.json(service);
    }

    public void fetchServiceByCar(Context ctx) throws SQLException {
        int carId = Integer.parseInt(ctx.pathParam("carId"));
        ServiceDisplay[] services = Service.fetchByCar(carId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByCarState(Context ctx) throws SQLException {
        int carId = Integer.parseInt(ctx.pathParam("carId"));
        int stateId = Integer.parseInt(ctx.pathParam("stateId"));
        ServiceDisplay[] services = Service.fetchByCarState(carId, stateId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByMechanic(Context ctx) throws SQLException {
        int mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));
        ServiceDisplay[] services = Service.fetchByMechanic(mechanicId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByMechanicState(Context ctx) throws SQLException {
        int mechanicId = Integer.parseInt(ctx.pathParam("mechanicId"));
        int stateId = Integer.parseInt(ctx.pathParam("stateId"));
        ServiceDisplay[] services = Service.fetchByMechanicState(mechanicId, stateId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void fetchServiceByState(Context ctx) throws SQLException {
        Integer stateId = Integer.parseInt(ctx.pathParam("stateId"));

        if (stateId > 4 || stateId < 0) {
            ctx.status(400);
            return;
        }

        ServiceDisplay[] services = Service.fetchByState(stateId);

        if (services.length == 0) {
            ctx.status(404);
            return;
        }

        ctx.json(services);
    }

    public void save(Context ctx) throws SQLException {
        ServiceDisplay serviceDisplay = ctx.bodyAsClass(ServiceDisplay.class);

        Integer mechanicId = serviceDisplay.mechanic() == null ? null : serviceDisplay.mechanic().getId();
        Service service = Service.serviceForCreate(
                mechanicId,
                serviceDisplay.client().getId(),
                serviceDisplay.car().id());

        serviceDisplay = service.save();
        if (serviceDisplay == null) {
            ctx.status(400);
            return;
        }
        ctx.json(serviceDisplay);
    }

    public void update(Context ctx) throws SQLException {
        ServiceDisplay serviceDisplay = ctx.bodyAsClass(ServiceDisplay.class);

        Integer mechanicId = serviceDisplay.mechanic() == null ? null : serviceDisplay.mechanic().getId();
        Service service = Service.serviceForUpdate(
                serviceDisplay.id(),
                mechanicId,
                serviceDisplay.hoursWorked(),
                serviceDisplay.comments(),
                serviceDisplay.hasPictures());

        serviceDisplay = service.update();
        if (serviceDisplay == null) {
            ctx.status(400);
            return;
        }
        ctx.json(serviceDisplay);
    }

    public void incrementState(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("serviceId"));
        ServiceDisplay service = Service.incrementState(id);
        if (service == null) {
            ctx.status(400);
            return;
        }
        ctx.json(service);
    }

    public void delete(Context ctx) throws SQLException {
        Integer id = Integer.parseInt(ctx.pathParam("serviceId"));

        if (Service.delete(id)) {
            ctx.status(204);
            return;
        }

        ctx.status(400);
    }

    public void upload(Context ctx) {
        int serviceId = Integer.parseInt(ctx.pathParam("serviceId"));
        ctx.uploadedFiles().forEach(uploadedFile -> FileUtil.streamToFile(uploadedFile.content(),
                "media/" + serviceId + "/" + uploadedFile.filename()));
    }

    public void download(Context ctx) throws IOException {
        int serviceId = Integer.parseInt(ctx.pathParam("serviceId"));
        String imageId = ctx.pathParam("imageName");

        String imagePath = "media/" + serviceId + "/" + imageId;
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            ctx.status(404);
            return;
        }
        InputStream is = new BufferedInputStream(new FileInputStream(imageFile));
        ctx.header("Content-Disposition", "attachment; filename=\"" + imageFile.getName() + "\"");
        ctx.header("Content-Length", String.valueOf(imageFile.length()));
        ctx.result(is);
    }

    public void getImagesNames(Context ctx) {
        int serviceId = Integer.parseInt(ctx.pathParam("serviceId"));

        String dirPath = "media/" + serviceId + "/";
        File directory = new File(dirPath);

        if (!directory.exists()) {
            ctx.status(404);
            return;
        }

        String[] filNames = directory.list();
        if (filNames == null) {
            ctx.status(404);
            return;
        }
        ctx.json(filNames);
    }
}
