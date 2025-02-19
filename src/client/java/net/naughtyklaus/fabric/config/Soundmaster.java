package net.naughtyklaus.fabric.config;

import net.minecraft.client.sound.SoundInstance;
import net.naughtyklaus.fabric.client.music.MusicEnumerator;

import java.util.*;

public class Soundmaster {
    public static final MusicEnumerator[] DEFAULT_ALLOWED_MUSIC;

    static {
        List<MusicEnumerator> combinedList = new ArrayList<>();
        Collections.addAll(combinedList, MusicEnumerator.findByAuthor("minecraft", "C418"));

        // Uncomment this to add the remainder of the vanilla Minecraft songs as music allowed by default.
        /*
        Collections.addAll(combinedList, MusicEnumerator.findByAuthor("minecraft", "Lena Raine"));
        Collections.addAll(combinedList, MusicEnumerator.findByAuthor("minecraft", "Aaron Cherof"));
        Collections.addAll(combinedList, MusicEnumerator.findByAuthor("minecraft", "Kumi Tanioka"));
         */

        Set<MusicEnumerator> uniqueMusicSet = new HashSet<>(combinedList);

        DEFAULT_ALLOWED_MUSIC = uniqueMusicSet.toArray(new MusicEnumerator[0]);
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
