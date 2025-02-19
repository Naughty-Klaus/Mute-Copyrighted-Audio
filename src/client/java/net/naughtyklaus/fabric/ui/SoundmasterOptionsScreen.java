package net.naughtyklaus.fabric.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.naughtyklaus.fabric.config.Config;
import net.naughtyklaus.fabric.config.Soundmaster;
import net.naughtyklaus.fabric.ui.buttons.HoverableButton;

import java.util.Arrays;

public class SoundmasterOptionsScreen extends Screen {

    public ButtonWidget doneButton;
    public GameOptions gameOptions;
    private EntryList entryList;

    public SoundmasterOptionsScreen(GameOptions gameOptions) {
        super(Text.translatable("soundmaster.managelabel"));

        this.gameOptions = gameOptions;
    }

    @Override
    protected void init() {
        Config.getAndSave();

        this.entryList = new EntryList(this, this.client, this.width, this.height, 50, this.height - 50, 24);
        this.entryList.setTextRenderer(this.textRenderer);
        this.addSelectableChild(entryList);

        addDoneButton();
        addRefreshButton();
        addAdditionButton();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ListEntry entry : this.entryList.children()) {
            if (entry.deleteButton.isMouseOver(mouseX, mouseY)) {
                entry.deleteButton.mouseClicked(mouseX, mouseY, button);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    private void addDoneButton() {
        int w = clamp(width, 50, 200);

        doneButton = ButtonWidget.builder(Text.translatable("soundmaster.done"), (button) -> {
            this.close();
        }).dimensions(this.width / 2 - (w / 2), this.height - 28, w, 20).build();

        this.addDrawableChild(doneButton);
    }

    private void addAdditionButton() {
        int w = clamp(width, 50, 200);
        int x = this.width / 2 - (w / 2);
        int x1 = x - 22;
        int y = this.height - 28;

        HoverableButton button = new HoverableButton("add", x1, y, 20, 20, (button1) -> {

        });

        button.setTooltip(Tooltip.of(Text.translatable("soundmaster.notimplemented")));

        this.addDrawableChild(button);
    }

    private void addRefreshButton() {
        int w = clamp(width, 50, 200);
        int x = this.width / 2 - (w / 2);
        int x1 = x + w + 2;
        int y = this.height - 28;

        HoverableButton button = new HoverableButton("reset", x1, y, 20, 20, (button1) -> {
            Config config = Config.get();
            config.cyclePreset();
        });

        button.setTooltip(Tooltip.of(Text.translatable("soundmaster.resetsettings")));

        this.addDrawableChild(button);
    }

    @Override
    public void removed() {
        Config.getAndSave();
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        this.width = width;
        this.height = height;

        this.clearChildren();
        this.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);

        if(Config.isUpdateNeeded()) {
            this.entryList.setScrollAmount(0.0);
            this.entryList.updateEntries();
            Config.shouldUpdate(false);
        }

        this.entryList.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }
}