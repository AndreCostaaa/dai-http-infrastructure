package dai.database;

import java.sql.CallableStatement;
import java.sql.SQLException;

public interface IEntity {
    void completeCreateStatement(CallableStatement statement) throws SQLException;
    void completeUpdateStatement(CallableStatement statement) throws SQLException;
}
