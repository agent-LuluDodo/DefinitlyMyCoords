package de.luludodo.definitelymycoords.mixins.litematica;

import de.luludodo.definitelymycoords.api.DMCApi;
import fi.dy.masa.litematica.scheduler.tasks.TaskBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = TaskBase.class, remap = false)
public class TaskBaseMixin {
    @ModifyArg(
            method = "updateInfoHudLinesPendingChunks",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 1
            ),
            index = 1
    )
    private Object[] definitelymycoords$chunkPos(Object[] args) {
        DMCApi.safeArgsOffsetChunk("DefinitelyMyCoords/Litematica/TaskBaseMixin/chunkPos", args, 0, 1);
        DMCApi.safeArgsOffsetBlock("DefinitelyMyCoords/Litematica/TaskBaseMixin/chunkPos", args, 2, null, 3);
        return args;
    }
}
