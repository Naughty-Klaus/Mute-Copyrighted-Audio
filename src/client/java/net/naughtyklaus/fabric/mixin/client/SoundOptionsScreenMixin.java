package net.naughtyklaus.fabric.mixin.client;

import com.mojang.serialization.Codec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.naughtyklaus.fabric.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.naughtyklaus.fabric.config.Soundmaster.*;

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
        Config config = Config.get();
        if (newValue)
            if (lastMusicSoundInst != null && lastMusicSoundInst.getSound() != null) {
                if (!config.allowedMusicFiles.contains(lastMusicSoundInst.getSound().getLocation().toString()))
                    MinecraftClient.getInstance().getSoundManager().stop(lastMusicSoundInst);
            }
    }
}

