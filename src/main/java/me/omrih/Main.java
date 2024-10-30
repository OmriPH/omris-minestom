package me.omrih;

import io.github.togar2.pvp.MinestomPvP;
import io.github.togar2.pvp.feature.CombatFeatureSet;
import io.github.togar2.pvp.feature.CombatFeatures;
import me.omrih.commands.*;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.time.TimeUnit;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        // Initialize the server
        MinecraftServer server = MinecraftServer.init();

        // Create Instance
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // generate world
        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        // add lighting
        instanceContainer.setChunkSupplier(LightingChunk::new);

        //init pvp
        MinestomPvP.init();

        CombatFeatureSet modernVanilla = CombatFeatures.modernVanilla();
        MinecraftServer.getGlobalEventHandler().addChild(modernVanilla.createNode());

        // Add event handler to handle player spawning
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        // Nodes
        var allNode = EventNode.all("all");
        allNode.addListener(PickupItemEvent.class, event -> {
            var itemStack = event.getItemStack();
            if (event.getLivingEntity() instanceof Player player) {
                player.getInventory().addItemStack(itemStack);
            }
        });

        var playerNode = EventNode.type("players", EventFilter.PLAYER);
        playerNode.addListener(PlayerBlockBreakEvent.class, event -> {
            var material = event.getBlock().registry().material();
            if (material != null) {
                var itemStack = ItemStack.of(material);
                ItemEntity itemEntity = new ItemEntity(itemStack);
                itemEntity.setInstance(event.getInstance(), event.getBlockPosition().add(0.5,10,0.5));
                itemEntity.setPickupDelay(Duration.ofMillis(500));
            }
        });

        playerNode.addListener(ItemDropEvent.class, event -> {
            ItemEntity itemEntity = new ItemEntity(event.getItemStack());
            itemEntity.setInstance(event.getInstance(), event.getPlayer().getPosition());
            itemEntity.setVelocity(event.getPlayer().getPosition().add(0,1,0).direction().mul(8));
            itemEntity.setPickupDelay(Duration.ofMillis(500));
        });
        allNode.addChild(playerNode);

        globalEventHandler.addChild(allNode);

        MinecraftServer.getCommandManager().register(new TestCommand());
        MinecraftServer.getCommandManager().register(new SetHealthCommand());
        MinecraftServer.getCommandManager().register(new KillCommand());
        MinecraftServer.getCommandManager().register(new GamemodeCommand());
        MinecraftServer.getCommandManager().register(new TitleCommand());
        MinecraftServer.getCommandManager().register(new BroadcastCommand());
        MinecraftServer.getCommandManager().register(new WeaponCommand());

        var scheduler = MinecraftServer.getSchedulerManager();
        scheduler.buildShutdownTask(() -> {
            System.out.println("The server is shutting down");
            instanceManager.getInstances().forEach(Instance::saveChunksToStorage);
        });

        // Repeating task to continually save the world
        scheduler.buildTask(() -> {
            System.out.println("Saving all instances...");
            instanceManager.getInstances().forEach(Instance::saveChunksToStorage);
            System.out.println("Saved instances");
        })
                .repeat(30, TimeUnit.SECOND)
                .delay(1, TimeUnit.MINUTE)
                .schedule();

        MojangAuth.init();
        server.start("0.0.0.0", 62309);
    }
}