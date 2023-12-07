package dai.http;

public record Car(int id,
                  int ownerId,
                  String chassisNo,
                  String recType,
                  String brand,
                  String model,
                  String color) {
}
