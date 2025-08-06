package me.mato.plugin.base.command.impl.sub;

import me.mato.plugin.base.command.CommandContext;
import me.mato.plugin.base.command.SubCommand;
import me.mato.plugin.base.util.Permission;

public class ExampleSubCommand implements SubCommand {
    @Override
    public String name() {
        return "sub";
    }

    @Override
    public Permission permission() {
        return Permission.EXEMPLO;
    }

    @Override
    public void execute(CommandContext context) {
        context.sender().sendMessage("Você executou o subcomando de exemplo!");

        if (context.args().length > 0) {
            context.sender().sendMessage("Argumentos fornecidos: " + String.join(", ", context.args()));
        } else {
            context.sender().sendMessage("Nenhum argumento fornecido.");
        }
    }
}
