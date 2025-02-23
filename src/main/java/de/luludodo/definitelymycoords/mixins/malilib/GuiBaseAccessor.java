package de.luludodo.definitelymycoords.mixins.malilib;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.wrappers.TextFieldWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = GuiBase.class, remap = false)
public interface GuiBaseAccessor {
    @Accessor("textFields")
    List<TextFieldWrapper<? extends GuiTextFieldGeneric>> getTextFields();
}
