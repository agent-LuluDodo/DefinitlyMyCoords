package de.luludodo.dmc.mixins.vanilla;

import de.luludodo.dmc.api.DMCApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @ModifyArg(method = "getLeftText", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 3), index = 2)
    public Object[] definitelymycoords$getXYZ(Object[] args) {
        args[0] = DMCApi.getOffsetX((double) args[0]);
        args[1] = DMCApi.getOffsetY((double) args[1]);
        args[2] = DMCApi.getOffsetZ((double) args[2]);
        return args;
    }
    @ModifyArg(method = "getLeftText", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 4), index = 2)
    public Object[] definitelymycoords$getBlockXYZ(Object[] args) {
        long x = DMCApi.getOffsetBlockX((int) args[0]);
        long y = DMCApi.getOffsetBlockY((int) args[1]);
        long z = DMCApi.getOffsetBlockZ((int) args[2]);
        args[0] = x;
        args[1] = y;
        args[2] = z;
        args[3] = x & 15;
        args[4] = y & 15;
        args[5] = z & 15;
        return args;
    }
    @ModifyArg(method = "getLeftText", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 5), index = 2)
    public Object[] definitelymycoords$getChunkXYZ(Object[] args) {
        BlockPos originalblockPos = MinecraftClient.getInstance().cameraEntity.getBlockPos();
        BlockPos blockPos = new BlockPos(
                (int) DMCApi.getOffsetBlockX(originalblockPos.getX()),
                (int) DMCApi.getOffsetBlockY(originalblockPos.getY()),
                (int) DMCApi.getOffsetBlockZ(originalblockPos.getZ())
        );
        ChunkPos chunkPos = new ChunkPos(blockPos);
        args[0] = chunkPos.x;
        args[1] = ChunkSectionPos.getSectionCoord(blockPos.getY());
        args[2] = chunkPos.z;
        args[3] = chunkPos.getRegionRelativeX();
        args[4] = chunkPos.getRegionRelativeZ();
        args[5] = chunkPos.getRegionX();
        args[6] = chunkPos.getRegionZ();
        return args;
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "getRightText", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1), index = 0)
    public<E> E definitelymycoords$addBlockHitXYZ(E original) {
        return (E) definitelymycoords$offsetBlockHitString((String) original);
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "getRightText", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 5), index = 0)
    public<E> E definitelymycoords$addFluidHitXYZ(E original) {
        return (E) definitelymycoords$offsetBlockHitString((String) original);
    }

    @Unique
    private String definitelymycoords$offsetBlockHitString(String originalString) {
        int posStartIndex = originalString.lastIndexOf(':') + 2;
        if (posStartIndex == 1) { // -1 (<- no result) + 2
            return originalString;
        }
        String[] blockPosStrings = originalString.substring(posStartIndex).split(", ");
        if (blockPosStrings.length != 3) {
            return originalString;
        }
        try {
            return originalString.substring(0, posStartIndex + 1) +
                    DMCApi.getOffsetBlockX(Integer.parseInt(blockPosStrings[0])) + ", " +
                    DMCApi.getOffsetBlockY(Integer.parseInt(blockPosStrings[1])) + ", " +
                    DMCApi.getOffsetBlockZ(Integer.parseInt(blockPosStrings[2]));
        } catch (NumberFormatException ignored) {
            return originalString;
        }
    }
}
