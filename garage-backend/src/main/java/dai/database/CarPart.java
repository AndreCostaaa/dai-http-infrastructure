package dai.database;

public record CarPart(int id,
                      int serviceId,
                      String supplier,
                      int supplierRef,
                      String name,
                      String description,
                      double buyPrice,
                      double sellPrice) {

    static public CarPart fetchOne(int id){
        return null;
    }

    static public boolean create(CarPart carPart){
        return true;
    }

    static public boolean update(CarPart carPart){
        return true;
    }

    static public boolean delete(int id){
        return true;
    }
}
