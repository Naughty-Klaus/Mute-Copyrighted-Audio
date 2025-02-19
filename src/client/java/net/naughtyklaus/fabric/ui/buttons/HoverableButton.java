package net.naughtyklaus.fabric.ui.buttons;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.util.Identifier;
import net.naughtyklaus.fabric.client.SoundmasterClient;

@Environment(EnvType.CLIENT)
public class HoverableButton extends ButtonWidget {
    private final Identifier ON_TEXTURE;
    private final Identifier OFF_TEXTURE;
    private final Identifier ON_HOVER_TEXTURE;
    private final Identifier OFF_HOVER_TEXTURE;
    protected boolean isPressed = false;
    String buttonId;

    public HoverableButton(String buttonId, int x, int y, int width, int height, PressAction pressAction) {
        super(x, y, width, height, ScreenTexts.EMPTY, pressAction, DEFAULT_NARRATION_SUPPLIER);

        this.buttonId = buttonId;

        ON_TEXTURE = SoundmasterClient.id("textures/gui/sprites", buttonId + "_button_on.png");
        OFF_TEXTURE = SoundmasterClient.id("textures/gui/sprites", buttonId + "_button_off.png");
        ON_HOVER_TEXTURE = SoundmasterClient.id("textures/gui/sprites", buttonId + "_button_on_hovered.png");
        OFF_HOVER_TEXTURE = SoundmasterClient.id("textures/gui/sprites", buttonId + "_button_off_hovered.png");
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        Identifier texture = isPressed ? (hovered ? ON_HOVER_TEXTURE : ON_TEXTURE)
                : (hovered ? OFF_HOVER_TEXTURE : OFF_TEXTURE);

        context.drawTexture(texture, getX(), getY(), 20, 20, 0.0f, 0.0f, 20, 20, 20, 20);
    }
}
