package de.luludodo.definitelymycoords.mixins.malilib;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.malilib.gui.GuiRenderLayerEditBase;
import fi.dy.masa.malilib.util.LayerRange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiRenderLayerEditBase.class, remap = false)
public class GuiRenderLayerEditBaseMixin {
    @Unique LayerRange definitelymycoords$layerRange;

    @Inject(
            method = "updateTextFieldValues",
            at = @At(
                    "HEAD"
            )
    )
    private void definitelymycoords$updateLayerRange(LayerRange layerRange, CallbackInfo ci) {
        definitelymycoords$layerRange = layerRange;
    }

    @ModifyArg(
            method = "updateTextFieldValues",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;valueOf(I)Ljava/lang/String;"
            )
    )
    private int definitelymycoords$set(int value) {
        return (int) switch (definitelymycoords$layerRange.getAxis()) {
            case X -> DMCApi.getOffsetBlockX(value);
            case Y -> DMCApi.getOffsetBlockY(value);
            case Z -> DMCApi.getOffsetBlockZ(value);
        };
    }
}
