package de.luludodo.dmc.modes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.TranslatableOption;

@Environment(value= EnvType.CLIENT)
public enum Mode implements TranslatableOption {
    VANILLA(3, "options.dmc.vanilla"),
    RELATIVE(0, "options.dmc.relative"),
    ABSOLUTE(1, "options.dmc.absolute"),
    CUSTOM(2, "options.dmc.custom");

    private final int id;
    private final String translationKey;
    Mode(int id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTranslationKey() {
        return translationKey;
    }
}