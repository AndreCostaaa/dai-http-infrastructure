package dai.database;

public record Service(int id,
                      int mechanicId,
                      int clientId,
                      int carId,
                      int hoursWorked,
                      String comments,
                      boolean hasPictures,
                      int stateId,
                      int dateCreated,
                      int dateCarArrival,
                      int dateCarProcessing,
                      int dateCarDone,
                      int dateCarLeft) {
    //service requests @TODO
    static public Service fetchById(int id){
        return null;
    }

    static public Service[] fetchAll(){
        return null;
    }

    static public Service[] fetchByCar(int carId){
        return null;
    }

    static public Service[] fetchByCarState(int carId, int stateId){
        return null;
    }

    static public Service[] fetchByMechanic(int mechanicId){
        return null;
    }

    static public Service[] fetchByMechanicState(int mechanicId, int stateId){
        return null;
    }

    static public Service[] fetchByState(int stateId){
        return null;
    }

    static public boolean create(Service service){
        return true;
    }

    static public boolean delete(int serviceId){
        return true;
    }

    static public boolean update(Service service){
        return true;
    }

    static public boolean incrementState(int serviceId){
        return true;
    }
}
