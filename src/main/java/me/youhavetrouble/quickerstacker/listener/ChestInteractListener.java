package me.youhavetrouble.quickerstacker.listener;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.protocol.BlockPosition;
import com.hypixel.hytale.protocol.InteractionSyncData;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.NotificationUtil;

public class ChestInteractListener {

    public static void onChestInteract(UseBlockEvent event) {
        Ref<EntityStore> ref = event.getContext().getEntity();
        Player player = ref.getStore().getComponent(ref, Player.getComponentType());
        if (player == null) return;
        NotificationUtil.sendNotification(player.getPlayerRef().getPacketHandler(), "Interaction happened!  "+ event.getInteractionType().name());
        player.sendMessage(Message.raw("Interaction: " + event.getInteractionType().name()));
        BlockPosition targetBlockPosition = new BlockPosition();
        targetBlockPosition.x = event.getTargetBlock().x;
        targetBlockPosition.y = event.getTargetBlock().y;
        targetBlockPosition.z = event.getTargetBlock().z;
        World world = player.getWorld();
        if (world == null) return;
        WorldChunk chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(targetBlockPosition.x, targetBlockPosition.z));
        if (chunk == null) return;
        var blockState = chunk.getState(targetBlockPosition.x, targetBlockPosition.y, targetBlockPosition.z);
        if (!(blockState instanceof ItemContainerState containerState)) return;

        Inventory playerInventory = player.getInventory();
        if (playerInventory == null) return;
        playerInventory.getCombinedHotbarFirst().quickStackTo(containerState.getItemContainer());
        player.sendMessage(Message.raw("Quick stacked items to chest."));
    }

    public static void playerJoin(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        NotificationUtil.sendNotification(player.getPlayerConnection(), "QuickerStacker Loaded!");
        player.sendMessage(Message.raw("QuickerStacker Loaded!"));
    }

}
