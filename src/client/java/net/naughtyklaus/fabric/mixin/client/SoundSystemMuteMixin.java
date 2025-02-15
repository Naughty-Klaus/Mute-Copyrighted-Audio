package net.naughtyklaus.fabric.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.naughtyklaus.fabric.config.Config;
import net.naughtyklaus.fabric.config.Soundmaster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMuteMixin {

    @Inject(
            method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/SoundInstance;getSound()Lnet/minecraft/client/sound/Sound;"
            ),
            cancellable = true
    )
    private void onPlay(SoundInstance sound, CallbackInfo ci) {
        Sound sound2 = sound.getSound();
        Config config = Config.get();

        if (sound2 != null && sound2.getLocation() != null) {
            String loc = sound2.getLocation().toString();
            if (sound.getCategory() == SoundCategory.MUSIC) {
                if (Config.doesMuteCopyrightedAudio()) {
                    if (!config.allowedMusicFiles.contains(loc))
                        ci.cancel();
                } else {
                    if (!config.allowedMusicFiles.contains(loc))
                        System.out.println("Playing Content ID'd music file: " + loc);
                }
            }
        } else {
            System.out.println("Sound or Sound Location is invalid");
        }
    }

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("TAIL"), cancellable = true)
    private void onPlayTail(SoundInstance sound, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        client.execute(() -> {
            if (sound.getCategory() != null) {
                if (sound.getCategory() == SoundCategory.MUSIC) {
                    if (sound.getSound().getLocation() != null) {
                        Soundmaster.lastMusicSoundInst = sound;
                    }
                }
            }
        });
    }
}
