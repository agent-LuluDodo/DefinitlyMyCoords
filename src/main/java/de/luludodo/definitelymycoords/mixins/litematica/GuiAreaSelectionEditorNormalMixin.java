package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.gui.GuiAreaSelectionEditorNormal;
import fi.dy.masa.litematica.selection.Box;
import fi.dy.masa.litematica.util.PositionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = GuiAreaSelectionEditorNormal.class, remap = false)
public class GuiAreaSelectionEditorNormalMixin {
    @ModifyArg(
            method = "createCoordinateInput",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;valueOf(I)Ljava/lang/String;",
                    ordinal = 0
            )
    )
    private int definitelymycoords$getX(int x) {
        return (int) DMCApi.getOffsetBlockX(x);
    }

    @ModifyArg(
            method = "createCoordinateInput",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;valueOf(I)Ljava/lang/String;",
                    ordinal = 1
            )
    )
    private int definitelymycoords$getY(int y) {
        return (int) DMCApi.getOffsetBlockY(y);
    }

    @ModifyArg(
            method = "createCoordinateInput",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;valueOf(I)Ljava/lang/String;",
                    ordinal = 2
            )
    )
    private int definitelymycoords$getZ(int z) {
        return (int) DMCApi.getOffsetBlockZ(z);
    }

    @ModifyArg(
            method = "updatePosition",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/litematica/selection/AreaSelection;setCoordinate(Lfi/dy/masa/litematica/selection/Box;Lfi/dy/masa/litematica/util/PositionUtils$Corner;Lfi/dy/masa/malilib/util/PositionUtils$CoordinateType;I)V"
            ),
            index = 3
    )
    private int definitelymycoords$set(Box box, PositionUtils.Corner corner, fi.dy.masa.malilib.util.PositionUtils.CoordinateType type, int value) {
        return (int) switch (type) {
            case X -> DMCApi.getUndoOffsetBlockX(value);
            case Y -> DMCApi.getUndoOffsetBlockY(value);
            case Z -> DMCApi.getUndoOffsetBlockZ(value);
        };
    }
}
