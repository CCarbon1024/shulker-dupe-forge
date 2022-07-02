package net.shulkerdupe.mixin;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.shulkerdupe.SharedVariables.shouldDupe;
import static net.shulkerdupe.SharedVariables.shouldDupeAll;
import static net.shulkerdupe.Util.quickMoveAllItems;
import static net.shulkerdupe.Util.quickMoveItem;

@Mixin(NetworkManager.class)
public class ClientConnectionMixin {
    @Inject(at = @At("TAIL"),
            method = "sendPacket(Lnet/minecraft/network/IPacket;Lio/netty/util/concurrent/GenericFutureListener;)V",
            cancellable = true)
    public void send(IPacket<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo ci) {
        if (packet instanceof CPlayerDiggingPacket) {
            if (((CPlayerDiggingPacket) packet).getAction() == CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK) {
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
