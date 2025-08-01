package me.mato.plugin.base.core.service;

import com.google.inject.Inject;
import me.mato.plugin.base.api.dao.PlayerDAO;
import me.mato.plugin.base.core.manager.CommandManager;

public class BootstrapService {

    private final PlayerDAO playerDAO;
    private final CommandManager commandManager;

    @Inject
    public BootstrapService(PlayerDAO playerDAO, CommandManager commandManager) {
        this.playerDAO = playerDAO;
        this.commandManager = commandManager;
    }

    public void initialize() {
        // Inicializa o DAO do jogador
        playerDAO.getEngine().connect();
        playerDAO.createTable();

        // Registra comandos no plugin
        commandManager.registerCommands();

        // Pode ter mais coisas aqui (metrics, placeholderAPI, etc)
    }

    public void shutdown() {
        // Desconecta o DAO do jogador
        playerDAO.getEngine().disconnect();
    }
}
