package de.luludodo.dmc.mixins.xaeroworldmap;

import de.luludodo.dmc.api.DMCApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import xaero.map.mods.gui.WaypointReader;

@Mixin(WaypointReader.class)
public class WaypointReaderMixin {
    @ModifyArgs(
            method = "getRightClickOptions(Lxaero/map/mods/gui/Waypoint;Lxaero/map/gui/IRightClickableElement;)Ljava/util/ArrayList;",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 0
            )
    )
    public void definitelymycoords$coords(Args args) {
        try {
            Object[] formatArgs = args.get(1);
            formatArgs[0] = DMCApi.getOffsetBlockX((int) formatArgs[0]);
            formatArgs[1] = "~".equals(formatArgs[1])? "~" : DMCApi.getOffsetBlockY((int) formatArgs[1]);
            formatArgs[2] = DMCApi.getOffsetBlockZ((int) formatArgs[2]);
            args.set(1, formatArgs);
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            args.set(0, "Hidden");
        }
    }
}
