package me.omrih.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class KillCommand extends Command {
    public KillCommand() {
        super("kill");

        setDefaultExecutor((sender, context) -> {
            if (sender instanceof Player player) {
                player.kill();
                sender.sendMessage("Successfully killed player " + player.getUsername());
            }
            else {
                sender.sendMessage("Command must be executed by player!");
            }
        });
    }
}
