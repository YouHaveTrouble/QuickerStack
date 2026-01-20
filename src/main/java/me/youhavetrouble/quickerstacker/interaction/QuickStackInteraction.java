package me.youhavetrouble.quickerstacker.interaction;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;

public class QuickStackInteraction extends SimpleInteraction {

    public static final BuilderCodec<QuickStackInteraction> CODEC = BuilderCodec
            .builder(QuickStackInteraction.class, QuickStackInteraction::new)
            .build();

}
