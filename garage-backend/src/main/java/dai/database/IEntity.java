package dai.database;

import java.sql.SQLException;

public interface IEntity {
    void completeCreateStatement(NamedParameterStatement statement) throws SQLException;

    void completeUpdateStatement(NamedParameterStatement statement) throws SQLException;
}
