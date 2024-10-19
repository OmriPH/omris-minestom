package me.omrih.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

public class GamemodeCommand extends Command {

    public GamemodeCommand() {
        super("gamemode");

        setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /gamemode <survival | creative | adventure | spectator>"));

        var gamemodeArg = ArgumentType.String("gamemode");

        //command: /gamemode <survival | creative | adventure | spectator>
        addSyntax((sender, context) -> {
            String newGamemode = context.get(gamemodeArg).toUpperCase();

            if (sender instanceof Player player) {
                player.setGameMode(GameMode.valueOf(newGamemode));
                sender.sendMessage("Gamemode set to " + newGamemode.toLowerCase());
            }
            else {
                sender.sendMessage("Command must be executed by player!");
            }
        }, gamemodeArg);
    }
}
