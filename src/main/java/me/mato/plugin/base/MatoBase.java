package me.mato.plugin.base;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.mato.plugin.base.core.service.BootstrapService;
import me.mato.plugin.base.di.PluginModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class MatoBase extends JavaPlugin {
    private Injector injector;

    private BootstrapService bootstrap;

    @Override
    public void onEnable() {
        this.injector = Guice.createInjector(new PluginModule(this));
        this.bootstrap = injector.getInstance(BootstrapService.class);

        getLogger().info("Iniciando MatoPluginBase...");

        bootstrap.initialize();

        getLogger().info("MatoPluginBase carregado com sucesso!");
    }

    @Override
    public void onDisable() {
        bootstrap.shutdown();
        getLogger().info("MatoPluginBase desligado.");
    }

    public Injector injector() {
        return injector;
    }
}
