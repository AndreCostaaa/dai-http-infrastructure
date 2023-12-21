package dai.database;

public class Person{
    private final int id;
    private final String firstName, lastName, phoneCode, phoneNo;

    public Person(int id,
                  String firstName,
                  String lastName,
                  String phoneCode,
                  String phoneNo){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneCode = phoneCode;
        this.phoneNo = phoneNo;
    }

    public int id(){
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getPhoneCode(){
        return phoneCode;
    }
    public String getPhoneNo(){
        return phoneNo;
    }

    //Person requests @TODO
    static public Person fetchOne(int personId){
        return null;
    }

    static public Person[] fetchAll(){
        return null;
    }

    static public boolean create(Person person){
        return true;
    }

    static public boolean update(Person person){
        return true;
    }

    static public boolean delete(int personId){
        return true;
    }
}
