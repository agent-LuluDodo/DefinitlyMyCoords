package de.luludodo.definitelymycoords.mixins.xaerominimap;

import de.luludodo.definitelymycoords.api.DMCApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xaero.hud.minimap.info.BuiltInInfoDisplays;
import xaero.hud.minimap.info.InfoDisplay;
import xaero.hud.minimap.info.render.compile.InfoDisplayOnCompile;

import java.util.Objects;

@Mixin(value = InfoDisplay.class, remap = false)
public class InfoDisplayMixin {
    @ModifyVariable(
            method = "<init>(Ljava/lang/String;Lnet/minecraft/text/Text;Ljava/lang/Object;Lxaero/hud/minimap/info/codec/InfoDisplayStateCodec;Lxaero/hud/minimap/info/widget/InfoDisplayWidgetFactory;Lxaero/hud/minimap/info/render/compile/InfoDisplayOnCompile;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            index = 6,
            argsOnly = true,
            remap = true
    )
    private static <T> InfoDisplayOnCompile<T> definitelymycoords$init(final InfoDisplayOnCompile<T> value) {
        return (
                displayInfo,
                compiler,
                session,
                availableWidth,
                playerPos
        ) -> {
            boolean shouldOffset =
                    Objects.equals(displayInfo.getId(), BuiltInInfoDisplays.COORDINATES.getId()) ||
                    Objects.equals(displayInfo.getId(), BuiltInInfoDisplays.OVERWORLD_COORDINATES.getId()) ||
                    Objects.equals(displayInfo.getId(), BuiltInInfoDisplays.CHUNK_COORDINATES.getId());
            value.onCompile(
                    displayInfo,
                    compiler,
                    session,
                    availableWidth,
                    shouldOffset ? DMCApi.getOffsetBlock(playerPos) : playerPos
            );
        };
    }
}
