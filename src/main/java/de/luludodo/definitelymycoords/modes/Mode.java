package de.luludodo.definitelymycoords.modes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.TranslatableOption;

@Environment(value= EnvType.CLIENT)
public enum Mode implements TranslatableOption {
    VANILLA(3, "options.definitelymycoords.vanilla"),
    RELATIVE(0, "options.definitelymycoords.relative"),
    ABSOLUTE(1, "options.definitelymycoords.absolute"),
    CUSTOM(2, "options.definitelymycoords.custom");

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
