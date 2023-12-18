package database;

public record Client(int id,
                     String email,
                     String street,
                     int streetNo,
                     int npa,
                     String country) {
}
