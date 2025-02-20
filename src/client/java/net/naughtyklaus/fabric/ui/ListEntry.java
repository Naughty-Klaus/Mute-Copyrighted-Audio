package net.naughtyklaus.fabric.ui;

/*
 * MIT License
 *
 * Copyright (c) 2025 NaughtyKlaus (https://github.com/Naughty-Klaus/)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import net.naughtyklaus.fabric.client.music.MusicEnumerator;
import net.naughtyklaus.fabric.config.Config;
import net.naughtyklaus.fabric.util.Soundmaster;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static net.naughtyklaus.fabric.util.Soundmaster.lastMusicSoundInst;

public class ListEntry extends ElementListWidget.Entry<ListEntry> {
    public final String label;
    public final ButtonWidget deleteButton;
    private final EntryList parent;

    public ListEntry(EntryList parent, @NotNull MusicEnumerator e) {
        this.parent = parent;
        this.label = e.getNamespace() + " - " + e.getAuthor() + " - " + e.getTitle();

        this.deleteButton = new ButtonWidget.Builder(Text.literal("X"), button -> {
            Config.removeAllowedMusic(e);

            if (Config.doesMuteCopyrightedAudio() && !Soundmaster.isWhitelisted(lastMusicSoundInst))
                switch (lastMusicSoundInst.getCategory()) {
                    case MUSIC:
                    case AMBIENT:
                    case RECORDS:
                            MinecraftClient.getInstance().getSoundManager().stop(lastMusicSoundInst);
                        break;
                }

            parent.children().remove(this);
            parent.updateEntries();
        }).dimensions(0, 0, 20, 20).build();
    }

    public ListEntry(EntryList parent, String file) {
        this.parent = parent;
        String[] arr = getNamespaceAndPath(file);

        final MusicEnumerator e = MusicEnumerator.findOrRegister(arr[0], "", "", arr[1]);

        if (Objects.equals(e.getAuthor(), "")) {
            this.label = arr[0] + ":" + arr[1];
        } else {
            this.label = arr[0] + " - " + e.getAuthor() + " - " + e.getTitle();
        }

        this.deleteButton = new ButtonWidget.Builder(Text.literal("X"), button -> {
            Config.removeAllowedMusic(e);

            if (Config.doesMuteCopyrightedAudio() && !Soundmaster.isWhitelisted(lastMusicSoundInst))
                switch (lastMusicSoundInst.getCategory()) {
                    case MUSIC:
                    case AMBIENT:
                    case RECORDS:
                        MinecraftClient.getInstance().getSoundManager().stop(lastMusicSoundInst);
                        break;
                }

            parent.children().remove(this);
            parent.updateEntries();
        }).dimensions(0, 0, 20, 20).build();
    }

    public String[] getNamespaceAndPath(String file) {
        String[] split = file.split(":");
        String namespace = null;
        String path = null;

        if (split.length > 1) {
            namespace = split[0];
            path = split[1];

            path = path.substring(path.lastIndexOf('/') + 1);
        }

        return new String[]{namespace, path};
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
