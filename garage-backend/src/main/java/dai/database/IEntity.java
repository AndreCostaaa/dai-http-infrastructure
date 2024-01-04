package dai.database;

import java.sql.SQLException;

public interface IEntity {
    void completeCreateStatement(NamedParameterStatement wrapper) throws SQLException;
    void completeUpdateStatement(NamedParameterStatement wrapper) throws SQLException;
}
