package me.mato.plugin.base.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import me.mato.plugin.base.MatoBase;
import me.mato.plugin.base.command.BaseCommand;
import me.mato.plugin.base.command.impl.ExampleCommand;
import me.mato.plugin.base.command.impl.MainCommand;
import me.mato.plugin.base.data.dao.PlayerDAO;
import me.mato.plugin.base.data.database.DatabaseFactory;
import me.mato.plugin.base.data.database.config.DatabaseConfigLoader;
import me.mato.plugin.base.data.database.config.IDatabaseConfig;
import me.mato.plugin.base.data.database.engine.AbstractDatabaseEngine;

public class PluginModule extends AbstractModule {
    private final MatoBase plugin;

    public PluginModule(MatoBase plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(MatoBase.class).toInstance(plugin);

        IDatabaseConfig config = DatabaseConfigLoader.load(plugin);
        AbstractDatabaseEngine engine = DatabaseFactory.createDatabaseEngine(plugin, config);

        bind(AbstractDatabaseEngine.class).toInstance(engine);

        PlayerDAO playerDAO = new PlayerDAO(engine);
        playerDAO.createTable();

        bind(PlayerDAO.class).toInstance(playerDAO);

        Multibinder<BaseCommand> commandBinder = Multibinder.newSetBinder(binder(), BaseCommand.class);
        commandBinder.addBinding().to(MainCommand.class);
        commandBinder.addBinding().to(ExampleCommand.class);
    }
}
