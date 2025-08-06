package me.mato.plugin.base.api.dao;

import com.google.inject.Inject;
import me.mato.plugin.base.api.database.engine.AbstractDatabaseEngine;
import me.mato.plugin.base.api.model.PlayerDataModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlayerDAO {

    private final AbstractDatabaseEngine engine;
    private final Map<UUID, PlayerDataModel> playerCache = new HashMap<>();

    @Inject
    public PlayerDAO(AbstractDatabaseEngine engine) {
        this.engine = engine;
    }

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS players (
                uuid VARCHAR(36) PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                level INT NOT NULL
            )
        """;

        try {
            engine.createTables(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela de jogadores", e);
        }
    }

    public void insertPlayer(PlayerDataModel player) {
        String sql = "INSERT INTO players (uuid, name, level) VALUES (?, ?, ?)";

        engine.executeUpdate(sql, stmt -> {
            stmt.setString(1, player.uuid().toString());
            stmt.setString(2, player.name());
            stmt.setInt(3, player.level());
        });

        playerCache.put(player.uuid(), player);
    }

    public void updatePlayerLevel(UUID uuid, int level) {
        String sql = "UPDATE players SET level = ? WHERE uuid = ?";

        engine.executeUpdate(sql, stmt -> {
            stmt.setInt(1, level);
            stmt.setString(2, uuid.toString());
        });

        PlayerDataModel existing = playerCache.get(uuid);
        if (existing != null) {
            playerCache.put(uuid, new PlayerDataModel(uuid, existing.name(), level));
        }
    }

    public void loadPlayerCache() {
        String sql = "SELECT uuid, name, level FROM players";

        engine.executeQuery(sql, stmt -> {}, rs -> {
            playerCache.clear();
            while (rs.next()) {
                PlayerDataModel player = toModel(rs);
                playerCache.put(player.uuid(), player);
            }
        });
    }

    public Optional<PlayerDataModel> playerData(UUID uuid) {
        return Optional.ofNullable(playerCache.get(uuid));
    }

    public List<PlayerDataModel> allPlayers() {
        return new ArrayList<>(playerCache.values());
    }

    private PlayerDataModel toModel(ResultSet rs) throws SQLException {
        UUID uuid = UUID.fromString(rs.getString("uuid"));
        String name = rs.getString("name");
        int level = rs.getInt("level");
        return new PlayerDataModel(uuid, name, level);
    }

    public AbstractDatabaseEngine engine() {
        return engine;
    }

    public Map<UUID, PlayerDataModel> playerCache() {
        return playerCache;
    }
}