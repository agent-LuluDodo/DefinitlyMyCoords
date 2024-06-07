package de.luludodo.dmc.mixins;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class DMCMixinPlugin implements IMixinConfigPlugin {
    private static final Logger LOG = LoggerFactory.getLogger("DefinitelyMyCoords/Mixin");

    // mixins.*.<value>.*, condition
    private final Map<String, Supplier<Boolean>> conditions = ImmutableMap.of(
            "betterf3", () -> FabricLoader.getInstance().isModLoaded("betterf3"),
            "xaeroworldmap", () -> FabricLoader.getInstance().isModLoaded("xaeroworldmap"),
            "xaerominimap", () -> FabricLoader.getInstance().isModLoaded("xaerominimap") ||
                    FabricLoader.getInstance().isModLoaded("xaerominimapfair"),
            "sodiumextra", () -> FabricLoader.getInstance().isModLoaded("sodium-extra")
    );

    // Important method
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean apply = true;
        String mixinsClassName = mixinClassName.substring(mixinClassName.indexOf("mixins") + 7);
        for (String part : mixinsClassName.split("\\.")) {
            Supplier<Boolean> condition = conditions.get(part);
            if (condition != null) {
                apply = apply && condition.get();
            }
        }
        if (apply) {
            LOG.info("Applying Mixin ({}) to {}!", mixinsClassName, targetClassName);
        } else {
            LOG.info("Skipping Mixin ({})!", mixinsClassName);
        }
        return apply;
    }

    // Unimportant methods
    @Override
    public void onLoad(String mixinPackage) {}
    @Override
    public String getRefMapperConfig() {
        return null;
    }
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
    @Override
    public List<String> getMixins() {
        return null;
    }
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
