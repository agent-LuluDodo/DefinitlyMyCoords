package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.gui.GuiPlacementConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = GuiPlacementConfiguration.TextFieldListener.class, remap = false)
public class GuiPlacementConfigurationTextFieldListenerMixin {
    @ModifyArg(
            method = "onTextChange(Lfi/dy/masa/malilib/gui/GuiTextFieldInteger;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/BlockPos;<init>(III)V",
                    ordinal = 0
            ),
            index = 0
    )
    private int definitelymycoords$setX(int x) {
        return (int) DMCApi.getUndoOffsetX(x);
    }

    @ModifyArg(
            method = "onTextChange(Lfi/dy/masa/malilib/gui/GuiTextFieldInteger;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/BlockPos;<init>(III)V",
                    ordinal = 1
            ),
            index = 1
    )
    private int definitelymycoords$setY(int y) {
        return (int) DMCApi.getUndoOffsetY(y);
    }

    @ModifyArg(
            method = "onTextChange(Lfi/dy/masa/malilib/gui/GuiTextFieldInteger;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/BlockPos;<init>(III)V",
                    ordinal = 2
            ),
            index = 2
    )
    private int definitelymycoords$setZ(int z) {
        return (int) DMCApi.getUndoOffsetZ(z);
    }
}
