package me.mato.plugin.base.command.impl;

import me.mato.plugin.base.command.BaseCommand;
import me.mato.plugin.base.command.CommandContext;
import me.mato.plugin.base.command.impl.sub.ExampleSubCommand;

import java.util.List;

public class ExampleCommand extends BaseCommand {
    public ExampleCommand() {
        super("example", "Um comando de exemplo para demonstração do sistema de argumentos");
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

    @Override
    public List<String> tab(CommandContext context) {
        return List.of();
    }
}
