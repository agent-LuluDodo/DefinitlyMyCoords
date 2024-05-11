package de.luludodo.dmc.mixins.betterf3;

import de.luludodo.dmc.api.DMCApi;
import me.cominixo.betterf3.modules.TargetModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TargetModule.class)
public class TargetModuleMixin {
    @ModifyArg(method = "update", at = @At(value = "INVOKE", remap = false, target = "Lme/cominixo/betterf3/utils/DebugLine;value(Ljava/lang/Object;)V", ordinal = 0), index = 0)
    public Object getBlockHitXYZ(Object original) {
        return offsetBlockHitString((String) original);
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", remap = false, target = "Lme/cominixo/betterf3/utils/DebugLine;value(Ljava/lang/Object;)V", ordinal = 2), index = 0)
    public Object getFluidHitXYZ(Object original) {
        return offsetBlockHitString((String) original);
    }

    @Unique
    public String offsetBlockHitString(String original) {
        String[] blockPosStrings = original.split(", ");
        return DMCApi.getOffsetBlockX(Long.parseLong(blockPosStrings[0])) + ", " +
            DMCApi.getOffsetBlockY(Integer.parseInt(blockPosStrings[1])) + ", " +
            DMCApi.getOffsetBlockZ(Integer.parseInt(blockPosStrings[2]));
    }
}