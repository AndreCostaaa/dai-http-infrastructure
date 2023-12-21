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
    static public Service fetchOne(int id){
        return null;
    }

    static public Service[] fetchAll(){
        return null;
    }

    /**
     * fetch all services on the car matching carId from the database
     * @return Service[] or null
     */
    static public Service[] fetchByCar(int carId){
        return null;
    }

    /**
     * fetch all services on the car matching carId and with state matching stateId
     * from the database
     * @return Service[] or null
     */
    static public Service[] fetchByCarState(int carId, int stateId){
        return null;
    }

    /**
     * fetch all services assigned to the mechanic matching mechanicId from the database
     * @return Service[] or null
     */
    static public Service[] fetchByMechanic(int mechanicId){
        return null;
    }

    /**
     * fetch all services assigned to the mechanic matching mechanicId and with state
     * matching stateId from the database
     * @return Service[] or null
     */
    static public Service[] fetchByMechanicState(int mechanicId, int stateId){
        return null;
    }

    /**
     * fetch all services with state matching stateId
     * @return Service[] or null
     */
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

    /**
     * increment the state of the service matching serviceId
     * @return true if successful
     */
    static public boolean incrementState(int serviceId){
        return true;
    }
}
