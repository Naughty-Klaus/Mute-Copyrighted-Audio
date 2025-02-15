package net.naughtyklaus.fabric.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import net.naughtyklaus.fabric.config.Config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.naughtyklaus.fabric.config.Soundmaster.lastMusicSoundInst;

public class ListEntry extends ElementListWidget.Entry<ListEntry> {
    public final String label;
    public final ButtonWidget deleteButton;
    private final EntryList parent;

    public ListEntry(EntryList parent, String label) {
        this.label = label;
        this.parent = parent;
        Config config = Config.get();

        this.deleteButton = new ButtonWidget.Builder(Text.literal("X"), button -> {
            Config.removeAllowedMusicFile(label);

            if (Config.doesMuteCopyrightedAudio())
                if (lastMusicSoundInst != null && lastMusicSoundInst.getSound() != null) {
                    if (!config.allowedMusicFiles.contains(lastMusicSoundInst.getSound().getLocation().toString()))
                        MinecraftClient.getInstance().getSoundManager().stop(lastMusicSoundInst);
                }

            parent.children().remove(this);
            parent.updateEntries();
        }).dimensions(0, 0, 20, 20).build();
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float delta) {
        int customWidth = 400;
        int screenCenterX = (parent.screen.width - customWidth) / 2;

        context.fill(screenCenterX, y, screenCenterX + customWidth, y + entryHeight, 0xFF000000);
        context.drawText(parent.getTextRenderer(), label, screenCenterX + 5, y + 6, 0xFFFFFF, false);

        deleteButton.setPosition(screenCenterX + customWidth - 20, y);
        deleteButton.render(context, mouseX, mouseY, delta);
    }

    @Override
    public List<? extends net.minecraft.client.gui.Element> children() {
        return Collections.singletonList(deleteButton);
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return Collections.singletonList(deleteButton);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return deleteButton.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return deleteButton.isMouseOver(mouseX, mouseY) || super.isMouseOver(mouseX, mouseY);
    }
}
