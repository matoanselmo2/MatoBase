package me.mato.plugin.base.api.database.functional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementResultConsumer {
    void accept(PreparedStatement stmt, ResultSet rs) throws SQLException;
}
