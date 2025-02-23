package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.gui.GuiPlacementConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = GuiPlacementConfiguration.class, remap = false)
public class GuiPlacementConfigurationMixin {
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
}
