package me.mato.plugin.base.api.database.engine;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.mato.plugin.base.api.database.config.IDatabaseConfig;
import me.mato.plugin.base.api.database.functional.SQLConsumer;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.function.Function;

public abstract class AbstractDatabaseEngine {
    @Getter
    private final JavaPlugin plugin;
    @Getter
    private final IDatabaseConfig databaseConfig;
    private HikariDataSource dataSource;

    public AbstractDatabaseEngine(JavaPlugin plugin, IDatabaseConfig databaseConfig) {
        this.plugin = plugin;
        this.databaseConfig = databaseConfig;
    }

    protected abstract String getJdbcUrl();

    protected abstract void configure(HikariConfig config);

    public void connect() {
        if (dataSource != null && !dataSource.isClosed()) return;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getJdbcUrl());
        config.setMaximumPoolSize(10);
        config.setPoolName("MatoDatabasePool");

        configure(config);

        this.dataSource = new HikariDataSource(config);
        plugin.getLogger().info("Conectado ao banco de dados com HikariCP: " + getJdbcUrl());
    }

    public void disconnect() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            plugin.getLogger().info("Conexão com o banco de dados encerrada.");
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            connect();
        }
        return dataSource.getConnection();
    }

    public void createTables(String... tableStatements) throws SQLException {
        try (Connection conn = getConnection()) {
            for (String sql : tableStatements) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                }
            }
        }
    }

    // Executa um update (INSERT, UPDATE, DELETE)
    public void executeUpdate(String sql, SQLConsumer<PreparedStatement> prepare) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            prepare.accept(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao executar update: " + sql);
            e.printStackTrace();
        }
    }

    // Executa uma query (SELECT) com retorno processado por um handler
    public void executeQuery(String sql, SQLConsumer<PreparedStatement> prepare, SQLConsumer<ResultSet> handler) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            prepare.accept(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                handler.accept(rs);
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao executar query: " + sql);
            e.printStackTrace();
        }
    }

    // Versão com retorno
    public <T> T query(String sql, SQLConsumer<PreparedStatement> prepare, Function<ResultSet, T> mapper) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            prepare.accept(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapper.apply(rs);
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao executar query com retorno: " + sql);
            e.printStackTrace();
            return null;
        }
    }
}