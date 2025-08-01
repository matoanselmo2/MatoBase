package me.mato.plugin.base.data.database.functional;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLBiConsumer<T, U> {
    void accept(T t, U u) throws SQLException;
}