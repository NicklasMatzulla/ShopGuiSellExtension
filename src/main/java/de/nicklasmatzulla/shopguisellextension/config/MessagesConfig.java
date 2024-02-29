/*
 * Copyright (c) 2024 Nicklas Matzulla.
 */

package de.nicklasmatzulla.shopguisellextension.config;

import de.nicklasmatzulla.shopguisellextension.util.BaseConfig;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Getter
public class MessagesConfig extends BaseConfig {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private final Component prefixComponent;
    private final Component onlyPlayerComponent;
    private final Component notingToSellComponent;
    private final String moneyFormat;

    @Getter(AccessLevel.NONE)
    private final TagResolver prefixPlaceholder;

    public MessagesConfig() {
        super(new File("plugins/ShopGuiSellExtension/messages.yml"), "messages.yml");
        this.prefixComponent = getComponent("general.prefix");
        this.prefixPlaceholder = Placeholder.component("prefix", this.prefixComponent);
        this.onlyPlayerComponent = getComponent("general.onlyPlayer", this.prefixPlaceholder);
        this.notingToSellComponent = getComponent("commands.sell.notingToSell", this.prefixPlaceholder);
        this.moneyFormat = this.config.getString("commands.sell.moneyFormat", "#,##0.00");
    }

    private @NotNull Component getComponent(final @NotNull String key, final @NotNull TagResolver... tagResolvers) {
        final String value = this.config.getString(key);
        return MINI_MESSAGE.deserialize(value != null ? value : "<red>" + key + "</red>", tagResolvers).colorIfAbsent(NamedTextColor.GRAY);
    }

    public @NotNull Component getSoldComponent(final int soldItems, final String receivedMoney) {
        final TagResolver soldItemsPlaceholder = Placeholder.unparsed("sold_items", String.valueOf(soldItems));
        final TagResolver receivedMoneyPlaceholder = Placeholder.unparsed("received_money", receivedMoney);
        return getComponent("commands.sell.sold", this.prefixPlaceholder, soldItemsPlaceholder, receivedMoneyPlaceholder);
    }

}
