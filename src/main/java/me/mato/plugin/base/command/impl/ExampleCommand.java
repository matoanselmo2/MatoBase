package me.mato.plugin.base.command.impl;

import me.mato.plugin.base.command.BaseCommand;
import me.mato.plugin.base.command.CommandContext;
import me.mato.plugin.base.command.impl.sub.ExampleSubCommand;
import me.mato.plugin.base.util.Permission;

public class ExampleCommand extends BaseCommand {
    public ExampleCommand() {
        super("example", "Um comando de exemplo para demonstração do sistema de argumentos", Permission.EXEMPLO);
        registerSubCommand(new ExampleSubCommand());
    }

    @Override
    public void execute(CommandContext context) {
        context.sender().sendMessage("Você executou o comando de exemplo!");

        if (context.args().length > 0) {
            context.sender().sendMessage("Argumentos fornecidos: " + String.join(", ", context.args()));
        } else {
            context.sender().sendMessage("Nenhum argumento fornecido.");
        }
    }
}
