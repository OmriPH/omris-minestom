package me.omrih.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class SetHealthCommand extends Command {

    public SetHealthCommand() {
        super("sethealth", "health");

        setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /sethealth <amount>"));

        var healthAmountArg = ArgumentType.Float("healthAmount");

        //command: /sethealth <1-20>
        addSyntax((sender, context) -> {
            float newHealth = context.get(healthAmountArg);

            //make sure its 1 - 20
            if (newHealth < 1 || newHealth > 20) {
                sender.sendMessage("Health must be between 1 and 20");
                return;
            }

            if (sender instanceof Player player) {
                player.setHealth(newHealth);
                sender.sendMessage("Health set to " + newHealth);
            }
            else {
                sender.sendMessage("Command must be executed by player!");
            }
        }, healthAmountArg);
    }
}
