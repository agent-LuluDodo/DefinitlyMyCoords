package de.luludodo.definitelymycoords.mixins.xaeroworldmap;

import de.luludodo.definitelymycoords.api.DMCApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "xaero.map.radar.tracker.PlayerTrackerMapElementReader$2", remap = false)
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
    public double definitelymycoords$x(double x) {
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
    public double definitelymycoords$y(double y) {
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
    public double definitelymycoords$z(double z) {
        return DMCApi.getOffsetZ(z);
    }
}
