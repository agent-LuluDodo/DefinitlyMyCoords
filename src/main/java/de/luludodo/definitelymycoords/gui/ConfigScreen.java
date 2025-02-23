package de.luludodo.definitelymycoords.gui;

import de.luludodo.definitelymycoords.api.DMCApi;
import de.luludodo.definitelymycoords.config.ConfigAPI;
import de.luludodo.definitelymycoords.modes.Mode;
import de.luludodo.definitelymycoords.reflection.ReflectionHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
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
    private SimpleOption<Boolean> obscureRotations;
    private TextFieldWidget offsetX;
    private TextFieldWidget offsetY;
    private TextFieldWidget offsetZ;

    private final Element[] elements = new Element[9];
    private boolean infoMode;

    private long oldX = 0;
    private long oldY = 0;
    private long oldZ = 0;
    private Mode oldMode = null;
    private boolean oldObscureRotations = true;
    private boolean oldSpoofBiome;
    private Identifier oldBiome;
    private boolean oldDebugEnabled = false;

    public ConfigScreen(GameOptions gameOptions) {
        super(Text.translatable("options.definitelymycoords.title"));
        this.settings = gameOptions;
    }

    protected void init() {
        oldX = ConfigAPI.getOffsetX();
        oldY = ConfigAPI.getOffsetY();
        oldZ = ConfigAPI.getOffsetZ();
        oldMode = ConfigAPI.getMode();
        oldObscureRotations = ConfigAPI.getObscureRotations();
        oldSpoofBiome = ConfigAPI.getSpoofBiome();
        oldBiome = ConfigAPI.getBiome();
        oldDebugEnabled = client.options.debugEnabled;
        client.options.debugEnabled = false;
        obscureRotations = SimpleOption.ofBoolean("options.definitelymycoords.obscure-rotations", ConfigAPI.getObscureRotations(), value -> {
            if (ConfigAPI.getObscureRotations() != value) {
                ConfigAPI.setObscureRotations(value);
                reloadWorld();
            }
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
                Text.translatable("options.definitelymycoords.offset-mode"),
                this::onModeChange
        ));
        offsetX = new TextFieldWidget(textRenderer, width / 2 - 76, height / 6 + 32, 176, 20, offsetX, Text.translatable("options.definitelymycoords.offset-x"));
        offsetY = new TextFieldWidget(textRenderer, width / 2 - 76, height / 6 + 56, 176, 20, offsetY, Text.translatable("options.definitelymycoords.offset-y"));
        offsetZ = new TextFieldWidget(textRenderer, width / 2 - 76, height / 6 + 80, 176, 20, offsetZ, Text.translatable("options.definitelymycoords.offset-z"));
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
        elements[1] = addDrawableChild(obscureRotations.createWidget(settings, width / 2 - 100, height / 6 - 16, 200));
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
        elements[6] = addDrawableChild(new TextFieldWidget(textRenderer, width / 2 - 60, height / 6 + 120, 160, 20, Text.translatable("options.definitelymycoords.biome")));
        ((TextFieldWidget) elements[6]).setChangedListener(biome -> {
            if (biome.isEmpty()) {
                if (ConfigAPI.getSpoofBiome()) {
                    ConfigAPI.setSpoofBiome(false);
                    reloadWorld();
                }
            } else {
                Identifier id = Identifier.tryParse(biome);
                if (!ConfigAPI.getSpoofBiome() || id != ConfigAPI.getBiome()) {
                    if (id == null) return;
                    if (client.world == null) return;
                    if (!DMCApi.isValidBiome(client.world, id)) return;
                    ConfigAPI.setBiome(id);
                    ConfigAPI.setSpoofBiome(true);
                    reloadWorld();
                }
            }
        });
        if (ConfigAPI.getSpoofBiome()) {
            ((TextFieldWidget) elements[6]).setText(ConfigAPI.getBiome().toString());
        }
        elements[7] = addDrawableChild(ButtonWidget.builder(Text.translatable("options.definitelymycoords.save"), button -> close()).dimensions(width / 2 - 100, height / 6 + 144, 200, 20).build());
        elements[8] = addDrawableChild(ButtonWidget.builder(Text.translatable("options.definitelymycoords.cancel"), button -> cancel()).dimensions(width / 2 - 100, height / 6 + 168, 200, 20).build());
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
        client.options.debugEnabled = oldDebugEnabled;
        super.close();
    }

    public void cancel() {
        boolean obscureRotations = ConfigAPI.getObscureRotations();
        boolean spoofBiome = ConfigAPI.getSpoofBiome();
        Identifier biome = ConfigAPI.getBiome();

        ConfigAPI.setOffsetX(oldX);
        ConfigAPI.setOffsetY(oldY);
        ConfigAPI.setOffsetZ(oldZ);
        ConfigAPI.setMode(oldMode);
        ConfigAPI.setObscureRotations(oldObscureRotations);
        ConfigAPI.setSpoofBiome(oldSpoofBiome);
        ConfigAPI.setBiome(oldBiome);

        if (obscureRotations != oldObscureRotations || spoofBiome != oldSpoofBiome || oldBiome != biome) {
            reloadWorld();
        }
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

    public void reloadWorld() {
        // Reload world
        client.worldRenderer.reload();

        // Reload schematics
        if (FabricLoader.getInstance().isModLoaded("litematica")) {
            ReflectionHelper helper = new ReflectionHelper("Failed to refresh schematics");
            helper.set(() -> Class.forName("fi.dy.masa.litematica.util.SchematicWorldRefresher"));
            helper.instance("INSTANCE");
            helper.call("updateAll");
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.getMatrices().push();
        context.getMatrices().translate(0, 0, -1000);

        if (client.world == null) {
            super.renderBackgroundTexture(context);
        } else {
            client.inGameHud.debugHud.render(context);
            context.getMatrices().pop();
            context.getMatrices().push();
            context.getMatrices().translate(0, 0, -100);
            renderCustomBackground(context);
        }

        context.getMatrices().pop();

        super.render(context, mouseX, mouseY, delta);

        drawTitle(context);
        if (mode.getValue() != Mode.CUSTOM && mode.getValue() != Mode.VANILLA) {
            drawOffsetText(context, offsetX, "X", 38);
            drawOffsetText(context, offsetY, "Y", 62);
            drawOffsetText(context, offsetZ, "Z", 86);
        }
        drawChunksMisalignedWarningTextIfNeeded(context);
        drawBiomeText(context, (TextFieldWidget) elements[6]);
        if (infoMode) infoMode(context, mouseX, mouseY, delta);
    }

    private void infoMode(DrawContext context, int mouseX, int mouseY, float delta) {
        if (chunksMisalignedWarningTextHovered(mouseX, mouseY)) {
            renderTooltip(context, "chunks-misaligned-warning", mouseX, mouseY);
            return;
        }

        Optional<Element> optionalHoveredElement = this.hoveredElement(mouseX, mouseY);
        if (optionalHoveredElement.isPresent()) {
            Element hoveredElement = optionalHoveredElement.get();
            int index = -1;
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] != null && elements[i].equals(hoveredElement)) index = i;
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
        context.drawTooltip(textRenderer, Text.translatable("tooltip.definitelymycoords." + name), x, y);
    }

    private void drawTitle(DrawContext context) {
        drawCenteredText(context, title, width / 2, height / 6 - 30, 0xFFFFFF);
    }

    private void drawOffsetText(DrawContext context, TextFieldWidget offset, String name, int heightOffset) {
        int V = validNumber(offset.getText());
        int color = V == 0? 0xFFFFFF: V == 1? 0x00FF00: 0xFF0000;
        drawCenteredText(context, name, width / 2 - 88, height / 6 + heightOffset, color);
    }

    private void drawBiomeText(DrawContext context, TextFieldWidget biome) {
        int valid = validBiome(client, biome.getText());
        int color = valid == 0? 0xFFFFFF: valid == 1? 0x00FF00: 0xFF0000;
        drawCenteredText(context, "Biome", width / 2 - 80, height / 6 + 126, color);
    }

    private boolean chunksAligned() {
        return switch (ConfigAPI.getMode()) {
            case VANILLA -> true;
            case CUSTOM -> false;
            case ABSOLUTE, RELATIVE -> ConfigAPI.getOffsetX() % 16 == 0 && ConfigAPI.getOffsetZ() % 16 == 0;
        };
    }

    private void drawChunksMisalignedWarningTextIfNeeded(DrawContext context) {
        if (chunksAligned())
            return;

        drawCenteredText(context, Text.translatable("options.definitelymycoords.chunks-misaligned-warning"), width / 2, height / 6 + 105, 0xFFFF00);
    }

    private boolean chunksMisalignedWarningTextHovered(int mouseX, int mouseY) {
        if (chunksAligned())
            return false;

        int textWidth = textRenderer.getWidth(Text.translatable("options.definitelymycoords.chunks-misaligned-warning"));
        int x1 = width / 2 - textWidth / 2 - 2;
        int y1 = height / 6 + 103;
        int x2 = x1 + textWidth + 4;
        int y2 = y1 + textRenderer.fontHeight + 2;
        return x1 <= mouseX && y1 <= mouseY && x2 > mouseX && y2 > mouseY;
    }

    private void drawCenteredText(DrawContext context, String text, int centerX, int y, int color) {
        int textWidth = textRenderer.getWidth(text);
        int x = centerX - textWidth / 2;

        context.drawText(textRenderer, text, x, y, color, false);
    }

    private void drawCenteredText(DrawContext context, Text text, int centerX, int y, int color) {
        int textWidth = textRenderer.getWidth(text);
        int x = centerX - textWidth / 2;

        context.drawText(textRenderer, text, x, y, color, false);
    }

    private void renderCustomBackground(DrawContext context) {
        int x1 = width / 2 - 104;
        int y1 = height / 6 - 34;
        int x2 = x1 + 208;
        int y2 = y1 + 228;
        context.fill(x1, y1, x2, y2, 0xAA000000);

        int x3 = x1 - 1;
        int y3 = y1 - 1;
        int x4 = x2 + 1;
        int y4 = y2 + 1;
        context.fill(x3, y3, x4, y1, 0xFFFFFFFF);
        context.fill(x3, y2, x4, y4, 0xFFFFFFFF);
        context.fill(x3, y3, x1, y4, 0xFFFFFFFF);
        context.fill(x2, y3, x4, y4, 0xFFFFFFFF);
    }
}