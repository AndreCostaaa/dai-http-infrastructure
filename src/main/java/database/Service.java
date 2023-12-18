package database;

public record Service(int id,
                      int mechanicId,
                      int clientId,
                      int carId,
                      int hoursWorked,
                      String comments,
                      boolean hasPictures,
                      int stateId,
                      int dateCreated,
                      int dateCarArrival,
                      int dateCarProcessing,
                      int dateCarDone,
                      int dateCarLeft) {
}
