package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.gui.widgets.WidgetAreaSelectionEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = WidgetAreaSelectionEntry.class, remap = false)
public class WidgetAreaSelectionEntryMixin {
    @ModifyArg(
            method = "postRenderHovered",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"
            ),
            index = 1
    )
    private Object[] definitelymycoords$origin(Object[] args) {
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/WidgetAreaSelectionEntryMixin/origin", args, 0, 1, 2);
        return args;
    }
}
