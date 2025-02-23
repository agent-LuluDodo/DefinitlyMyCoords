package de.luludodo.definitelymycoords.api;

import de.luludodo.definitelymycoords.modes.custom.RelativeF3Coords;
import de.luludodo.definitelymycoords.config.ConfigAPI;
import de.luludodo.definitelymycoords.modes.Mode;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class DMCApi {
    public static final Logger LOG = LoggerFactory.getLogger("DefinitelyMyCoords/Api");

    public static int getOffsetChunkX(int x) {
        if (isVanilla()) return x;

        if (isCustom())
            return x - (int) Math.ceil(RelativeF3Coords.getOldBlockX() / 16d);

        return x + (int) Math.ceil(ConfigAPI.getOffsetX() / 16d);
    }
    public static int getOffsetChunkZ(int z) {
        if (isVanilla()) return z;

        if (isCustom())
            return z - (int) Math.ceil(RelativeF3Coords.getOldBlockZ() / 16d);

        return z + (int) Math.ceil(ConfigAPI.getOffsetZ() / 16d);
    }
    public static ChunkPos getOffsetChunk(ChunkPos pos) {
        return new ChunkPos(
                getOffsetChunkX(pos.x),
                getOffsetChunkZ(pos.z)
        );
    }

    private static final Set<String> ALREADY_WARNED_BY_OFFSET_CHUNK_X = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_OFFSET_CHUNK_Z = new HashSet<>();
    public static boolean safeArgsOffsetChunk(String id, Object[] args, Integer x, Integer z) {
        boolean success = true;
        if (x != null) {
            if (args.length > x && args[x] instanceof Number argsX) {
                args[x] = getOffsetChunkX(argsX.intValue());
            } else {
                if (!ALREADY_WARNED_BY_OFFSET_CHUNK_X.contains(id)) {
                    ALREADY_WARNED_BY_OFFSET_CHUNK_X.add(id);
                    LOG.error("Cannot offset chunk x-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (z != null) {
            if (args.length > z && args[z] instanceof Number argsZ) {
                args[z] = getOffsetChunkZ(argsZ.intValue());
            } else {
                if (!ALREADY_WARNED_BY_OFFSET_CHUNK_Z.contains(id)) {
                    ALREADY_WARNED_BY_OFFSET_CHUNK_Z.add(id);
                    LOG.error("Cannot offset chunk z-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        return success;
    }

    public static int getUndoOffsetChunkX(int x) {
        if (isVanilla()) return x;

        if (isCustom())
            return x + (int) Math.ceil(RelativeF3Coords.getOldBlockX() / 16d);

        return x - (int) Math.ceil(ConfigAPI.getOffsetX() / 16d);
    }
    public static int getUndoOffsetChunkZ(int z) {
        if (isVanilla()) return z;

        if (isCustom())
            return z + (int) Math.ceil(RelativeF3Coords.getOldBlockZ() / 16d);

        return z - (int) Math.ceil(ConfigAPI.getOffsetZ() / 16d);
    }
    public static ChunkPos getUndoOffsetChunk(ChunkPos pos) {
        return new ChunkPos(
                getUndoOffsetChunkX(pos.x),
                getUndoOffsetChunkZ(pos.z)
        );
    }

    private static final Set<String> ALREADY_WARNED_BY_UNDO_OFFSET_CHUNK_X = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_UNDO_OFFSET_CHUNK_Z = new HashSet<>();
    public static boolean safeArgsUndoOffsetChunk(String id, Object[] args, Integer x, Integer z) {
        boolean success = true;
        if (x != null) {
            if (args.length > x && args[x] instanceof Number argsX) {
                args[x] = getUndoOffsetChunkX(argsX.intValue());
            } else {
                if (!ALREADY_WARNED_BY_UNDO_OFFSET_CHUNK_X.contains(id)) {
                    ALREADY_WARNED_BY_UNDO_OFFSET_CHUNK_X.add(id);
                    LOG.error("Cannot undo offset for chunk x-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (z != null) {
            if (args.length > z && args[z] instanceof Number argsZ) {
                args[z] = getUndoOffsetChunkZ(argsZ.intValue());
            } else {
                if (!ALREADY_WARNED_BY_UNDO_OFFSET_CHUNK_Z.contains(id)) {
                    ALREADY_WARNED_BY_UNDO_OFFSET_CHUNK_Z.add(id);
                    LOG.error("Cannot undo offset for chunk z-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        return success;
    }

    public static long getOffsetBlockX(long x) {
        if (isVanilla()) return x;

        if (isCustom())
            return x - RelativeF3Coords.getOldBlockX();

        return x + ConfigAPI.getOffsetX();
    }
    public static long getOffsetBlockY(long y) {
        if (isVanilla()) return y;

        if (isCustom())
            return y - RelativeF3Coords.getOldBlockY();

        return y + ConfigAPI.getOffsetY();
    }
    public static long getOffsetBlockZ(long z) {
        if (isVanilla())
            return z;

        if (isCustom())
            return z - RelativeF3Coords.getOldBlockZ();

        return z + ConfigAPI.getOffsetZ();
    }
    public static BlockPos getOffsetBlock(BlockPos pos) {
        return new BlockPos(
                (int) getOffsetBlockX(pos.getX()),
                (int) getOffsetBlockY(pos.getY()),
                (int) getOffsetBlockZ(pos.getZ())
        );
    }

    private static final Set<String> ALREADY_WARNED_BY_OFFSET_BLOCK_X = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_OFFSET_BLOCK_Y = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_OFFSET_BLOCK_Z = new HashSet<>();
    public static boolean safeArgsOffsetBlock(String id, Object[] args, Integer x, Integer y, Integer z) {
        boolean success = true;
        if (x != null) {
            if (args.length > x && args[x] instanceof Number argsX) {
                args[x] = getOffsetBlockX(argsX.longValue());
            } else {
                if (!ALREADY_WARNED_BY_OFFSET_BLOCK_X.contains(id)) {
                    ALREADY_WARNED_BY_OFFSET_BLOCK_X.add(id);
                    LOG.error("Cannot offset block x-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (y != null) {
            if (args.length > y && args[y] instanceof Number argsY) {
                args[y] = getOffsetBlockY(argsY.longValue());
            } else {
                if (!ALREADY_WARNED_BY_OFFSET_BLOCK_Y.contains(id)) {
                    ALREADY_WARNED_BY_OFFSET_BLOCK_Y.add(id);
                    LOG.error("Cannot offset block y-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (z != null) {
            if (args.length > z && args[z] instanceof Number argsZ) {
                args[z] = getOffsetBlockZ(argsZ.longValue());
            } else {
                if (!ALREADY_WARNED_BY_OFFSET_BLOCK_Z.contains(id)) {
                    ALREADY_WARNED_BY_OFFSET_BLOCK_Z.add(id);
                    LOG.error("Cannot offset block z-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        return success;
    }

    public static long getUndoOffsetBlockX(long x) {
        if (isVanilla()) return x;

        if (isCustom())
            return x + RelativeF3Coords.getOldBlockX();

        return x - ConfigAPI.getOffsetX();
    }
    public static long getUndoOffsetBlockY(long y) {
        if (isVanilla()) return y;

        if (isCustom())
            return y + RelativeF3Coords.getOldBlockY();

        return y - ConfigAPI.getOffsetY();
    }
    public static long getUndoOffsetBlockZ(long z) {
        if (isVanilla())
            return z;

        if (isCustom())
            return z + RelativeF3Coords.getOldBlockZ();

        return z - ConfigAPI.getOffsetZ();
    }
    public static BlockPos getUndoOffsetBlock(BlockPos pos) {
        return new BlockPos(
                (int) getUndoOffsetBlockX(pos.getX()),
                (int) getUndoOffsetBlockY(pos.getY()),
                (int) getUndoOffsetBlockZ(pos.getZ())
        );
    }

    private static final Set<String> ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_X = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_Y = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_Z = new HashSet<>();
    public static boolean safeArgsUndoOffsetBlock(String id, Object[] args, Integer x, Integer y, Integer z) {
        boolean success = true;
        if (x != null) {
            if (args.length > x && args[x] instanceof Number argsX) {
                args[x] = getUndoOffsetBlockX(argsX.longValue());
            } else {
                if (!ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_X.contains(id)) {
                    ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_X.add(id);
                    LOG.error("Cannot undo offset for block x-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (y != null) {
            if (args.length > y && args[y] instanceof Number argsY) {
                args[y] = getUndoOffsetBlockY(argsY.longValue());
            } else {
                if (!ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_Y.contains(id)) {
                    ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_Y.add(id);
                    LOG.error("Cannot undo offset for block y-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (z != null) {
            if (args.length > z && args[z] instanceof Number argsZ) {
                args[z] = getUndoOffsetBlockZ(argsZ.longValue());
            } else {
                if (!ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_Z.contains(id)) {
                    ALREADY_WARNED_BY_UNDO_OFFSET_BLOCK_Z.add(id);
                    LOG.error("Cannot undo offset for block z-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        return success;
    }

    public static double getOffsetX(double x) {
        if (isVanilla())
            return x;

        if (isCustom())
            return x - RelativeF3Coords.getOldX();

        return x + ConfigAPI.getOffsetX();
    }
    public static double getOffsetY(double y) {
        if (isVanilla())
            return y;

        if (isCustom())
            return y - RelativeF3Coords.getOldY();

        return y + ConfigAPI.getOffsetY();
    }
    public static double getOffsetZ(double z) {
        if (isVanilla())
            return z;

        if (isCustom())
            return z - RelativeF3Coords.getOldZ();

        return z + ConfigAPI.getOffsetZ();
    }
    public static Vec3d getOffset(Vec3d pos) {
        return new Vec3d(
                getOffsetX(pos.getX()),
                getOffsetY(pos.getY()),
                getOffsetZ(pos.getZ())
        );
    }

    private static final Set<String> ALREADY_WARNED_BY_OFFSET_X = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_OFFSET_Y = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_OFFSET_Z = new HashSet<>();
    public static boolean safeArgsOffset(String id, Object[] args, Integer x, Integer y, Integer z) {
        boolean success = true;
        if (x != null) {
            if (args.length > x && args[x] instanceof Number argsX) {
                args[x] = getOffsetX(argsX.doubleValue());
            } else {
                if (!ALREADY_WARNED_BY_OFFSET_X.contains(id)) {
                    ALREADY_WARNED_BY_OFFSET_X.add(id);
                    LOG.error("Cannot offset x-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (y != null) {
            if (args.length > y && args[y] instanceof Number argsY) {
                args[y] = getOffsetY(argsY.doubleValue());
            } else {
                if (!ALREADY_WARNED_BY_OFFSET_Y.contains(id)) {
                    ALREADY_WARNED_BY_OFFSET_Y.add(id);
                    LOG.error("Cannot offset y-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (z != null) {
            if (args.length > z && args[z] instanceof Number argsZ) {
                args[z] = getOffsetZ(argsZ.doubleValue());
            } else {
                if (!ALREADY_WARNED_BY_OFFSET_Z.contains(id)) {
                    ALREADY_WARNED_BY_OFFSET_Z.add(id);
                    LOG.error("Cannot offset z-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        return success;
    }

    public static double getUndoOffsetX(double x) {
        if (isVanilla())
            return x;

        if (isCustom())
            return x + RelativeF3Coords.getOldX();

        return x - ConfigAPI.getOffsetX();
    }
    public static double getUndoOffsetY(double y) {
        if (isVanilla())
            return y;

        if (isCustom())
            return y + RelativeF3Coords.getOldY();

        return y - ConfigAPI.getOffsetY();
    }
    public static double getUndoOffsetZ(double z) {
        if (isVanilla())
            return z;

        if (isCustom())
            return z + RelativeF3Coords.getOldZ();

        return z - ConfigAPI.getOffsetZ();
    }
    public static Vec3d getUndoOffset(Vec3d pos) {
        return new Vec3d(
                getUndoOffsetX(pos.getX()),
                getUndoOffsetY(pos.getY()),
                getUndoOffsetZ(pos.getZ())
        );
    }

    private static final Set<String> ALREADY_WARNED_BY_UNDO_OFFSET_X = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_UNDO_OFFSET_Y = new HashSet<>();
    private static final Set<String> ALREADY_WARNED_BY_UNDO_OFFSET_Z = new HashSet<>();
    public static boolean safeArgsUndoOffset(String id, Object[] args, Integer x, Integer y, Integer z) {
        boolean success = true;
        if (x != null) {
            if (args.length > x && args[x] instanceof Number argsX) {
                args[x] = getUndoOffsetX(argsX.doubleValue());
            } else {
                if (!ALREADY_WARNED_BY_UNDO_OFFSET_X.contains(id)) {
                    ALREADY_WARNED_BY_UNDO_OFFSET_X.add(id);
                    LOG.error("Cannot undo offset for x-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (y != null) {
            if (args.length > y && args[y] instanceof Number argsY) {
                args[y] = getUndoOffsetY(argsY.doubleValue());
            } else {
                if (!ALREADY_WARNED_BY_UNDO_OFFSET_Y.contains(id)) {
                    ALREADY_WARNED_BY_UNDO_OFFSET_Y.add(id);
                    LOG.error("Cannot undo offset for y-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        if (z != null) {
            if (args.length > z && args[z] instanceof Number argsZ) {
                args[z] = getUndoOffsetZ(argsZ.doubleValue());
            } else {
                if (!ALREADY_WARNED_BY_UNDO_OFFSET_Z.contains(id)) {
                    ALREADY_WARNED_BY_UNDO_OFFSET_Z.add(id);
                    LOG.error("Cannot undo offset for z-position as requested by '{}', because it's out of bounds or not a number (only logs once)", id);
                }
                success = false;
            }
        }
        return success;
    }

    public static boolean isCustom() {
        return ConfigAPI.getMode() == Mode.CUSTOM;
    }

    public static boolean isVanilla() {
        return ConfigAPI.getMode() == Mode.VANILLA;
    }

    public static boolean obscureRotations() {
        return ConfigAPI.getObscureRotations();
    }

    public static boolean isValidBiome(WorldView world, Identifier biome) {
        return world.getRegistryManager().get(RegistryKeys.BIOME).containsId(biome);
    }
}
