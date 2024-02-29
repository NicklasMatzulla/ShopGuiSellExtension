/*
 * Copyright (c) 2024 Nicklas Matzulla.
 */

package de.nicklasmatzulla.shopguisellextension;

import de.nicklasmatzulla.shopguisellextension.config.CommandsConfig;
import de.nicklasmatzulla.shopguisellextension.config.MessagesConfig;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopGuiSellExtension extends JavaPlugin implements Listener {

    @Getter
    private Economy economy;

    private CommandsConfig commandsConfig;

    @Override
    public void onEnable() {
        loadEconomy();
        loadConfigs();
        registerCommands();
    }

    private void loadEconomy() {
        final RegisteredServiceProvider <Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (serviceProvider != null) {
            this.economy = serviceProvider.getProvider();
        } else {
            getLogger().warning("No currency was found!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void loadConfigs() {
        final MessagesConfig messagesConfig = new MessagesConfig();
        this.commandsConfig = new CommandsConfig(messagesConfig, this.economy);
    }

    private void registerCommands() {
        final CommandMap commandMap = Bukkit.getCommandMap();
        this.commandsConfig.getCommands().forEach(command -> commandMap.register("", command));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
