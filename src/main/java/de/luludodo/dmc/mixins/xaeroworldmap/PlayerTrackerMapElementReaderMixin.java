package de.luludodo.dmc.mixins.xaeroworldmap;

import de.luludodo.dmc.api.DMCApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = {"xaero.map.radar.tracker.PlayerTrackerMapElementReader$2"}, remap = false)
public class PlayerTrackerMapElementReaderMixin {
    @ModifyArg(
            method = "getName",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;floor(D)D",
                    ordinal = 0
            ),
            index = 0
    )
    public double rebindmykeys$x(double x) {
        return DMCApi.getOffsetX(x);
    }

    @ModifyArg(
            method = "getName",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;floor(D)D",
                    ordinal = 1
            ),
            index = 0
    )
    public double rebindmykeys$y(double y) {
        return DMCApi.getOffsetY(y);
    }

    @ModifyArg(
            method = "getName",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;floor(D)D",
                    ordinal = 2
            ),
            index = 0
    )
    public double rebindmykeys$z(double z) {
        return DMCApi.getOffsetZ(z);
    }
}
