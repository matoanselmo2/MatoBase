package me.mato.plugin.base;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.mato.plugin.base.data.database.engine.AbstractDatabaseEngine;
import me.mato.plugin.base.di.PluginModule;
import me.mato.plugin.base.manager.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MatoBase extends JavaPlugin {
    private Injector injector;
    private AbstractDatabaseEngine databaseEngine;

    @Override
    public void onEnable() {
        this.injector = Guice.createInjector(new PluginModule(this));
        this.databaseEngine = this.injector.getInstance(AbstractDatabaseEngine.class);

        // Aqui você pode adicionar qualquer lógica de inicialização necessária
        this.injector.getInstance(CommandManager.class).registerCommands();

        getLogger().info("MatoPluginBase carregado com sucesso!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MatoPluginBase desligado.");

        // Aqui você pode adicionar qualquer lógica de limpeza necessária
        if (databaseEngine != null) {
            try {
                databaseEngine.disconnect();
                getLogger().info("Conexão com a database encerrada com sucesso.");
            } catch (Exception e) {
                getLogger().severe("Erro ao desconectar da database: " + e.getMessage());
            }
        }
    }

    public Injector getInjector() {
        return injector;
    }
}
