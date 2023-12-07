package dai.http;

public record ServiceBill(int id,
                          int price,
                          boolean delivered,
                          boolean paid,
                          int discountPercentage) {
}
