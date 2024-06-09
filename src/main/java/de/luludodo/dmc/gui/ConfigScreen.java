package de.luludodo.dmc.gui;

import de.luludodo.dmc.api.DMCApi;
import de.luludodo.dmc.config.ConfigAPI;
import de.luludodo.dmc.modes.Mode;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

@Environment(value=EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final GameOptions settings;

    private CyclingButtonWidget<Mode> mode;
    private SimpleOption<Boolean> randomRotations;
    private TextFieldWidget offsetX;
    private TextFieldWidget offsetY;
    private TextFieldWidget offsetZ;

    private final Element[] elements = new Element[9];
    private boolean infoMode;

    private long oldX;
    private long oldY;
    private long oldZ;
    private Mode oldMode;
    private boolean oldRandomRotations;
    private boolean oldSpoofBiome;
    private Identifier oldBiome;
    private boolean oldDebugEnabled;

    public ConfigScreen(GameOptions gameOptions) {
        super(Text.translatable("options.dmc.title"));
        this.settings = gameOptions;
    }

    protected void init() {
        oldX = ConfigAPI.getOffsetX();
        oldY = ConfigAPI.getOffsetY();
        oldZ = ConfigAPI.getOffsetZ();
        oldMode = ConfigAPI.getMode();
        oldRandomRotations = ConfigAPI.getObscureRotations();
        oldSpoofBiome = ConfigAPI.getSpoofBiome();
        oldBiome = ConfigAPI.getBiome();
        oldDebugEnabled = client.getDebugHud().shouldShowDebugHud();
        if (oldDebugEnabled) {
            client.getDebugHud().toggleDebugHud();
        }
        randomRotations = SimpleOption.ofBoolean("options.dmc.random-rotations", ConfigAPI.getObscureRotations(), value -> {
            ConfigAPI.setObscureRotations(value);
            client.worldRenderer.reload();
        });
        elements[0] = addDrawableChild(mode = CyclingButtonWidget.builder(
                (Mode mode) -> Text.translatable(mode.getTranslationKey())
        ).values(
                Mode.values()
        ).initially(
                ConfigAPI.getMode()
        ).build(
                width / 2 - 100,
                height / 6 + 8,
                176,
                20,
                Text.translatable("options.dmc.offset-mode"),
                this::onModeChange
        ));
        offsetX = new TextFieldWidget(textRenderer, width / 2 - 76, height / 6 + 32, 176, 20, offsetX, Text.translatable("options.dmc.offset-x"));
        offsetY = new TextFieldWidget(textRenderer, width / 2 - 76, height / 6 + 56, 176, 20, offsetY, Text.translatable("options.dmc.offset-y"));
        offsetZ = new TextFieldWidget(textRenderer, width / 2 - 76, height / 6 + 80, 176, 20, offsetZ, Text.translatable("options.dmc.offset-z"));
        offsetX.setChangedListener(offset -> {
            try {
                if (mode.getValue() == Mode.ABSOLUTE) {
                    ConfigAPI.setOffsetX(Integer.parseInt(offset.replaceAll("^$", "0")));
                } else {
                    ConfigAPI.setOffsetX(Integer.parseInt(offset.replaceAll("^$", "0")) - client.cameraEntity.getBlockX());
                }
            } catch (Exception ignored) {}
        });
        offsetY.setChangedListener(offset -> {
            try {
                if (mode.getValue() == Mode.ABSOLUTE) {
                    ConfigAPI.setOffsetY(Integer.parseInt(offset.replaceAll("^$", "0")));
                } else {
                    ConfigAPI.setOffsetY(Integer.parseInt(offset.replaceAll("^$", "0")) - client.cameraEntity.getBlockY());
                }
            } catch (Exception ignored) {}
        });
        offsetZ.setChangedListener(offset -> {
            try {
                if (mode.getValue() == Mode.ABSOLUTE) {
                    ConfigAPI.setOffsetZ(Integer.parseInt(offset.replaceAll("^$", "0")));
                } else {
                    ConfigAPI.setOffsetZ(Integer.parseInt(offset.replaceAll("^$", "0")) - client.cameraEntity.getBlockZ());
                }
            } catch (Exception ignored) {}
        });
        elements[1] = addDrawableChild(randomRotations.createWidget(settings, width / 2 - 100, height / 6 - 16, 200));
        elements[5] = addDrawableChild(ButtonWidget.builder(Text.of("i"), button -> infoMode = !infoMode).dimensions(width / 2 + 80, height / 6 + 8, 20, 20).build());
        Mode curMode = mode.getValue();
        if (curMode != Mode.CUSTOM && curMode != Mode.VANILLA) {
            if (curMode == Mode.RELATIVE) {
                offsetX.setText(((ConfigAPI.getOffsetX() + client.cameraEntity.getBlockX()) + "").replaceAll("\\.0$", ""));
                offsetY.setText(((ConfigAPI.getOffsetY() + client.cameraEntity.getBlockY()) + "").replaceAll("\\.0$", ""));
                offsetZ.setText(((ConfigAPI.getOffsetZ() + client.cameraEntity.getBlockZ()) + "").replaceAll("\\.0$", ""));
            } else {
                offsetX.setText((ConfigAPI.getOffsetX() + "").replaceAll("\\.0$", ""));
                offsetY.setText((ConfigAPI.getOffsetY() + "").replaceAll("\\.0$", ""));
                offsetZ.setText((ConfigAPI.getOffsetZ() + "").replaceAll("\\.0$", ""));
            }
            elements[2] = addDrawableChild(offsetX);
            elements[3] = addDrawableChild(offsetY);
            elements[4] = addDrawableChild(offsetZ);
        }
        elements[6] = addDrawableChild(new TextFieldWidget(textRenderer, width / 2 - 60, height / 6 + 120, 160, 20, Text.translatable("options.dmc.biome")));
        ((TextFieldWidget) elements[6]).setChangedListener(biome -> {
            if (biome.isEmpty()) {
                ConfigAPI.setSpoofBiome(false);
                client.worldRenderer.reload();
            } else {
                Identifier id = Identifier.tryParse(biome);
                if (id == null) return;
                if (client.world == null) return;
                if (!DMCApi.isValidBiome(client.world, id)) return;
                ConfigAPI.setBiome(id);
                ConfigAPI.setSpoofBiome(true);
                client.worldRenderer.reload();
            }
        });
        if (ConfigAPI.getSpoofBiome()) {
            ((TextFieldWidget) elements[6]).setText(ConfigAPI.getBiome().toString());
        }
        elements[7] = addDrawableChild(ButtonWidget.builder(Text.translatable("options.dmc.save"), button -> close()).dimensions(width / 2 - 100, height / 6 + 144, 200, 20).build());
        elements[8] = addDrawableChild(ButtonWidget.builder(Text.translatable("options.dmc.cancel"), button -> cancel()).dimensions(width / 2 - 100, height / 6 + 168, 200, 20).build());
    }

    private void onModeChange(CyclingButtonWidget<Mode> widget, Mode mode) {
        if (mode == Mode.CUSTOM || mode == Mode.VANILLA) {
            if (this.children().contains(offsetX)) {
                remove(offsetX);
                remove(offsetY);
                remove(offsetZ);
            }
            ConfigAPI.setMode(mode);
            return;
        } else {
            if (!this.children().contains(offsetX)) {
                elements[2] = addDrawableChild(offsetX);
                elements[3] = addDrawableChild(offsetY);
                elements[4] = addDrawableChild(offsetZ);
            }
        }
        if (mode == Mode.RELATIVE) {
            offsetX.setText(
                    String.valueOf(
                            ConfigAPI.getOffsetX() + client.cameraEntity.getBlockX()
                    ).replaceAll(
                            "\\.0$",
                            ""
                    )
            );
            offsetY.setText(
                    String.valueOf(
                            ConfigAPI.getOffsetY() + client.cameraEntity.getBlockY()
                    ).replaceAll(
                            "\\.0$",
                            ""
                    )
            );
            offsetZ.setText(
                    String.valueOf(
                            ConfigAPI.getOffsetZ() + client.cameraEntity.getBlockZ()
                    ).replaceAll(
                            "\\.0$",
                            ""
                    )
            );
        } else  {
            offsetX.setText(
                    String.valueOf(
                            ConfigAPI.getOffsetX()
                    ).replaceAll(
                            "\\.0$",
                            ""
                    )
            );
            offsetY.setText(
                    String.valueOf(
                            ConfigAPI.getOffsetY()
                    ).replaceAll(
                            "\\.0$",
                            ""
                    )
            );
            offsetZ.setText(
                    String.valueOf(
                            ConfigAPI.getOffsetZ()
                    ).replaceAll(
                            "\\.0$",
                            ""
                    )
            );
        }
        ConfigAPI.setMode(mode);
    }

    public void close() {
        if (oldDebugEnabled != client.getDebugHud().shouldShowDebugHud()) {
            client.getDebugHud().toggleDebugHud();
        }
        client.worldRenderer.reload();
        super.close();
    }

    public void cancel() {
        ConfigAPI.setOffsetX(oldX);
        ConfigAPI.setOffsetY(oldY);
        ConfigAPI.setOffsetZ(oldZ);
        ConfigAPI.setMode(oldMode);
        ConfigAPI.setObscureRotations(oldRandomRotations);
        ConfigAPI.setSpoofBiome(oldSpoofBiome);
        ConfigAPI.setBiome(oldBiome);
        client.worldRenderer.reload();
        close();
    }

    private static int validNumber(String value) {
        if (value.isEmpty()) {
            return 0;
        }
        try {
            Long.parseLong(value);
        } catch (Exception e) {
            return 2;
        }
        return 1;
    }

    private static int validBiome(MinecraftClient client, String value) {
        if (value.isEmpty() || client.world == null) {
            return 0;
        }
        Identifier id = Identifier.tryParse(value);
        if (id == null) return 2;
        if (!DMCApi.isValidBiome(client.world, id)) return 2;
        return 1;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.getMatrices().push();
        context.getMatrices().translate(0, 0, -1000);
        super.renderBackground(context, mouseX, mouseY, delta);
        client.getDebugHud().render(context);
        context.getMatrices().pop();

        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 5, 0xFFFFFF);
        if (mode.getValue() != Mode.CUSTOM && mode.getValue() != Mode.VANILLA) {
            drawOffsetText(context, offsetX, "X", 38);
            drawOffsetText(context, offsetY, "Y", 62);
            drawOffsetText(context, offsetZ, "Z", 86);
        }
        drawBiomeText(context, (TextFieldWidget) elements[6]);
        if (infoMode) infoMode(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {}

    private void infoMode(DrawContext context, int mouseX, int mouseY, float delta) {
        Optional<Element> optionalHoveredElement = this.hoveredElement(mouseX, mouseY);
        if (optionalHoveredElement.isPresent()) {
            Element hoveredElement = optionalHoveredElement.get();
            int index = -1;
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] != null&&elements[i].equals(hoveredElement)) index = i;
            }
            switch (index) {
                case 0 -> renderTooltip(context, "mode-" + mode.getValue().toString().toLowerCase(), mouseX, mouseY);
                case 1 -> renderTooltip(context, "obscure-rotations", mouseX, mouseY);
                case 2 -> renderTooltip(context, "offset-x", mouseX, mouseY);
                case 3 -> renderTooltip(context, "offset-y", mouseX, mouseY);
                case 4 -> renderTooltip(context, "offset-z", mouseX, mouseY);
                case 5 -> renderTooltip(context, "info-mode", mouseX, mouseY);
                case 6 -> renderTooltip(context, "biome", mouseX, mouseY);
                case 7 -> renderTooltip(context, "save", mouseX, mouseY);
                case 8 -> renderTooltip(context, "cancel", mouseX, mouseY);
            }
        }
    }

    private void renderTooltip(DrawContext context, String name, int x, int y) {
        context.drawTooltip(textRenderer, Text.translatable("tooltip.dmc." + name), x, y);
    }

    private void drawOffsetText(DrawContext context, TextFieldWidget offset, String name, int heightOffset) {
        int valid = validNumber(offset.getText());
        int color = valid == 0? 0xFFFFFF: valid == 1? 0x00FF00: 0xFF0000;
        context.drawCenteredTextWithShadow(textRenderer, name, width / 2 - 88, height / 6 + heightOffset, color);
    }

    private void drawBiomeText(DrawContext context, TextFieldWidget biome) {
        int valid = validBiome(client, biome.getText());
        int color = valid == 0? 0xFFFFFF: valid == 1? 0x00FF00: 0xFF0000;
        context.drawCenteredTextWithShadow(textRenderer, "Biome", width / 2 - 80, height / 6 + 126, color);
    }
}