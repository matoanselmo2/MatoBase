package me.mato.plugin.api.database.engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseEngine {
    void connect() throws SQLException;
    void disconnect();
    Connection getConnection();

    void createTables(String table) throws SQLException;
    void executeUpdate(String query);
    ResultSet executeQuery(String query);
}
