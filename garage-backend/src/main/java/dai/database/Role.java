package dai.database;

public record Role(int id,
                   String name,
                   boolean canCreate,
                   boolean canAssignOthers,
                   boolean isMechanic) {
}
