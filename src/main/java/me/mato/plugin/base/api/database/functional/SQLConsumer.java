package me.mato.plugin.base.api.database.functional;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLConsumer<T> {
    void accept(T t) throws SQLException;
}
