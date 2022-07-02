package net.shulkerdupe.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.shulkerdupe.MainClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.shulkerdupe.SharedVariables.shouldDupe;
import static net.shulkerdupe.SharedVariables.shouldDupeAll;

@Mixin(ShulkerBoxScreen.class)
public class ShulkerBoxScreenMixin extends Screen {
    public ShulkerBoxScreenMixin(ITextComponent title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "drawGuiContainerBackgroundLayer(Lcom/mojang/blaze3d/matrix/MatrixStack;FII)V")
    public void renderScreen(MatrixStack matrixStack, float partialTicks, int x, int y, CallbackInfo ci) {
        if (isFra()) {
            setFra(false);
            MainClient.thex = this.width;
            MainClient.they = this.height;
            this.addButton(new Button(this.width/2-90, this.height/2+35-145, 50, 20, ITextComponent.getTextComponentOrEmpty("Dupe"), (button) -> {
                if (shouldDupeAll) shouldDupeAll = false;
                shouldDupe = true;
            }));
            this.addButton(new Button(this.width/2+40, this.height/2+35-145, 50, 20, ITextComponent.getTextComponentOrEmpty("Dupe All"), (button) -> {
                if (shouldDupe) shouldDupe = false;
                shouldDupeAll = true;
            }));
        }

        if (this.width != MainClient.thex || this.height != MainClient.they) {
            setFra(true);
        }

    }

    public boolean isFra() {
        return MainClient.fra;
    }

    public void setFra(boolean fra) {
        MainClient.fra = fra;
    }
}
