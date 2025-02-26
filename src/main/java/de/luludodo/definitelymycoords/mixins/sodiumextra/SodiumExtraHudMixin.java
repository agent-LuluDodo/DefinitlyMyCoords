package de.luludodo.definitelymycoords.mixins.sodiumextra;

import de.luludodo.definitelymycoords.api.DMCApi;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SodiumExtraHud.class, remap = false)
public class SodiumExtraHudMixin {
    @Redirect(
            method = "onStartTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPos()Lnet/minecraft/util/math/Vec3d;",
                    ordinal = 0,
                    remap = true
            )
    )
    private Vec3d definitelymycoords$getPos(ClientPlayerEntity instance) {
        return DMCApi.getOffset(instance.getPos());
    }
}
