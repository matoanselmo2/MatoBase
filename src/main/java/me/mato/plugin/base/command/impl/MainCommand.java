package me.mato.plugin.base.command.impl;

import com.google.inject.Inject;
import me.mato.plugin.base.MatoBase;
import me.mato.plugin.base.command.BaseCommand;
import me.mato.plugin.base.command.CommandContext;
import me.mato.plugin.base.data.dao.PlayerDAO;

import java.util.List;

public class MainCommand extends BaseCommand {

    private final PlayerDAO playerDAO;

    @Inject
    public MainCommand(MatoBase plugin, PlayerDAO playerDAO) {
        super(plugin.getName().toLowerCase(), "Comando principal do plugin " + plugin.getName());
        this.playerDAO = playerDAO;
    }

    @Override
    public void execute(CommandContext context) {
        context.sender().sendMessage("Você executou o comando principal do plugin e salvou os dados do jogador!");

        playerDAO.updatePlayer(context.player().getUniqueId(), context.player().getLevel());

        if (playerDAO.getPlayerData(context.player().getUniqueId()) == null) {
            playerDAO.insertPlayer(context.player().getUniqueId().toString(), context.player().getName(), context.player().getLevel());
            context.player().sendMessage("Dados do jogador inseridos com sucesso!");

            return;
        } else {
            context.player().sendMessage("Dados do jogador já existem, atualizando...");
        }

        int playerData = playerDAO.getPlayerData(context.player().getUniqueId()).getLevel();

        context.player().sendMessage("Seu XP é: " + playerData);
    }

    @Override
    public List<String> tab(CommandContext context) {
        return List.of();
    }
}
