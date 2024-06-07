package de.luludodo.dmc.mixins.sodiumextra;

import de.luludodo.dmc.api.DMCApi;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SodiumExtraHud.class)
public class SodiumExtraHudMixin {
    @Redirect(
            method = "onStartTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPos()Lnet/minecraft/util/math/Vec3d;",
                    ordinal = 0
            )
    )
    private Vec3d definitelymycoords$getPos(ClientPlayerEntity instance) {
        return DMCApi.getOffset(instance.getPos());
    }
}
