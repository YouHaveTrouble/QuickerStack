package me.youhavetrouble.quickerstacker;


import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import me.youhavetrouble.quickerstacker.interaction.ChestInteraction;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class QuickerStacker extends JavaPlugin {

    public QuickerStacker(@NonNullDecl JavaPluginInit init) {
        super(init);

        this.getCodecRegistry(Interaction.CODEC).register("YouHaveTrouble_QuickerStacker_QuickStackToChest", ChestInteraction.class, ChestInteraction.CODEC);

    }

}
