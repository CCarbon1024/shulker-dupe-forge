package net.shulkerdupe;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.ItemStack;

public class Util {
    public static final Minecraft CLIENT = Minecraft.getInstance();

    public static void log(String msg) {
        CLIENT.player.displayClientMessage(Component.nullToEmpty("[Shulker Dupe]: " + msg), false);
    }
    public static void quickMoveAllItems() {
        for (int i = 0; i < 27; i++) {
            quickMoveItem(i);
        }
    }

    public static void quickMoveItem(int slot) {
        if (CLIENT.player.containerMenu instanceof ShulkerBoxMenu) {
            ShulkerBoxMenu screenHandler = (ShulkerBoxMenu) CLIENT.player.containerMenu;
            Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
            stack.put(slot, screenHandler.getSlot(slot).getItem());
            CLIENT.getConnection().send(new ServerboundContainerClickPacket(screenHandler.containerId, 0, slot, 0, ClickType.QUICK_MOVE, screenHandler.getSlot(0).getItem(), (Int2ObjectMap) stack));
        }
    }
}
