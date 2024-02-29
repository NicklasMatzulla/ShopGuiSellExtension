/*
 * Copyright (c) 2024 Nicklas Matzulla.
 */

package de.nicklasmatzulla.shopguisellextension.config;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CommandOptions(
        @NotNull String name,
        @NotNull List<String> aliases,
        @NotNull String description,
        @NotNull List<String> categories) {
}
