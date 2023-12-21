package dai.database;

public record Car(int id,
                  int ownerId,
                  String chassisNo,
                  String recType,
                  String brand,
                  String model,
                  String color) {

    static public Car fetchOne(int id){
        return null;
    }

    static public Car[] fetchAll(){
        return null;
    }

    static public boolean create(Car car){
        return true;
    }

    static public boolean update(Car car){
        return true;
    }

    static public boolean delete(int id){
        return true;
    }
}

//car requests @TODO

