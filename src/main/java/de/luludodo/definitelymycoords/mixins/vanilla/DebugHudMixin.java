package de.luludodo.definitelymycoords.mixins.vanilla;

import de.luludodo.definitelymycoords.DefinitelyMyCoords;
import de.luludodo.definitelymycoords.api.DMCApi;
import de.luludodo.definitelymycoords.gui.ConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
    @Shadow @Final private MinecraftClient client;
    @Unique private static boolean DEFINITELYMYCOORDS$ALREADY_WARNED_GET_BLOCK_XYZ = false;

    @ModifyArg(method = "getLeftText", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 4), index = 2)
    public Object[] definitelymycoords$getXYZ(Object[] args) {
        DMCApi.safeArgsOffset("DefinitelyMyCoords/Vanilla/DebugHudMixin/getXYZ", args, 0, 1, 2);
        return args;
    }
    @ModifyArg(method = "getLeftText", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 5), index = 2)
    public Object[] definitelymycoords$getBlockXYZ(Object[] args) {
        if (DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Vanilla/DebugHudMixin/getBlockXYZ", args, 0, 1, 2) &&
                args.length > 5) {
            args[3] = (long) args[0] & 15;
            args[4] = (long) args[1] & 15;
            args[5] = (long) args[2] & 15;
        } else {
            if (!DEFINITELYMYCOORDS$ALREADY_WARNED_GET_BLOCK_XYZ) {
                DEFINITELYMYCOORDS$ALREADY_WARNED_GET_BLOCK_XYZ = true;
                DefinitelyMyCoords.LOG.error("DefinitelyMyCoords/Vanilla/DebugHudMixin/getBlockXYZ failed to offset coords (only logs once)");
            }
        }
        return args;
    }
    @ModifyArg(method = "getLeftText", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 6), index = 2)
    public Object[] definitelymycoords$getChunkXYZ(Object[] args) {
        BlockPos blockPos = DMCApi.getOffsetBlock(MinecraftClient.getInstance().cameraEntity.getBlockPos());
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

    @Inject(method = "getChunk", at = @At("HEAD"), cancellable = true)
    public void definitelymycoords$fixFlickering(CallbackInfoReturnable<WorldChunk> cir) {
        if (client.currentScreen instanceof ConfigScreen)
            cir.setReturnValue(null);
    }

    @Unique
    private String definitelymycoords$offsetBlockHitString(String originalString) {
        int posStartIndex = originalString.lastIndexOf(':') + 2;
        if (posStartIndex == 1) { // -1 (<- no result) + 2
            return Text.translatable("offset.definitelymycoords.on-error").getString();
        }
        String[] blockPosStrings = originalString.substring(posStartIndex).split(", ");
        if (blockPosStrings.length != 3) {
            return Text.translatable("offset.definitelymycoords.on-error").getString();
        }
        try {
            return originalString.substring(0, posStartIndex) +
                    DMCApi.getOffsetBlockX(Integer.parseInt(blockPosStrings[0])) + ", " +
                    DMCApi.getOffsetBlockY(Integer.parseInt(blockPosStrings[1])) + ", " +
                    DMCApi.getOffsetBlockZ(Integer.parseInt(blockPosStrings[2]));
        } catch (NumberFormatException ignored) {
            return Text.translatable("offset.definitelymycoords.on-error").getString();
        }
    }
}
