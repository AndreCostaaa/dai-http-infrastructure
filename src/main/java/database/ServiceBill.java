package database;

public record ServiceBill(int id,
                          int price,
                          boolean delivered,
                          boolean paid,
                          int discountPercentage) {
}
