package me.mato.plugin.base.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.CustomInventoryImpl;
import me.mato.plugin.base.MatoBase;
import me.mato.plugin.base.api.permission.PermissionNode;
import me.mato.plugin.base.command.BaseCommand;
import me.mato.plugin.base.command.impl.ExampleCommand;
import me.mato.plugin.base.command.impl.MainCommand;
import me.mato.plugin.base.api.dao.PlayerDAO;
import me.mato.plugin.base.api.database.DatabaseFactory;
import me.mato.plugin.base.api.database.config.DatabaseConfigLoader;
import me.mato.plugin.base.api.database.config.IDatabaseConfig;
import me.mato.plugin.base.api.database.engine.AbstractDatabaseEngine;
import me.mato.plugin.base.core.gui.ExampleMenu;
import me.mato.plugin.base.core.manager.CommandManager;
import me.mato.plugin.base.core.manager.GuiManager;
import me.mato.plugin.base.core.permission.ExamplePermissionGroup;
import me.mato.plugin.base.core.registry.PluginPermissionWrapper;

import java.util.Set;

public class PluginModule extends AbstractModule {
    private final MatoBase plugin;

    public PluginModule(MatoBase plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(MatoBase.class).toInstance(plugin);

        Multibinder<PermissionNode[]> permissionBinder = Multibinder.newSetBinder(binder(), PermissionNode[].class);
        permissionBinder.addBinding().toInstance(ExamplePermissionGroup.values());

        Multibinder<CustomInventoryImpl> guiBinder = Multibinder.newSetBinder(binder(), CustomInventoryImpl.class);
        guiBinder.addBinding().to(ExampleMenu.class);

        Multibinder<BaseCommand> commandBinder = Multibinder.newSetBinder(binder(), BaseCommand.class);
        commandBinder.addBinding().to(MainCommand.class);
        commandBinder.addBinding().to(ExampleCommand.class);
    }

    @Provides @Singleton
    public IDatabaseConfig provideDatabaseConfig() {
        return DatabaseConfigLoader.load(plugin);
    }

    @Provides @Singleton
    public AbstractDatabaseEngine provideDatabaseEngine(IDatabaseConfig config) {
        return DatabaseFactory.createDatabaseEngine(plugin, config);
    }

    @Provides @Singleton
    public PlayerDAO providePlayerDAO(AbstractDatabaseEngine engine) {
        return new PlayerDAO(engine);
    }

    @Provides @Singleton
    public GuiManager provideGuiManager(MatoBase plugin, Set<CustomInventoryImpl> guis) {
        return new GuiManager(guis);
    }

    @Provides @Singleton
    public CommandManager provideCommandManager(Set<BaseCommand> commands) {
        return new CommandManager(plugin, commands);
    }
}


