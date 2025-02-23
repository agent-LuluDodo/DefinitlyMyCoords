package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "fi.dy.masa.litematica.event.KeyCallbacks$KeyCallbackToggleMessage", remap = false)
public class KeyCallbackToggleMessageMixin {
    @ModifyArg(method = "onKeyAction", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"), index = 1)
    public Object[] definitelymycoords$getPos(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/KeyCallbackToggleMessageMixin/getPos", args, 0, 1, 2);
        return args;
    }
}