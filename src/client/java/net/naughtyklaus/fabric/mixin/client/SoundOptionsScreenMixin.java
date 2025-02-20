package net.naughtyklaus.fabric.mixin.client;

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

import com.mojang.serialization.Codec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.naughtyklaus.fabric.config.Config;
import net.naughtyklaus.fabric.util.Soundmaster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.naughtyklaus.fabric.util.Soundmaster.lastMusicSoundInst;

@Mixin(SoundOptionsScreen.class)
public abstract class SoundOptionsScreenMixin extends Screen {

    @Shadow
    private OptionListWidget optionButtons;

    protected SoundOptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addCustomButtons(CallbackInfo ci) {
        Text text = Text.translatable("soundmaster.title");

        SimpleOption<Boolean> muteCopyrightedAudioOption = new SimpleOption<>(
                text.getString(),
                SimpleOption.constantTooltip(Text.translatable("soundmaster.toggletooltip")),
                (optionText, enabled) -> enabled ? Text.translatable("soundmaster.on") : Text.translatable("soundmaster.off"),
                new SimpleOption.PotentialValuesBasedCallbacks<>(List.of(true, false), Codec.BOOL),
                Config.doesMuteCopyrightedAudio(),
                this::handleMuteOptionChange
        );

        this.optionButtons.addSingleOptionEntry(muteCopyrightedAudioOption);
    }

    @Unique
    private void handleMuteOptionChange(boolean newValue) {
        Config.setMuteCopyrightedAudio(newValue);

        if (Config.doesMuteCopyrightedAudio() && !Soundmaster.isWhitelisted(lastMusicSoundInst))
            switch (lastMusicSoundInst.getCategory()) {
                case MUSIC:
                case AMBIENT:
                case RECORDS:
                    MinecraftClient.getInstance().getSoundManager().stop(lastMusicSoundInst);
                    break;
            }
    }
}

