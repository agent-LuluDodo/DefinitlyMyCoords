package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.render.infohud.ToolHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = ToolHud.class, remap = false)
public class ToolHudMixin {
    @ModifyArg(
            method = "updateHudText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 0
            ),
            index = 1
    ) // [0] Origin
    public Object[] definitelymycoords$getPos$0(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/ToolHudMixin/getPos[0]", args, 0, 1, 2);
        return args;
    }
    @ModifyArg(
            method = "updateHudText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 1
            ),
            index = 1
    ) // [1] Origin
    public Object[] definitelymycoords$getPos$1(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/ToolHudMixin/getPos[1]", args, 0, 1, 2);
        return args;
    }
    // [2] Size -> don't offset
    @ModifyArg(
            method = "updateHudText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 3
            ),
            index = 1
    ) // [3] Corner 1
    public Object[] definitelymycoords$getPos$3(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/ToolHudMixin/getPos[3]", args, 0, 1, 2);
        return args;
    }
    @ModifyArg(
            method = "updateHudText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 4
            ),
            index = 1
    ) // [4] Corner 2
    public Object[] definitelymycoords$getPos$4(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/ToolHudMixin/getPos[4]", args, 0, 1, 2);
        return args;
    }
    // [5] Schematic Name -> don't offset
    // [6] Subregion Count -> don't offset
    // [7] Subregion Info -> don't offset
    @ModifyArg(
            method = "updateHudText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 8
            ),
            index = 1
    ) // [8] Origin
    public Object[] definitelymycoords$getPos$8(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/ToolHudMixin/getPos[8]", args, 0, 1, 2);
        return args;
    }
    // [9] Subregion Name and Modified
    @ModifyArg(
            method = "updateHudText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 10
            ),
            index = 1
    ) // [10] Origin
    public Object[] definitelymycoords$getPos$10(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/ToolHudMixin/getPos[10]", args, 0, 1, 2);
        return args;
    }
    // [11] Ignores Inventory -> don't offset
    // [12] Selected Schematic -> don't offset
}
