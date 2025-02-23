package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.selection.SelectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = SelectionManager.class, remap = false)
public class SelectionManagerMixin {
    @ModifyArg(
            method = {
                    "createNewSubRegion",
                    "createNewSubRegionIfDoesntExist",
                    "setPositionOfCurrentSelectionToRayTrace",
                    "moveSelectionOrigin"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"
            ),
            index = 1
    )
    public Object[] definitelymycoords$getPos(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/SelectionManagerMixin/getPos", args, 0, 1, 2);
        return args;
    }
}
