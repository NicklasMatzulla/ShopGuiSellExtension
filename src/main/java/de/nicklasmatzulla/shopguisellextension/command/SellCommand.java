/*
 * Copyright (c) 2024 Nicklas Matzulla.
 */

package de.nicklasmatzulla.shopguisellextension.command;

import de.nicklasmatzulla.shopguisellextension.config.CommandOptions;
import de.nicklasmatzulla.shopguisellextension.config.MessagesConfig;
import lombok.Getter;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.brcdev.shopgui.shop.Shop;
import net.brcdev.shopgui.shop.item.ShopItem;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
public class SellCommand extends Command {

    private final CommandOptions commandOptions;
    private final MessagesConfig messagesConfig;
    private final Economy economy;

    public SellCommand(final @NotNull CommandOptions commandOptions, final @NotNull MessagesConfig messagesConfig, final @NotNull Economy economy) {
        super(commandOptions.name());
        setDescription(commandOptions.description());
        setAliases(commandOptions.aliases());
        this.commandOptions = commandOptions;
        this.messagesConfig = messagesConfig;
        this.economy = economy;
    }

    @Override
    public boolean execute(final @NotNull CommandSender commandSender, final @NotNull String command, final @NotNull String[] args) {
        if (!(commandSender instanceof final Player player)) {
            return true;
        }
        int soldItems = 0;
        double receivedMoney = 0;
        for (final String category : this.commandOptions.categories()) {
            final Shop shop = ShopGuiPlusApi.getShop(category);
            for (final ShopItem shopItem : shop.getShopItems()) {
                final ItemStack shopItemStack = shopItem.getItem();
                int itemsAmount = 0;
                for (final ItemStack itemStack : player.getInventory().getContents()) {
                    if (itemStack != null && !itemStack.hasItemMeta() && itemStack.getType().equals(shopItemStack.getType())) {
                        player.getInventory().remove(itemStack);
                        itemsAmount += itemStack.getAmount();
                    }
                }
                final double receivableMoney = shopItem.getSellPriceForAmount(player, itemsAmount);
                this.economy.depositPlayer(player, receivableMoney);
                soldItems += itemsAmount;
                receivedMoney += receivableMoney;
            }
        }
        if (soldItems == 0) {
            final Component notingToSellComponent = this.messagesConfig.getNotingToSellComponent();
            player.sendMessage(notingToSellComponent);
            return true;
        }
        final DecimalFormat decimalFormat = new DecimalFormat(this.messagesConfig.getMoneyFormat());
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        final String formattedReceivedMoney = decimalFormat.format(receivedMoney);
        final Component soldComponent = this.messagesConfig.getSoldComponent(soldItems, formattedReceivedMoney);
        player.sendMessage(soldComponent);
        return true;
    }

}
