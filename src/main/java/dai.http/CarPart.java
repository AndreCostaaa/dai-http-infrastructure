package dai.http;

public record CarPart(int id,
                      int serviceId,
                      String supplier,
                      int supplierRef,
                      String name,
                      String description,
                      double buyPrice,
                      double sellPrice) {
}
