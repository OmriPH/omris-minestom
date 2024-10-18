package me.omrih.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class TestCommand extends Command {
    public TestCommand() {
        super("test");

        setDefaultExecutor((sender, context) -> sender.sendMessage("Test Command Ran!"));

        //test <amount>
        var testAmountArg = ArgumentType.Integer("testAmount");
        var testSay = ArgumentType.String("testSay");
        addSyntax((sender, context) -> {
            // get the argument from the context
            int testAmount = context.get(testAmountArg);
            for (int i = 0; i < testAmount; i++) {
                sender.sendMessage("Test Command Ran! " + (i + 1));
            }
        }, testAmountArg);
        addSyntax((sender, context) -> {
            String Say = context.get(testSay);
            sender.sendMessage("You typed: " + Say);
        }, testSay);
    }
}