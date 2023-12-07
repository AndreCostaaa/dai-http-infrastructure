package dai.http;

public record Role(int id,
                   String name,
                   boolean canCreate,
                   boolean canAssignOthers,
                   boolean isMechanic) {
}
