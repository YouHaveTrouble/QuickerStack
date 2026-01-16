package me.youhavetrouble.quickerstacker;


import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import me.youhavetrouble.quickerstacker.interaction.ChestInteraction;
import me.youhavetrouble.quickerstacker.listener.ChestInteractListener;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class QuickerStacker extends JavaPlugin {

    public QuickerStacker(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    public void setup() {
        this.getEventRegistry().registerGlobal(UseBlockEvent.class, ChestInteractListener::onChestInteract);
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ChestInteractListener::playerJoin);

        this.getCodecRegistry(Interaction.CODEC).register("YouHaveTrouble_QuickerStacker_QuickStackToChest", ChestInteraction.class, ChestInteraction.CODEC);
    }

}
