package me.omrih.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.Unbreakable;

public class WeaponCommand extends Command {
    public WeaponCommand() {
        super("weapon", "sword");

        setDefaultExecutor((sender, context) -> {
            if (sender instanceof Player player) {

                // Create an itemstack
                var swordName = Component.text("Magic Sword").color(NamedTextColor.DARK_AQUA).decorate(TextDecoration.ITALIC);
                var swordLore = Component.text("A magical sword...").color(NamedTextColor.GOLD);
                ItemStack sword = ItemStack.builder(Material.NETHERITE_SWORD)
                        .glowing()
                        .customName(swordName)
                        .lore(swordLore)
                        .set(ItemComponent.UNBREAKABLE, new Unbreakable(false))
                        .build();

                player.getInventory().addItemStack(sword);
                player.sendMessage("A weapon has been added to your inventory");
            } else {
                sender.sendMessage("Command must be executed by player!");
            }
        });
    }
}
