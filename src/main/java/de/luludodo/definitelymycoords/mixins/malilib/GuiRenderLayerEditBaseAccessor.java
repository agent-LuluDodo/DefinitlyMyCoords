package de.luludodo.definitelymycoords.mixins.malilib;

import fi.dy.masa.malilib.gui.GuiRenderLayerEditBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GuiRenderLayerEditBase.class, remap = false)
public interface GuiRenderLayerEditBaseAccessor {
    @Accessor("textField1")
    GuiTextFieldGeneric getTextField1();

    @Accessor("textField2")
    GuiTextFieldGeneric getTextField2();
}
