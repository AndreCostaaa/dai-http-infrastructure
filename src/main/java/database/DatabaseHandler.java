package database;

public class DatabaseHandler {

    private DatabaseHandler(){}

    //Role requests @TODO
    static public Role getRole(int id){
        return null;
    }

    static public Role[] getRoles(){
        return null;
    }

    static public boolean createRole(Role role){
        return true;
    }

    static public boolean deleteRole(int RoleId){
        return true;
    }

    static public boolean updateRole(Role role){
        return true;
    }

    //Service requests----------
    static public Service getServiceById(int id){
        return null;
    }

    static public Service[] getServices(){
        return null;
    }

    static public Service[] getServiceByCar(int carId){
        return null;
    }

    static public Service[] getServiceByCarState(int carId, int stateId){
        return null;
    }

    static public Service[] getServiceByMechanic(int mechanicId){
        return null;
    }

    static public Service[] getServiceByMechanicState(int mechanicId, int stateId){
        return null;
    }

    static public Service[] getServicesByState(int stateId){
        return null;
    }

    static public boolean createService(Service service){
        return true;
    }

    static public boolean deleteService(int serviceId){
        return true;
    }

    static public boolean updateService(Service service){
        return true;
    }

    static public boolean incrementServiceState(int serviceId){
        return true;
    }


}
