package me.youhavetrouble.quickerstacker.interaction;


import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.protocol.BlockPosition;
import com.hypixel.hytale.protocol.InteractionSyncData;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.NotificationUtil;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ChestInteraction extends SimpleBlockInteraction {

    public static final BuilderCodec<ChestInteraction> CODEC = BuilderCodec
            .builder(ChestInteraction.class, ChestInteraction::new)
            .build();

    @Override
    protected void interactWithBlock(
            @NonNullDecl World world,
            @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
            @NonNullDecl InteractionType interactionType,
            @NonNullDecl InteractionContext interactionContext,
            @NullableDecl ItemStack itemStack,
            @NonNullDecl Vector3i vector3i,
            @NonNullDecl CooldownHandler cooldownHandler
    ) {
        Ref<EntityStore> ref = interactionContext.getEntity();
        Player player = ref.getStore().getComponent(ref, Player.getComponentType());
        if (player == null) return;
        NotificationUtil.sendNotification(player.getPlayerRef().getPacketHandler(), "Interaction happened!  "+ interactionType.name());
        player.sendMessage(Message.raw("Interaction: " + interactionType.name()));
        InteractionSyncData clientState = interactionContext.getClientState();
        if (clientState == null) return;
        BlockPosition targetBlockPosition = clientState.blockPosition;
        if (targetBlockPosition == null) return;
        WorldChunk chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(targetBlockPosition.x, targetBlockPosition.z));
        if (chunk == null) return;
        var blockState = chunk.getState(targetBlockPosition.x, targetBlockPosition.y, targetBlockPosition.z);
        if (!(blockState instanceof ItemContainerState containerState)) return;

        Inventory playerInventory = player.getInventory();
        if (playerInventory == null) return;
        playerInventory.getCombinedHotbarFirst().quickStackTo(containerState.getItemContainer());
        player.sendMessage(Message.raw("Quick stacked items to chest."));
    }

    @Override
    protected void simulateInteractWithBlock(@NonNullDecl InteractionType interactionType, @NonNullDecl InteractionContext interactionContext, @NullableDecl ItemStack itemStack, @NonNullDecl World world, @NonNullDecl Vector3i vector3i) {

    }
}
