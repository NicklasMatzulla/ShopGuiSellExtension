# ShopGuiSellExtension
An extension for the ShopGuiPlus plugin that allows you to create commands to sell items from specific categories directly.

## Requirements
* Java version 17
* Paper 1.20+ (Tested on 1.20.2).
* Depends on the plugins ShopGui+ and Vault

## Usage
When the plugin is started, a new folder called ShopGuiSellExtension is created under plugins. A configuration for commands and messages can be found in this folder.

### Commands configuration
In the command configuration, you can register commands with which the player can sell all items from his inventory for a ShopGui category. To create a new command, simply add the following code below the ``commands`` section and customize your details.
````yaml
commands:
  [...]
  command_name:
    aliases:
      - "alias1"
      - "alias2"
      - "alias3"
    description: "Command description."
    permission: "shopguisellextension.commands.name"
    categories:
      - "first shopgui shop id"
      - "second shopgui shop id"
````

### Messages configuration
The message configuration supports the [mini message](https://docs.advntr.dev/minimessage/format.html) format. You can view all messages directly in the [file](src/main/resources/messages.yml).