package dai.database;

public class Employee extends Person{

    private final int roleId, specializationId;

    public Employee(int id, String firstName, String lastName, String phoneCode, String phoneNo, int roleId, int specializationId) {
        super(id, firstName, lastName, phoneCode, phoneNo);
        this.roleId = roleId;
        this.specializationId = specializationId;
    }
    public int getSpecializationId() {
        return specializationId;
    }
    public int getRoleId() {
        return roleId;
    }


    static public Employee fetchOne(int employeeId){
        return null;
    }

    static public Employee[] fetchAll(){
        return null;
    }

    static public Employee[] fetchMechanics(){
        return null;
    }

    static public boolean create(Employee employee){
        return true;
    }

    static public boolean createAlreadyInPerson(Employee employee){
        return true;
    }

    static public boolean update(Employee employee){
        return true;
    }

    static public boolean delete(int employeeId){
        return true;
    }
}