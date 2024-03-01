package de.nicklasmatzulla.shopguisellextension.command;

import de.nicklasmatzulla.shopguisellextension.config.CommandsConfig;
import de.nicklasmatzulla.shopguisellextension.config.MessagesConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShopGuiSellExtensionCommand extends Command {

    private final MessagesConfig messagesConfig;
    private final CommandsConfig commandsConfig;

    public ShopGuiSellExtensionCommand(final @NotNull MessagesConfig messagesConfig, final @NotNull CommandsConfig commandsConfig) {
        super("shopguisellextension");
        this.messagesConfig = messagesConfig;
        this.commandsConfig = commandsConfig;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission("shopguisellextension.commands.admin")) {
            final Component noPermissionComponent = this.messagesConfig.getNoPermissionComponent();
            sender.sendMessage(noPermissionComponent);
            return true;
        }
        if (args.length == 0 ||
                !args[0].equalsIgnoreCase("reload") ||
                !sender.hasPermission("shopguisellextension.commands.admin.reload")) {
            final Component usageComponent = this.messagesConfig.getAdminUsageComponent();
            sender.sendMessage(usageComponent);
            return true;
        }
        final CommandMap commandMap = Bukkit.getCommandMap();
        this.commandsConfig.getCommands().forEach(command -> commandMap.getKnownCommands().remove(command.getName()));
        this.messagesConfig.reload();
        this.commandsConfig.reload();
        this.commandsConfig.getCommands().forEach(command -> commandMap.register("", command));
        final Component reloadedComponent = this.messagesConfig.getAdminReloadComponent();
        sender.sendMessage(reloadedComponent);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length > 1 ||
                !sender.hasPermission("shopguisellextension.commands.admin") ||
                !sender.hasPermission("shopguisellextension.commands.admin.reload")) {
            return List.of();
        }
        return List.of("reload");
    }
}
