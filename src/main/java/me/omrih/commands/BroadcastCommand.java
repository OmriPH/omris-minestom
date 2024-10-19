package me.omrih.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class BroadcastCommand extends Command {
    public BroadcastCommand() {
        super("broadcast");

        //broadcast something something yay
        setDefaultExecutor((sender, context) -> sender.sendMessage("You need to specify a message to broadcast! /broadcast <message>"));

        var messageArg = ArgumentType.StringArray("message");

        addSyntax((sender, context) -> {
            String[] messageArray = context.get(messageArg);
            StringBuilder messageBuilder = new StringBuilder();
            for (String s : messageArray) {
                messageBuilder.append(s).append(" ");

            }
            String finalMessage = messageBuilder.toString();

            //send the message to all players
            Audiences.players().sendMessage(Component.text(finalMessage, NamedTextColor.RED, TextDecoration.BOLD));
        }, messageArg);
    }
}