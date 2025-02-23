package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = SchematicPlacementManager.class, remap = false)
public class SchematicPlacementManagerMixin {
    @ModifyArg(
            method = "setPositionOfCurrentSelectionTo",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"
            ),
            index = 1
    )
    public Object[] definitelymycoords$getPos(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/SchematicPlacementManagerMixin/getPos", args, 0, 1, 2);
        return args;
    }
}
