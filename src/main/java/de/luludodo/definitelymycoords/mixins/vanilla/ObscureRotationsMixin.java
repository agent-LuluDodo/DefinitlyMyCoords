package de.luludodo.definitelymycoords.mixins.vanilla;

import de.luludodo.definitelymycoords.api.DMCApi;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MathHelper.class)
public class ObscureRotationsMixin {
    @ModifyVariable(method = "hashCode(III)J", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int definitelymycoords$getX(int x) {
        if (DMCApi.obscureRotations()) {
            return (int) DMCApi.getOffsetBlockX(x);
        }
        return x;
    }
    @ModifyVariable(method = "hashCode(III)J", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static int definitelymycoords$getY(int y) {
        if (DMCApi.obscureRotations()) {
            return (int) DMCApi.getOffsetBlockY(y);
        }
        return y;
    }
    @ModifyVariable(method = "hashCode(III)J", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    private static int definitelymycoords$getZ(int z) {
        if (DMCApi.obscureRotations()) {
            return (int) DMCApi.getOffsetBlockZ(z);
        }
        return z;
    }
}
