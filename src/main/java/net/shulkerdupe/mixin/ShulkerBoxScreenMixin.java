package net.shulkerdupe.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.shulkerdupe.MainClient;
import net.shulkerdupe.SharedVariables;
import net.shulkerdupe.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.shulkerdupe.SharedVariables.shouldDupe;
import static net.shulkerdupe.SharedVariables.shouldDupeAll;

@Mixin(ShulkerBoxScreen.class)
public class ShulkerBoxScreenMixin extends Screen {
    public ShulkerBoxScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V")
    public void renderScreen(PoseStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (isFra()) {
            setFra(false);

            if (SharedVariables.dupeTime > 0) {
                NonNullList<ItemStack> items = Minecraft.getInstance().player.containerMenu.getItems();
                boolean isBoxFull = true;
                for(int i = 0 ; i < 27 && isBoxFull ; i++) {
                    if(items.get(i).isEmpty()) {
                        isBoxFull = false;
                    }
                }
                for (int i = 0; i < 27; i++) {
                    for (int j = 27; j < 63; j++) {
                        if (items.get(i).getItem().equals(items.get(j).getItem())) {
                           /* if(isBoxFull) {
                                Util.throwItem(j);
                                continue;
                            }*/
                            Util.quickMoveItem(j);
                        }
                    }
                }
                SharedVariables.dupeTime--;
                shouldDupeAll = true;
            }

            MainClient.thex = this.width;
            MainClient.they = this.height;
            this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 2 - 110, 50, 20, Component.nullToEmpty(I18n.get("shulkerdupe.dupe")), (button) -> {
                if (shouldDupeAll) shouldDupeAll = false;
                shouldDupe = true;
            }));
            this.addRenderableWidget(new Button(this.width / 2 + 50, this.height / 2 - 110, 50, 20, Component.nullToEmpty(I18n.get("shulkerdupe.dupeall")), (button) -> {
                if (shouldDupe) shouldDupe = false;
                shouldDupeAll = true;
            }));

            // Time and Run Button
            Button time = new Button(this.width / 2 - 20, this.height / 2 - 110, 40,
                    20, Component.nullToEmpty(SharedVariables.dupeTime + ""), (button) -> {
                if (SharedVariables.dupeTime > 0) {
                    SharedVariables.dupeTime--;
                    shouldDupeAll = true;
                }
            });
            this.addRenderableWidget(time);

            // - Button
            this.addRenderableWidget(new Button(this.width / 2 - 40, this.height / 2 - 110, 20, 20, Component.nullToEmpty("-"), (button) -> {
                if (hasShiftDown()) {
                    SharedVariables.dupeTime -= 7;
                } else if (hasControlDown()) {
                    SharedVariables.dupeTime -= 12;
                } else if (hasAltDown()) {
                    SharedVariables.dupeTime -= 27;
                } else {
                    SharedVariables.dupeTime -= 1;
                }
                time.setMessage(Component.nullToEmpty(SharedVariables.dupeTime + ""));
            }));

            // + Button
            this.addRenderableWidget(new Button(this.width / 2 + 20, this.height / 2 - 110, 20, 20, Component.nullToEmpty("+"), (button) -> {
                if (hasShiftDown()) {
                    SharedVariables.dupeTime += 6;
                } else if (hasControlDown()) {
                    SharedVariables.dupeTime += 11;
                } else if (hasAltDown()) {
                    SharedVariables.dupeTime += 27;
                } else {
                    SharedVariables.dupeTime += 1;
                }

                time.setMessage(Component.nullToEmpty(SharedVariables.dupeTime + ""));
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
