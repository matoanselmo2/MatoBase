package me.mato.plugin.base.data.dao;

import com.google.inject.Inject;
import me.mato.plugin.base.data.database.engine.AbstractDatabaseEngine;
import me.mato.plugin.base.data.model.PlayerDataModel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDAO {
    private final AbstractDatabaseEngine engine;
    private final Map<UUID, PlayerDataModel> playerCache = new HashMap<>();

    @Inject
    public PlayerDAO(AbstractDatabaseEngine engine) {
        this.engine = engine;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                     "uuid VARCHAR(36) PRIMARY KEY, " +
                     "name VARCHAR(255) NOT NULL, " +
                     "level INT NOT NULL" +
                     ")";

        try {
            engine.createTables(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadPlayerCache();
    }

    public void insertPlayer(String uuid, String name, int level) {
        String sql = "INSERT INTO players (uuid, name, level) VALUES (?, ?, ?)";

        engine.update(sql, stmt -> {
            try {
                stmt.setString(1, uuid);
                stmt.setString(2, name);
                stmt.setInt(3, level);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        loadPlayerCache();
    }

    public void updatePlayer(UUID uuid, int level) {
        String sql = "UPDATE players SET level = ? WHERE uuid = ?";

        engine.update(sql, stmt -> {
            try {
                stmt.setInt(1, level);
                stmt.setString(2, uuid.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        loadPlayerCache();
    }

    public void loadPlayerCache() {
        String sql = "SELECT uuid, name, level FROM players";

        engine.query(sql, rs -> {
            try {
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("uuid"));
                    String name = rs.getString("name");
                    int level = rs.getInt("level");

                    PlayerDataModel playerData = new PlayerDataModel(uuid, name, level);
                    playerCache.put(uuid, playerData);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public PlayerDataModel getPlayerData(UUID uuid) {
        return playerCache.get(uuid);
    }

    public AbstractDatabaseEngine getEngine() {
        return engine;
    }

    public Map<UUID, PlayerDataModel> getPlayerCache() {
        return playerCache;
    }
}
