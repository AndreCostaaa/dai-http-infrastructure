package dai.database;

public record ServiceBill(int id,
                          int price,
                          boolean delivered,
                          boolean paid,
                          int discountPercentage) {

    static public ServiceBill fetchOne(int id){
        return null;
    }

    static public boolean update(ServiceBill serviceBill){
        return true;
    }

    static public boolean delete(int billId){
        return true;
    }
}
