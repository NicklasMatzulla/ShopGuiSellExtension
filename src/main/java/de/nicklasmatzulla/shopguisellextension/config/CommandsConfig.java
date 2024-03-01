/*
 * Copyright (c) 2024 Nicklas Matzulla.
 */

package de.nicklasmatzulla.shopguisellextension.config;

import de.nicklasmatzulla.shopguisellextension.command.SellCommand;
import de.nicklasmatzulla.shopguisellextension.util.BaseConfig;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandsConfig extends BaseConfig {

    private final MessagesConfig messagesConfig;
    private final Economy economy;

    @Getter
    private List<Command> commands;

    public CommandsConfig(final @NotNull MessagesConfig messagesConfig, final @NotNull Economy economy) {
        super(new File("plugins/ShopGuiSellExtension/commands.yml"), "commands.yml");
        this.economy = economy;
        this.messagesConfig = messagesConfig;
        init();
    }

    private void init() {
        this.commands = loadCommands();
    }

    @SuppressWarnings({"CallToPrintStackTrace", "DataFlowIssue"})
    private List<Command> loadCommands() {
        final List<Command> commands = new ArrayList<>();
        if (config.contains("commands")) {
            final ConfigurationSection commandsSection = config.getConfigurationSection("commands");
            for (final String commandName : commandsSection.getKeys(false)) {
                try {
                    final List<String> aliases = commandsSection.getStringList(commandName + ".aliases");
                    final String description = commandsSection.getString(commandName + ".description", "");
                    String permission = commandsSection.getString(commandName + ".permission", "");
                    if (permission.isEmpty()) {
                        permission = null;
                    }
                    final List<String> categories = commandsSection.getStringList(commandName + ".categories");
                    final CommandOptions commandOptions = new CommandOptions(commandName, aliases, description, permission, categories);
                    final Command command = new SellCommand(commandOptions, this.messagesConfig, this.economy);
                    commands.add(command);
                } catch (final @NotNull Exception e) {
                    new IllegalArgumentException("The configuration of the command is invalid.", e).printStackTrace();
                }
            }
        }
        return commands;
    }

    public void reload() {
        loadConfig();
        init();
    }

}
