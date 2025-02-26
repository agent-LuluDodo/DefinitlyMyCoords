package de.luludodo.definitelymycoords.mixins;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.*;

public class DMCMixinPlugin implements IMixinConfigPlugin {
    private static final Logger LOG = LoggerFactory.getLogger("DefinitelyMyCoords/Mixin");

    /**
     * @param minVersion inclusive
     * @param maxVersion exclusive
     * @param ids mod ids
     */
    private record Condition(Version minVersion, Version maxVersion, String... ids) {
        Condition(String... ids) {
            this(null, null, ids);
        }
    }

    // mixins.*.<value>.*, condition
    private final Map<String, Condition> idToCondition = ImmutableMap.of(
            "betterf3", new Condition("betterf3"),
            "xaeroworldmap", new Condition("xaeroworldmap"),
            "old_xaerominimap", new Condition(null, version("24.5.0"), "xaerominimap", "xearominimapfair"),
            "xaerominimap", new Condition(version("24.5.0"), null, "xaerominimap", "xearominimapfair"),
            "sodiumextra", new Condition("sodium-extra"),
            "litematica", new Condition("litematica"),
            "malilib", new Condition("malilib")
    );

    /**
     * Only use for static input!
     */
    private static Version version(String v) {
        try {
            return Version.parse(v);
        } catch (VersionParsingException e) {
            throw new Error(e); // Should never happen because the string doesn't change
        }
    }

    private final Map<String, Integer> modToMixins = new HashMap<>();

    // Important method
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String mixinPath = mixinClassName.substring(mixinClassName.indexOf("mixins") + 7);
        int dotIndex = mixinPath.indexOf('.');
        String conditionId = mixinPath.substring(0, dotIndex);

        Condition condition = idToCondition.get(conditionId);
        String mod = getModForCondition(mixinClassName, condition);

        if (mod == null) {
            LOG.debug("[DefinitelyMyCoords] Skipping mixin '{}' for '{}'", mixinClassName, targetClassName);
            return false;
        } else {
            LOG.debug("[DefinitelyMyCoords] Applying mixin '{}' to '{}' ({})", mixinClassName, targetClassName, mod);
            modToMixins.put(mod, modToMixins.getOrDefault(mod, 0) + 1);
            return true;
        }
    }

    private String getModForCondition(String mixinClassName, Condition condition) {
        if (condition == null) {
            return "Vanilla";
        }

        String potentialModName = null;
        ModContainer mod = null;
        for (String modId : condition.ids()) {
            Optional<ModContainer> potentialMod = FabricLoader.getInstance().getModContainer(modId);
            if (potentialMod.isPresent()) {
                if (mod == null) {
                    mod = potentialMod.get();
                    potentialModName = mod.getMetadata().getName();
                } else {
                    LOG.warn("[DefinitelyMyCoords] Mixin '{}' matched multiple mods (\"{}\" and \"{}\") -> keeping {}", mixinClassName, potentialModName, potentialMod.get().getMetadata().getName(), potentialModName);
                }
            }
        }

        if (mod == null) {
            return null;
        }

        List<String> additions = new ArrayList<>();
        Version version = mod.getMetadata().getVersion();

        if (condition.minVersion() != null) {
            if (version.compareTo(condition.minVersion()) >= 0) {
                additions.add(">=" + condition.minVersion().getFriendlyString());
            } else {
                return null;
            }
        }

        if (condition.maxVersion() != null) {
            if (version.compareTo(condition.maxVersion()) < 0) {
                additions.add("<" + condition.maxVersion().getFriendlyString());
            } else {
                return null;
            }
        }

        StringBuilder builder = new StringBuilder(potentialModName);
        boolean first = true;
        for (String addition : additions) {
            if (first) {
                first = false;
                builder.append(" (");
            } else {
                builder.append("; ");
            }
            builder.append(addition);
        }

        if (!first) {
            builder.append(")");
        }

        return builder.toString();
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        modToMixins.forEach((mod, mixins) -> LOG.info("[DefinitelyMyCoords] Applied {} mixins to {}", mixins, mod));
    }

    // Unimportant methods
    @Override
    public String getRefMapperConfig() {
        return null;
    }
    @Override
    public void onLoad(String mixinPackage) {}
    @Override
    public List<String> getMixins() {
        return null;
    }
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
