package net.naughtyklaus.fabric.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.naughtyklaus.fabric.config.Config;

import java.util.List;

public class EntryList extends ElementListWidget<ListEntry> {
    public final SoundmasterOptionsScreen screen;

    private TextRenderer textRenderer;

    public EntryList(SoundmasterOptionsScreen screen, MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
        this.screen = screen;

        init();
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public void setTextRenderer(TextRenderer textRenderer) {
        this.textRenderer = textRenderer;
    }

    public void init() {
        Config config = Config.get();
        for (String s : config.allowedMusicFiles) {
            addEntry(new ListEntry(this, s));
        }
    }

    public void updateEntries() {
        List<ListEntry> children = List.copyOf(children());
        children().clear();
        children().addAll(children);
    }

    @Override
    protected int getScrollbarPositionX() {
        return this.width - 10;
    }
}
