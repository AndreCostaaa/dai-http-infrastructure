package dai.database;

import java.sql.*;
import java.util.*;

/**
 * Credit: <a href=
 * "https://www.infoworld.com/article/2077706/named-parameters-for-preparedstatement.amp.html">
 * InfoWorld, Named Parameters for PreparedStatement Making JDBC code easier to
 * read and write</a>
 * <p>
 * </p>
 * This class wraps around a {@link PreparedStatement} and allows the programmer
 * to set
 * parameters by name instead of by index. This eliminates any confusion as to
 * which
 * parameter index represents what. This also means that rearranging the SQL
 * statement
 * or adding a parameter doesn't involve renumbering your indices.
 *
 * @author adam_crume
 */
public class NamedParameterStatement implements AutoCloseable {
    /**
     * The statement this object is wrapping.
     */
    private final PreparedStatement statement;

    /**
     * Maps parameter names to arrays of ints which are the parameter indices.
     */
    private final Map<String, int[]> indexMap;

    /**
     * * Creates a NamedParameterStatement. Wraps a call to
     * c.{@link Connection#prepareStatement(java.lang.String)
     * prepareStatement}.
     * This constructor is useful if the PreparedStatement needs to be created with
     * extra values
     *
     * @param connection           the database connection
     * @param query                the query that was used to create the statement
     * @param prepareStatementArgs the arguments to be passed to the
     *                             {@link Connection#prepareStatement(java.lang.String)}
     * @throws SQLException if the statement could not be created
     */
    public NamedParameterStatement(Connection connection, String query, int... prepareStatementArgs)
            throws SQLException {
        indexMap = new HashMap<>();
        String parsedQuery = parse(query, indexMap);
        statement = connection.prepareStatement(parsedQuery, prepareStatementArgs);
    }

    /**
     * Parses a query with named parameters. The parameter-index mappings are put
     * into the map,
     * and the parsed query is returned. DO NOT CALL FROM CLIENT CODE. This method
     * is non-private
     * so JUnit code can test it.
     *
     * @param query    query to parse
     * @param paramMap map to hold parameter-index mappings
     * @return the parsed query
     */
    static final String parse(String query, Map<String, int[]> paramMap) {
        /*
         * I was originally using regular expressions, but they didn't work well for
         * ignoring
         * parameter-like strings inside quotes.
         */
        int length = query.length();
        StringBuilder parsedQuery = new StringBuilder(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;

        for (int i = 0; i < length; i++) {
            char c = query.charAt(i);

            if (inSingleQuote) {
                if (c == '\'') {
                    inSingleQuote = false;
                }
            } else if (inDoubleQuote) {
                if (c == '"') {
                    inDoubleQuote = false;
                }
            } else {
                if (c == '\'') {
                    inSingleQuote = true;
                } else if (c == '"') {
                    inDoubleQuote = true;
                } else if (c == ':' && i + 1 < length && Character.isJavaIdentifierStart(query.charAt(i + 1))) {
                    int j = i + 2;
                    while (j < length && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name = query.substring(i + 1, j);
                    c = '?'; // replace the parameter with a question mark
                    i += name.length(); // skip past the end if the parameter

                    int[] indexes = paramMap.computeIfAbsent(name, k -> new int[0]);
                    indexes = Arrays.copyOf(indexes, indexes.length + 1);
                    indexes[indexes.length - 1] = index++;
                    paramMap.put(name, indexes);
                }
            }
            parsedQuery.append(c);
        }

        return parsedQuery.toString();
    }

    /**
     * Returns the indexes for a parameter.
     *
     * @param name parameter name
     * @return parameter indexes
     * @throws IllegalArgumentException if the parameter does not exist
     */
    private int[] getIndexes(String name) {
        int[] indexes = indexMap.get(name);

        if (indexes == null) {
            throw new IllegalArgumentException("Parameter not found: " + name);
        }

        return indexes;
    }

    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setObject(int, java.lang.Object)
     */
    public void setObject(String name, Object value) throws SQLException {
        int[] indexes = getIndexes(name);

        for (int index : indexes) {
            statement.setObject(index, value);
        }
    }

    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(String name, String value) throws SQLException {
        int[] indexes = getIndexes(name);

        for (int index : indexes) {
            statement.setString(index, value);
        }
    }

    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setInt(String name, int value) throws SQLException {
        int[] indexes = getIndexes(name);

        for (int index : indexes) {
            statement.setInt(index, value);
        }
    }

    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setLong(String name, long value) throws SQLException {
        int[] indexes = getIndexes(name);

        for (int index : indexes) {
            statement.setLong(index, value);
        }
    }

    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setDouble(String name, double value) throws SQLException {
        int[] indexes = getIndexes(name);

        for (int index : indexes) {
            statement.setDouble(index, value);
        }
    }

    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setTimestamp(String name, Timestamp value) throws SQLException {
        int[] indexes = getIndexes(name);

        for (int index : indexes) {
            statement.setTimestamp(index, value);
        }
    }


    /**
     * Sets a parameter to NULL in the database.
     *
     * @param name  parameter name
     * @param value SQL type
     * @throws SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setNull(int, int)
     */
    public void setNull(String name, int value) throws SQLException {
        int[] indexes = getIndexes(name);

        for (int index : indexes) {
            statement.setNull(index, value);
        }
    }

    /**
     * Returns the underlying statement.
     *
     * @return the statement
     */
    public PreparedStatement getStatement() {
        return statement;
    }

    /**
     * Executes the statement.
     *
     * @return true if the first result is a {@link ResultSet}
     * @throws SQLException if an error occurred
     * @see PreparedStatement#execute()
     */
    public boolean execute() throws SQLException {
        return statement.execute();
    }

    /**
     * Executes the statement, which must be a query.
     *
     * @return the query results
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery();
    }

    /**
     * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE
     * statement;
     * or an SQL statement that returns nothing, such as a DDL statement.
     *
     * @return number of rows affected
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    /**
     * Executes the given SQL statement, which must be an SQL INSERT, UPDATE or
     * DELETE
     * statement;
     * or an SQL statement that returns nothing, such as a DDL statement, and
     * signals
     * the driver with the given flag about whether the auto-generated keys produced
     * by this Statement object should be made available for retrieval.
     *
     * @param stringQuery       SQL statement to be sent to the database
     * @param autoGeneratedKeys a flag indicating whether auto-generated keys should
     *                          be
     * @return number of rows affected
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeUpdate(String sql, int autoGeneratedKeys)
     */
    public int executeUpdate(String stringQuery, int autoGeneratedKeys) throws SQLException {
        return statement.executeUpdate(stringQuery, autoGeneratedKeys);
    }

    /**
     * Closes the statement.
     *
     * @throws SQLException if an error occurred
     * @see Statement#close()
     */
    public void close() throws SQLException {
        statement.close();
    }

    /**
     * Adds the current set of parameters as a batch entry.
     *
     * @throws SQLException if something went wrong
     */
    public void addBatch() throws SQLException {
        statement.addBatch();
    }

    /**
     * Executes all the batched statements.
     * <p>
     * See {@link Statement#executeBatch()} for details.
     *
     * @return update counts for each statement
     * @throws SQLException if something went wrong
     */
    public int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }
}