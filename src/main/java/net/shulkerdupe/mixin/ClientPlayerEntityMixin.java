package net.shulkerdupe.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.shulkerdupe.MainClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class ClientPlayerEntityMixin {
    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo ci) {
        MainClient.tick();
    }
}
