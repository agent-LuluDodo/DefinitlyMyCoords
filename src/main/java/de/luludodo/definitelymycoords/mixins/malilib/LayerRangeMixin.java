package de.luludodo.definitelymycoords.mixins.malilib;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.malilib.util.LayerRange;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LayerRange.class, remap = false)
public class LayerRangeMixin {
    @Shadow protected int layerBelow;

    @Shadow protected Direction.Axis axis;

    @Shadow protected int layerSingle;

    @Shadow protected int layerAbove;

    @Inject(
            method = "moveLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerBelow(I)V",
                    shift = At.Shift.AFTER
            )
    )
    private void definitelymycoords$offsetLayerBelow(int amount, CallbackInfoReturnable<Boolean> cir) {
        layerBelow = definitelymycoords$offset(layerBelow);
    }

    @Inject(
            method = "moveLayer",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerBelow(I)V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/InfoUtils;printActionbarMessage(Ljava/lang/String;[Ljava/lang/Object;)V",
                    ordinal = 0
            )
    )
    private void definitelymycoords$undoOffsetLayerBelow(int amount, CallbackInfoReturnable<Boolean> cir) {
        layerBelow = definitelymycoords$undoOffset(layerBelow);
    }

    @Inject(
            method = "moveLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerSingle(I)V",
                    shift = At.Shift.AFTER
            )
    )
    private void definitelymycoords$offsetLayerSingle(int amount, CallbackInfoReturnable<Boolean> cir) {
        layerSingle = definitelymycoords$offset(layerSingle);
    }

    @Inject(
            method = "moveLayer",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerSingle(I)V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/InfoUtils;printActionbarMessage(Ljava/lang/String;[Ljava/lang/Object;)V",
                    ordinal = 0
            )
    )
    private void definitelymycoords$undoOffsetLayerSingle(int amount, CallbackInfoReturnable<Boolean> cir) {
        layerSingle = definitelymycoords$undoOffset(layerSingle);
    }

    @Inject(
            method = "moveLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerAbove(I)V",
                    shift = At.Shift.AFTER
            )
    )
    private void definitelymycoords$offsetLayerAbove(int amount, CallbackInfoReturnable<Boolean> cir) {
        layerAbove = definitelymycoords$offset(layerAbove);
    }

    @Inject(
            method = "moveLayer",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lfi/dy/masa/malilib/util/LayerRange;setLayerAbove(I)V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/InfoUtils;printActionbarMessage(Ljava/lang/String;[Ljava/lang/Object;)V",
                    ordinal = 0
            )
    )
    private void definitelymycoords$undoOffsetLayerAbove(int amount, CallbackInfoReturnable<Boolean> cir) {
        layerAbove = definitelymycoords$undoOffset(layerAbove);
    }

    @Unique
    private int definitelymycoords$offset(int value) {
        return (int) switch (axis) {
            case X -> DMCApi.getOffsetBlockX(value);
            case Y -> DMCApi.getOffsetBlockY(value);
            case Z -> DMCApi.getOffsetBlockZ(value);
        };
    }

    @Unique
    private int definitelymycoords$undoOffset(int value) {
        return (int) switch (axis) {
            case X -> DMCApi.getUndoOffsetBlockX(value);
            case Y -> DMCApi.getUndoOffsetBlockY(value);
            case Z -> DMCApi.getUndoOffsetBlockZ(value);
        };
    }
}
