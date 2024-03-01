/*
 * Copyright (c) 2024 Nicklas Matzulla.
 */

package de.nicklasmatzulla.shopguisellextension.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record CommandOptions(
        @NotNull String name,
        @NotNull List<String> aliases,
        @NotNull String description,
        @Nullable String permission,
        @NotNull List<String> categories) {
}
