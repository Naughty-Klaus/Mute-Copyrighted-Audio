package net.naughtyklaus.fabric.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.naughtyklaus.fabric.client.music.MusicEnumerator;
import net.naughtyklaus.fabric.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static net.naughtyklaus.fabric.config.Soundmaster.lastMusicSoundInst;

public class ListEntry extends ElementListWidget.Entry<ListEntry> {
    public final String label;
    public final ButtonWidget deleteButton;
    private final EntryList parent;

    public ListEntry(EntryList parent, @NotNull MusicEnumerator e) {
        this.parent = parent;
        this.label = e.getNamespace() + " - " + e.getAuthor() + " - " + e.getTitle();

        Config config = Config.get();

        this.deleteButton = new ButtonWidget.Builder(Text.literal("X"), button -> {
            Config.removeAllowedMusic(e);

            if (Config.doesMuteCopyrightedAudio())
                if (lastMusicSoundInst != null && lastMusicSoundInst.getSound() != null && lastMusicSoundInst.getCategory() == SoundCategory.MUSIC) {
                    String loc = lastMusicSoundInst.getSound().getLocation().toString();
                    String[] split = loc.split(":");
                    String namespace = "";
                    String path = "";

                    if (split.length > 1) {
                        namespace = split[0];
                        path = split[1];

                        path = path.substring(path.lastIndexOf('/') + 1);
                    }

                    boolean isAllowed = config.isMusicAllowed(namespace, path);

                    if (!isAllowed) {
                        MinecraftClient.getInstance().getSoundManager().stop(lastMusicSoundInst);
                    }
                }

            parent.children().remove(this);
            parent.updateEntries();
        }).dimensions(0, 0, 20, 20).build();
    }

    public ListEntry(EntryList parent, String file) {
        this.parent = parent;
        String[] ss = getNamespaceAndPath(file);

        String namespace = ss[0];
        String path = ss[1];

        final MusicEnumerator e = MusicEnumerator.findOrRegister(namespace, "", "", path);

        if (Objects.equals(e.getAuthor(), "")) {
            this.label = namespace + ":" + path;
        } else {
            this.label = namespace + " - " + e.getAuthor() + " - " + e.getTitle();
        }

        Config config = Config.get();

        this.deleteButton = new ButtonWidget.Builder(Text.literal("X"), button -> {
            Config.removeAllowedMusic(e);

            if (Config.doesMuteCopyrightedAudio())
                if (lastMusicSoundInst != null && lastMusicSoundInst.getSound() != null) {
                    String loc = lastMusicSoundInst.getSound().getLocation().toString();
                    String[] split = loc.split(":");
                    String namespace1 = "";
                    String path1 = "";

                    if (split.length > 1) {
                        namespace1 = split[0];
                        path1 = split[1];

                        path1 = path1.substring(path1.lastIndexOf('/') + 1);
                    }

                    boolean isAllowed = config.isMusicAllowed(namespace1, path1);

                    if (!isAllowed) {
                        MinecraftClient.getInstance().getSoundManager().stop(lastMusicSoundInst);
                    }
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
