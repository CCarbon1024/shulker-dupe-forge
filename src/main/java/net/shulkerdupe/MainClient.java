package net.shulkerdupe;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

import static net.shulkerdupe.SharedVariables.shouldDupe;
import static net.shulkerdupe.SharedVariables.shouldDupeAll;
import static net.shulkerdupe.Util.CLIENT;
import static net.shulkerdupe.Util.log;

public class MainClient{

    public static boolean fra = true;
    public static int thex = 0;
    public static int they = 0;

    public static void setFra(boolean fra) {
        MainClient.fra = fra;
    }

    public static void tick() {

        boolean b1 = (CLIENT.player.openContainer instanceof ShulkerBoxContainer);

        if (shouldDupe || shouldDupeAll) {
            RayTraceResult hit = CLIENT.objectMouseOver;
            if (hit instanceof BlockRayTraceResult) {
                BlockRayTraceResult blockHit = (BlockRayTraceResult) hit;
                if (CLIENT.world.getBlockState(blockHit.getPos()).getBlock() instanceof ShulkerBoxBlock && b1) {
                    CLIENT.playerController.onPlayerDamageBlock(blockHit.getPos(), Direction.DOWN);
                } else {
                    log("You need to have a shulker box screen open and look at a shulker box.");
                    CLIENT.player.closeScreen();
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
