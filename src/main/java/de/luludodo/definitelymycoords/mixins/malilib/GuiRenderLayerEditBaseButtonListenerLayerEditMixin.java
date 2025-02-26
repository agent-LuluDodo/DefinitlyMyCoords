package de.luludodo.definitelymycoords.mixins.malilib;

import fi.dy.masa.malilib.gui.GuiRenderLayerEditBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.gui.wrappers.TextFieldWrapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "fi.dy.masa.malilib.gui.GuiRenderLayerEditBase$ButtonListenerLayerEdit", remap = false)
public class GuiRenderLayerEditBaseButtonListenerLayerEditMixin {
    @Shadow @Final protected GuiRenderLayerEditBase parent;

    @Inject(
            method = "actionPerformedWithButton",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;setAxis(Lnet/minecraft/util/math/Direction$Axis;)V",
                    shift = At.Shift.AFTER,
                    remap = true
            )
    )
    @SuppressWarnings("unchecked")
    private void definitelymycoords$setAxis(ButtonBase button, int mouseButton, CallbackInfo ci) {
        GuiTextFieldGeneric textField1 = ((GuiRenderLayerEditBaseAccessor) parent).getTextField1();
        GuiTextFieldGeneric textField2 = ((GuiRenderLayerEditBaseAccessor) parent).getTextField2();
        for (TextFieldWrapper<? extends GuiTextFieldGeneric> wrapper : ((GuiBaseAccessor) parent).getTextFields()) {
            if (wrapper.getTextField() == textField1) {
                ((ITextFieldListener<GuiTextFieldGeneric>) wrapper.getListener()).onTextChange(textField1);
            } else if (wrapper.getTextField() == textField2) {
                ((ITextFieldListener<GuiTextFieldGeneric>) wrapper.getListener()).onTextChange(textField2);
            }
        }
    }
}
