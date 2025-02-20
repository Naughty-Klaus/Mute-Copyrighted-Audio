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
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.naughtyklaus.fabric.client.music.MusicEnumerator;
import net.naughtyklaus.fabric.config.Config;

import java.util.ArrayList;
import java.util.Comparator;
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

        List<MusicEnumerator> sortedMusic = new ArrayList<>(config.allowedMusic);
        sortedMusic.sort(Comparator.comparing(e -> new ListEntry(this, e).label));

        for (MusicEnumerator e : sortedMusic) {
            addEntry(new ListEntry(this, e));
        }
    }

    public void updateEntries() {
        children().clear();
        Config config = Config.get();

        List<MusicEnumerator> sortedMusic = new ArrayList<>(config.allowedMusic);
        sortedMusic.sort(Comparator.comparing(e -> new ListEntry(this, e).label));

        for (MusicEnumerator e : sortedMusic) {
            addEntry(new ListEntry(this, e));
        }
    }

    @Override
    protected int getScrollbarPositionX() {
        return this.width - 10;
    }
}
