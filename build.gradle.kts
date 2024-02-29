/*
 * Copyright (c) 2024 Nicklas Matzulla.
 */

@file:Suppress("SpellCheckingInspection")

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.3"
}

group = "de.nicklasmatzulla"
version = "1.0.0"
description = "An extension for the ShopGuiPlus plugin that allows you to create commands to sell items from specific categories directly."

val lombokVersion = "1.18.30"
val paperVersion = "1.20.2"
val paperApiVersion = "1.20.2-R0.1-SNAPSHOT"
val apiVersion = "1.20"
val shopGuiApiVersion = "3.0.0"
val vaultApiVersion = "1.7.1"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    paperweight.paperDevBundle(paperApiVersion)
    compileOnly("com.github.brcdev-minecraft:shopgui-api:$shopGuiApiVersion")
    compileOnly("com.github.MilkBowl:VaultAPI:$vaultApiVersion")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        val props = mapOf(
                "name" to project.name,
                "version" to project.version,
                "description" to project.description,
                "apiVersion" to apiVersion
        )
        inputs.properties(props)
        filesMatching("*.yml") {
            expand(props)
        }
    }
    runServer {
        minecraftVersion(paperVersion)
    }
}