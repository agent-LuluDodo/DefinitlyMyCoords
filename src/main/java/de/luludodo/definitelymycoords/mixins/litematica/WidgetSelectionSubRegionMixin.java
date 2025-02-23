package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.gui.widgets.WidgetSelectionSubRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = WidgetSelectionSubRegion.class, remap = false)
public class WidgetSelectionSubRegionMixin {
    @ModifyArg(
            method = "postRenderHovered",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 0
            ),
            index = 1
    )
    private Object[] definitelymycoords$getPos$1(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/WidgetSelectionSubRegionMixin/getPos[1]", args, 1, 2, 3);
        return args;
    }

    @ModifyArg(
            method = "postRenderHovered",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 1
            ),
            index = 1
    )
    private Object[] definitelymycoords$getPos$2(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/WidgetSelectionSubRegionMixin/getPos[2]", args, 1, 2, 3);
        return args;
    }
}
