package me.mato.plugin.base.core.service;

import com.google.inject.Inject;
import me.mato.plugin.base.api.dao.PlayerDAO;
import me.mato.plugin.base.core.manager.CommandManager;
import me.mato.plugin.base.core.registry.PluginPermissionWrapper;

public class BootstrapService {

    private final PluginPermissionWrapper permissionWrapper;

    private final PlayerDAO playerDAO;
    private final CommandManager commandManager;

    @Inject
    public BootstrapService(PluginPermissionWrapper permissionWrapper, PlayerDAO playerDAO, CommandManager commandManager) {
        this.permissionWrapper = permissionWrapper;
        this.playerDAO = playerDAO;
        this.commandManager = commandManager;
    }

    public void initialize() {
        // Registra todas as permissões do plugin
        permissionWrapper.registerAll();

        // Inicializa o DAO do jogador
        playerDAO.engine().connect();
        playerDAO.createTable();

        // Registra comandos no plugin
        commandManager.registerCommands();

        // Pode ter mais coisas aqui (metrics, placeholderAPI, etc)
    }

    public void shutdown() {
        // Desconecta o DAO do jogador
        playerDAO.engine().disconnect();
    }
}
