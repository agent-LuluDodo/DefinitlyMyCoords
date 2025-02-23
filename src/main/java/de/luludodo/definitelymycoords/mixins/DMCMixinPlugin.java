package de.luludodo.definitelymycoords.mixins;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.*;

public class DMCMixinPlugin implements IMixinConfigPlugin {
    private static final Logger LOG = LoggerFactory.getLogger("DefinitelyMyCoords/Mixin");

    // mixins.*.<value>.*, condition
    private final Map<String, Set<String>> conditionToMods = ImmutableMap.of(
            "betterf3", Set.of("betterf3"),
            "xaeroworldmap", Set.of("xaeroworldmap"),
            "xaerominimap", Set.of("xaerominimap", "xearominimapfair"),
            "sodiumextra", Set.of("sodium-extra"),
            "litematica", Set.of("litematica"),
            "malilib", Set.of("malilib")
    );

    private final Map<String, Integer> modToMixins = new HashMap<>();

    // Important method
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String mixinPath = mixinClassName.substring(mixinClassName.indexOf("mixins") + 7);
        int dotIndex = mixinPath.indexOf('.');
        String condition = mixinPath.substring(0, dotIndex);

        Set<String> mods = conditionToMods.get(condition);
        String modName = null;
        if (mods == null) {
            modName = "Vanilla";
        } else {
            for (String modId : mods) {
                Optional<ModContainer> mod = FabricLoader.getInstance().getModContainer(modId);
                if (mod.isPresent()) {
                    String newName = mod.get().getMetadata().getName();
                    if (modName != null) {
                        LOG.warn("Mixin '{}' matched multiple mods (\"{}\" and \"{}\") -> keeping {}", mixinClassName, modName, newName, modName);
                    } else {
                        modName = newName;
                    }
                }
            }
        }

        if (modName == null) {
            LOG.debug("Skipping mixin '{}' for '{}'", mixinClassName, targetClassName);
            return false;
        } else {
            LOG.debug("Applying mixin '{}' to '{}' ({})", mixinClassName, targetClassName, modName);
            modToMixins.put(modName, modToMixins.getOrDefault(modName, 0) + 1);
            return true;
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        modToMixins.forEach((mod, mixins) -> LOG.info("Applied {} mixins to {}", mixins, mod));
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
