package me.mato.plugin.game.dao;

import me.mato.plugin.MatoBase;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDAO {

    public void savePlayer(Player player) {
        String query = "INSERT INTO players (uuid, name) VALUES (?, ?)";

        try (Connection conn = MatoBase.getInstance().getDatabaseManager().getEngine().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ) {

            stmt.setString(1, player.getUniqueId().toString());
            stmt.setString(2, player.getName());

            stmt.executeUpdate();

        } catch (SQLException e) {
            MatoBase.getInstance().getLogger().severe("Error saving player data: " + e.getMessage());
        }
    }

}
