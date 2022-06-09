package net.shulkerdupe.mixin;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.shulkerdupe.SharedVariables.shouldDupe;
import static net.shulkerdupe.SharedVariables.shouldDupeAll;
import static net.shulkerdupe.Util.quickMoveAllItems;
import static net.shulkerdupe.Util.quickMoveItem;

@Mixin(Connection.class)
public class ClientConnectionMixin {
    @Inject(at = @At("TAIL"),
            method = "send(Lnet/minecraft/network/protocol/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V")
    public void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> p_129516_, CallbackInfo ci) {
        if (packet instanceof ServerboundPlayerActionPacket) {
            if (((ServerboundPlayerActionPacket) packet).getAction() == ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK) {
                if (shouldDupe) {
                    quickMoveItem(0);
                    shouldDupe = false;
                } else if (shouldDupeAll) {
                    quickMoveAllItems();
                    shouldDupeAll = false;
                }
            }
        }
    }
}
