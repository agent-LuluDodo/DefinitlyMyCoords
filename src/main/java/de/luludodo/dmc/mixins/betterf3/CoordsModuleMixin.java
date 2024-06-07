package de.luludodo.dmc.mixins.betterf3;

import me.cominixo.betterf3.modules.CoordsModule;
import de.luludodo.dmc.api.DMCApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CoordsModule.class)
public class CoordsModuleMixin {

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 0), index = 1)
    public Object[] definitelymycoords$getCameraX(Object[] args) {
        args[0] = DMCApi.getOffsetX((double) args[0]);
        return args;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 1), index = 1)
    public Object[] definitelymycoords$getCameraY(Object[] args) {
        args[0] = DMCApi.getOffsetY((double) args[0]);
        return args;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 2), index = 1)
    public Object[] definitelymycoords$getCameraZ(Object[] args) {
        args[0] = DMCApi.getOffsetZ((double) args[0]);
        return args;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 6), index = 0)
    public Object definitelymycoords$getBlockX(Object x) {
        return DMCApi.getOffsetBlockX((int) x);
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 7), index = 0)
    public Object definitelymycoords$getBlockY(Object y) {
        return DMCApi.getOffsetBlockY((int) y);
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 8), index = 0)
    public Object definitelymycoords$getBlockZ(Object z) {
        return DMCApi.getOffsetBlockZ((int) z);
    }


    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 9), index = 0)
    public Object definitelymycoords$getChunkRelativeX(Object x) {
        return DMCApi.getOffsetBlockX((int) x) & 15;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 10), index = 0)
    public Object definitelymycoords$getChunkRelativeY(Object y) {
        return DMCApi.getOffsetBlockY((int) y) & 15;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 11), index = 0)
    public Object definitelymycoords$getChunkRelativeZ(Object z) {
        return DMCApi.getOffsetBlockZ((int) z) & 15;
    }


    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 12), index = 0)
    public Object definitelymycoords$getChunkX(Object x) {
        return DMCApi.getOffsetBlockX(MinecraftClient.getInstance().cameraEntity.getBlockX()) >> 4;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 13), index = 0)
    public Object definitelymycoords$getChunkY(Object y) {
        return DMCApi.getOffsetBlockY(MinecraftClient.getInstance().cameraEntity.getBlockY()) >> 4;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/Utils;styledText(Ljava/lang/Object;Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/MutableText;", ordinal = 14), index = 0)
    public Object definitelymycoords$getChunkZ(Object z) {
        return DMCApi.getOffsetBlockZ(MinecraftClient.getInstance().cameraEntity.getBlockZ()) >> 4;
    }
}
