package net.naughtyklaus.fabric.util.sfx;

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
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.naughtyklaus.fabric.cfg.Config;

import java.util.*;

public class Soundmaster {
    public static final MusicEnumerator[] DEFAULT_ALLOWED_MUSIC;

    static {
        List<MusicEnumerator> combinedList = new ArrayList<>();
        Collections.addAll(combinedList, MusicEnumerator.findByAuthor("minecraft", "C418"));

        Set<MusicEnumerator> uniqueMusicSet = new HashSet<>(combinedList);

        DEFAULT_ALLOWED_MUSIC = uniqueMusicSet.toArray(new MusicEnumerator[0]);
    }

    public static void check() {
        if (Config.doesMuteCopyrightedAudio() && !Soundmaster.isWhitelisted(lastMusicSoundInst))
            switch (lastMusicSoundInst.getCategory()) {
                case MUSIC:
                case AMBIENT:
                case RECORDS:
                    MinecraftClient.getInstance().getSoundManager().stop(lastMusicSoundInst);
                    break;
            }
    }

    public static boolean isWhitelisted(SoundInstance inst) {
        Config config = Config.get();

        if (inst != null && inst.getSound() != null) {
            String loc = inst.getSound().getLocation().toString();
            String[] split = loc.split(":");
            String namespace = "";
            String path = "";

            if (split.length > 1) {
                namespace = split[0];
                path = split[1];

                path = path.substring(path.lastIndexOf('/') + 1);
            }

            return config.isMusicAllowed(namespace, path);
        }

        return false;
    }

    public static boolean isWhitelisted(Sound sound, SoundInstance inst) {
        Config config = Config.get();

        if (sound != null && sound.getLocation() != null) {
            String loc = sound.getLocation().toString();
            String[] split = loc.split(":");
            String namespace = "";
            String path = "";

            if (split.length > 1) {
                namespace = split[0];
                path = split[1];

                path = path.substring(path.lastIndexOf('/') + 1);
            }

            return config.isMusicAllowed(namespace, path);
        }

        return false;
    }

    /*

    Kept in comments in case I need to use it later.

    public static final String[] DEFAULT_ALLOWED_MUSIC_FILES = new String[]{
            "minecraft:sounds/music/game/calm1.ogg",
            "minecraft:sounds/music/game/calm2.ogg",
            "minecraft:sounds/music/game/calm3.ogg",
            "minecraft:sounds/music/game/creative/creative1.ogg",
            "minecraft:sounds/music/game/creative/creative2.ogg",
            "minecraft:sounds/music/game/creative/creative3.ogg",
            "minecraft:sounds/music/game/creative/creative4.ogg",
            "minecraft:sounds/music/game/creative/creative5.ogg",
            "minecraft:sounds/music/game/creative/creative6.ogg",
            "minecraft:sounds/music/game/end/boss.ogg",
            "minecraft:sounds/music/game/end/credits.ogg",
            "minecraft:sounds/music/game/end/end.ogg",
            "minecraft:sounds/music/game/hal1.ogg",
            "minecraft:sounds/music/game/hal2.ogg",
            "minecraft:sounds/music/game/hal3.ogg",
            "minecraft:sounds/music/game/hal4.ogg",
            "minecraft:sounds/music/game/nether/nether1.ogg",
            "minecraft:sounds/music/game/nether/nether2.ogg",
            "minecraft:sounds/music/game/nether/nether3.ogg",
            "minecraft:sounds/music/game/nether/nether4.ogg",
            "minecraft:sounds/music/game/nuance1.ogg",
            "minecraft:sounds/music/game/nuance2.ogg",
            "minecraft:sounds/music/game/piano1.ogg",
            "minecraft:sounds/music/game/piano2.ogg",
            "minecraft:sounds/music/game/piano3.ogg",
            "minecraft:sounds/music/menu/menu1.ogg",
            "minecraft:sounds/music/menu/menu2.ogg",
            "minecraft:sounds/music/menu/menu3.ogg",
            "minecraft:sounds/music/menu/menu4.ogg",
            "minecraft:sounds/music/game/water/axolotl.ogg",
            "minecraft:sounds/music/game/water/dragon_fish.ogg",
            "minecraft:sounds/music/game/water/shuniji.ogg",
            "minecraft:sounds/music/game/end/the_end.ogg",
            "minecraft:sounds/music/game/end/alpha.ogg",
            // Depending on the version of (fabric?, or minecraft?) music files use bedrock edition file names.
            "minecraft:sounds/music/game/minecraft.ogg",
            "minecraft:sounds/music/game/clark.ogg",
            "minecraft:sounds/music/game/sweden.ogg",
            "minecraft:sounds/music/game/creative/biome_fest.ogg",
            "minecraft:sounds/music/game/creative/blind_spots.ogg",
            "minecraft:sounds/music/game/creative/haunt_muskie.ogg",
            "minecraft:sounds/music/game/creative/aria_math.ogg",
            "minecraft:sounds/music/game/creative/dreiton.ogg",
            "minecraft:sounds/music/game/creative/taswell.ogg",
            "minecraft:sounds/music/game/subwoofer_lullaby.ogg",
            "minecraft:sounds/music/game/living_mice.ogg",
            "minecraft:sounds/music/game/haggstrom.ogg",
            "minecraft:sounds/music/game/danny.ogg",
            "minecraft:sounds/music/game/key.ogg",
            "minecraft:sounds/music/game/oxygene.ogg",
            "minecraft:sounds/music/game/dry_hands.ogg",
            "minecraft:sounds/music/game/wet_hands.ogg",
            "minecraft:sounds/music/game/mice_on_venus.ogg",
            "minecraft:sounds/music/game/water/axolotl.ogg",
            "minecraft:sounds/music/game/water/dragon_fish.ogg",
            "minecraft:sounds/music/game/water/shuniji.ogg",
            "minecraft:sounds/music/game/nether/concrete_halls.ogg",
            "minecraft:sounds/music/game/nether/dead_voxel.ogg",
            "minecraft:sounds/music/game/nether/warmth.ogg",
            "minecraft:sounds/music/game/nether/ballad_of_the_cats.ogg",
            "minecraft:sounds/music/game/end/boss.ogg",
            "minecraft:sounds/music/game/end/the_end.ogg",
            "minecraft:sounds/music/menu/mutation.ogg",
            "minecraft:sounds/music/menu/moog_city_2.ogg",
            "minecraft:sounds/music/menu/beginning_2.ogg",
            "minecraft:sounds/music/menu/floating_trees.ogg",
            "minecraft:sounds/music/game/end/alpha.ogg",
            "minecraft:sounds/music/menu/menu1.ogg",
            "minecraft:sounds/music/menu/menu2.ogg",
            "minecraft:sounds/music/menu/menu3.ogg",
            "minecraft:sounds/music/menu/menu4.ogg",
            "minecraft:sounds/music/menu/credits.ogg"
    };*/
    public static SoundInstance lastMusicSoundInst;

}
