package dai.database;

public record Person(int id,
                     String firstName,
                     String lastName,
                     String phoneCode,
                     String phoneNo) {
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
