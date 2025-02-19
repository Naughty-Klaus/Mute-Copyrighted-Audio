package net.naughtyklaus.fabric.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import net.naughtyklaus.fabric.config.Config;
import net.naughtyklaus.fabric.config.Soundmaster;
import org.spongepowered.asm.mixin.Mixin;
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

        if (sound2 != null && sound2.getLocation() != null && sound.getCategory() == SoundCategory.MUSIC) {
            String loc = sound2.getLocation().toString();
            String[] split = loc.split(":");
            String namespace = "";
            String path = "";

            if (split.length > 1) {
                namespace = split[0];
                path = split[1];

                path = path.substring(path.lastIndexOf('/') + 1);
            }
            if (Config.doesMuteCopyrightedAudio()) {
                boolean isAllowed = config.isMusicAllowed(namespace, path);

                if (!isAllowed) {
                    ci.cancel();
                }
            }
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
