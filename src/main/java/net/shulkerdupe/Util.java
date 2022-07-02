package net.shulkerdupe;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CClickWindowPacket;
import net.minecraft.util.text.ITextComponent;

public class Util {
    public static final Minecraft CLIENT = Minecraft.getInstance();

    public static void log(String msg) {
        CLIENT.player.sendStatusMessage(ITextComponent.getTextComponentOrEmpty("[Shulker Dupe]: " + msg), false);
    }

    public static void quickMoveAllItems() {
        for (int i = 0; i < 27; i++) {
            quickMoveItem(i);
        }
    }

    public static void quickMoveItem(int slot) {
        if (CLIENT.player.openContainer instanceof ShulkerBoxContainer) {
            ShulkerBoxContainer screenHandler = (ShulkerBoxContainer) CLIENT.player.openContainer;
            Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
            stack.put(slot, screenHandler.getSlot(slot).getStack());

            CLIENT.getConnection().sendPacket(new
                    CClickWindowPacket(screenHandler.windowId
                    , slot
                    , 0
                    , ClickType.QUICK_MOVE
                    , screenHandler.getSlot(0).getStack()
                    ,CLIENT.player.openContainer.getNextTransactionID(CLIENT.player.inventory)));
        }
    }
}
