package me.omrih.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class TitleCommand extends Command {

    public TitleCommand() {
        super("title");

        var mainTitle = Component.text("Hello, World!", NamedTextColor.GOLD);
        var subtitle = Component.text("Subtitle", NamedTextColor.GRAY)
                .decorate(TextDecoration.ITALIC);
        Title title = Title.title(mainTitle, subtitle);

        setDefaultExecutor((sender, context) -> sender.showTitle(title));

        var titleTextArg = ArgumentType.String("titleText");
        var subtitleTextArg = ArgumentType.String("subtitleText");

        addSyntax((sender, context) -> {
            String titleString = context.get(titleTextArg);
            String subtitleString = context.get(subtitleTextArg);

            Title newTitle = Title.title(Component.text(titleString), Component.text(subtitleString));
            sender.showTitle(newTitle);
        }, titleTextArg, subtitleTextArg);
    }
}