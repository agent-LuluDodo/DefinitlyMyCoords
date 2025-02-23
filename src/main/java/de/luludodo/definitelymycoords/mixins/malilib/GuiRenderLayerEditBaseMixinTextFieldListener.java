package de.luludodo.definitelymycoords.mixins.malilib;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.malilib.util.LayerRange;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "fi.dy.masa.malilib.gui.GuiRenderLayerEditBase$TextFieldListener", remap = false)
public class GuiRenderLayerEditBaseMixinTextFieldListener {
    @Shadow @Final protected LayerRange layerRange;

    @ModifyArg(
            method = "onTextChange(Lfi/dy/masa/malilib/gui/GuiTextFieldGeneric;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerAbove(I)V"
            )
    )
    private int definitelymycoords$set$1(int value) {
        return definitelymycoords$set(value);
    }

    @ModifyArg(
            method = "onTextChange(Lfi/dy/masa/malilib/gui/GuiTextFieldGeneric;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerBelow(I)V"
            )
    )
    private int definitelymycoords$set$2(int value) {
        return definitelymycoords$set(value);
    }

    @ModifyArg(
            method = "onTextChange(Lfi/dy/masa/malilib/gui/GuiTextFieldGeneric;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerSingle(I)V"
            )
    )
    private int definitelymycoords$set$3(int value) {
        return definitelymycoords$set(value);
    }

    @ModifyArg(
            method = "onTextChange(Lfi/dy/masa/malilib/gui/GuiTextFieldGeneric;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerRangeMax(I)Z"
            )
    )
    private int definitelymycoords$set$4(int value) {
        return definitelymycoords$set(value);
    }

    @ModifyArg(
            method = "onTextChange(Lfi/dy/masa/malilib/gui/GuiTextFieldGeneric;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerRangeMin(I)Z"
            )
    )
    private int definitelymycoords$set$5(int value) {
        return definitelymycoords$set(value);
    }

    @Unique
    private int definitelymycoords$set(int value) {
        return (int) switch (layerRange.getAxis()) {
            case X -> DMCApi.getUndoOffsetBlockX(value);
            case Y -> DMCApi.getUndoOffsetBlockY(value);
            case Z -> DMCApi.getUndoOffsetBlockZ(value);
        };
    }
}
