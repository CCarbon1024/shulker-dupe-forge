package net.shulkerdupe;

import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkEvent;

import static net.shulkerdupe.SharedVariables.*;
import static net.shulkerdupe.Util.CLIENT;
import static net.shulkerdupe.Util.log;

public class MainClient {

    public static boolean fra = true;
    public static int thex = 0;
    public static int they = 0;

    public static void setFra(boolean fra) {
        MainClient.fra = fra;
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent.ClientTickEvent e) {

        if (CLIENT.player != null) {
            boolean b1 = (CLIENT.player.containerMenu instanceof ShulkerBoxMenu);

            if (shouldDupe || shouldDupeAll) {
                HitResult hit = CLIENT.hitResult;
                if (hit instanceof BlockHitResult) {
                    BlockHitResult blockHit = (BlockHitResult) hit;

                    if (CLIENT.level.getBlockState(blockHit.getBlockPos()).getBlock() instanceof ShulkerBoxBlock && b1) {
                        CLIENT.gameMode.continueDestroyBlock(blockHit.getBlockPos(), Direction.DOWN);
                    } else {
                        log("You need to have a shulker box screen open and look at a shulker box.");
                        CLIENT.player.clientSideCloseContainer();
                        shouldDupe = false;
                        shouldDupeAll = false;
                    }
                }
            }
            if (b1) {

            } else {
                setFra(true);
            }
        }

    }

}
