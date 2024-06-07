package de.luludodo.dmc.mixins.xaeroworldmap;

import de.luludodo.dmc.api.DMCApi;
import de.luludodo.dmc.log.Log;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import xaero.map.graphics.MapRenderHelper;
import xaero.map.gui.GuiMap;

@Mixin(GuiMap.class)
@Debug(export = true)
public class GuiMapMixin {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lxaero/map/graphics/MapRenderHelper;drawCenteredStringWithBackground(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIFFFFLnet/minecraft/client/render/VertexConsumer;)V",
                    ordinal = 0
            )
    )
    public void definitelymycoords$topCoords(DrawContext guiGraphics, TextRenderer font, String string, int x, int y, int color, float bgRed, float bgGreen, float bgBlue, float bgAlpha, VertexConsumer backgroundVertexBuffer) {
        String[] parts = string.split(" ");
        String mouseXs = parts[1];
        String mouseYs = parts[3];
        String mouseY2s = null;
        String mouseZs;
        if (parts.length == 7) {
            mouseY2s = parts[4];
            mouseZs = parts[6];
        } else if (parts.length == 6) {
            mouseZs = parts[5];
        } else {
            MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, font, "Hidden", x, y, color, bgRed, bgGreen, bgBlue, bgAlpha, backgroundVertexBuffer);
            return;
        }
        String text;
        try {
            long mouseX = DMCApi.getOffsetBlockX(Integer.parseInt(mouseXs));
            long mouseY = DMCApi.getOffsetBlockY(Integer.parseInt(mouseYs));
            long mouseZ = DMCApi.getOffsetBlockZ(Integer.parseInt(mouseZs));
            if (mouseY2s == null) {
                text = "X: " + mouseX + " Y: " + mouseY + " Z: " + mouseZ;
            } else {
                long mouseY2 = DMCApi.getOffsetBlockY(Integer.parseInt(mouseY2s.substring(1, mouseY2s.length() - 1)));
                text = "X: " + mouseX + " Y: " + mouseY + " (" + mouseY2 + ") Z: " + mouseZ;
            }
        } catch (NumberFormatException e) {
            text = "Hidden";
        }
        MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, font, text, x, y, color, bgRed, bgGreen, bgBlue, bgAlpha, backgroundVertexBuffer);
    }

    @ModifyArgs(
            method = "getRightClickOptions",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 2
            )
    )
    public void definitelymycoords$rightClickCoords(Args args) {
        try {
            Object[] formatArgs = args.get(1);
            formatArgs[0] = DMCApi.getOffsetBlockX((int) formatArgs[0]);
            formatArgs[1] = DMCApi.getOffsetBlockY((int) formatArgs[1]);
            formatArgs[2] = DMCApi.getOffsetBlockZ((int) formatArgs[2]);
            args.set(1, formatArgs);
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            args.set(0, "Hidden");
        }
    }
}
