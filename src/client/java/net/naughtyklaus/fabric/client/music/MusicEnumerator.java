package net.naughtyklaus.fabric.client.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicEnumerator {
    private static final Map<String, MusicEnumerator> LOOKUP_MAP = new HashMap<>();

    static {
        /*
         * Music by C418
         */
        register("C418", "Minecraft", "calm1.ogg", "minecraft.ogg");
        register("C418", "Clark", "calm2.ogg", "clark.ogg");
        register("C418", "Sweden", "calm3.ogg", "sweden.ogg");
        register("C418", "Biome Fest", "creative1.ogg", "biome_fest.ogg");
        register("C418", "Blind Spots", "creative2.ogg", "blind_spots.ogg");
        register("C418", "Haunt Muskie", "creative3.ogg", "haunt_muskie.ogg");
        register("C418", "Aria Math", "creative4.ogg", "aria_math.ogg");
        register("C418", "Dreiton", "creative5.ogg", "dreiton.ogg");
        register("C418", "Taswell", "creative6.ogg", "taswell.ogg");
        register("C418", "Subwoofer Lullaby", "hal1.ogg", "subwoofer_lullaby.ogg");
        register("C418", "Living Mice", "hal2.ogg", "living_mice.ogg");
        register("C418", "Haggstrom", "hal3.ogg", "haggstrom.ogg");
        register("C418", "Danny", "hal4.ogg", "danny.ogg");
        register("C418", "Key", "nuance1.ogg", "key.ogg");
        register("C418", "Oxygene", "nuance2.ogg", "oxygene.ogg");
        register("C418", "Dry Hands", "piano1.ogg", "dry_hands.ogg");
        register("C418", "Wet Hands", "piano2.ogg", "wet_hands.ogg");
        register("C418", "Mice on Venus", "piano3.ogg", "mice_on_venus.ogg");
        register("C418", "Axolotl", "axolotl.ogg");
        register("C418", "Dragon Fish", "dragon_fish.ogg");
        register("C418", "Shuniji", "shuniji.ogg");
        register("C418", "Concrete Halls", "nether1.ogg", "concrete_halls.ogg");
        register("C418", "Dead Voxel", "nether2.ogg", "dead_voxel.ogg");
        register("C418", "Warmth", "nether3.ogg", "warmth.ogg");
        register("C418", "Ballad of the Cats", "nether4.ogg", "ballad_of_the_cats.ogg");
        register("C418", "Ender Dragon Battle", "boss.ogg");
        register("C418", "The End", "end.ogg", "the_end.ogg");
        register("C418", "Mutation", "menu1.ogg", "mutation.ogg");
        register("C418", "Moog City 2", "menu2.ogg", "moog_city_2.ogg");
        register("C418", "Beginning 2", "menu3.ogg", "beginning_2.ogg");
        register("C418", "Floating Trees", "menu4.ogg", "floating_trees.ogg");
        register("C418", "Alpha", "credits.ogg", "alpha.ogg");
        /*
         * Music by Lena Raine
         */
        register("Lena Raine", "Aerie", "aerie.ogg");
        register("Lena Raine", "Ancestry", "ancestry.ogg");
        register("Lena Raine", "Deeper", "deeper.ogg");
        register("Lena Raine", "Eld Unknown", "eld_unknown.ogg");
        register("Lena Raine", "Endless", "endless.ogg");
        register("Lena Raine", "Firebugs", "firebugs.ogg");
        register("Lena Raine", "Infinite Amethyst", "infinite_amethyst.ogg");
        register("Lena Raine", "Labyrinthine", "labyrinthine.ogg");
        register("Lena Raine", "Left to Bloom", "left_to_bloom.ogg");
        register("Lena Raine", "One More Day", "one_more_day.ogg");
        register("Lena Raine", "Stand Tall", "stand_tall.ogg");
        register("Lena Raine", "Wending", "wending.ogg");
        register("Lena Raine", "Chrysopoeia", "chrysopoeia.ogg");
        register("Lena Raine", "Rubedo", "rubedo.ogg");
        register("Lena Raine", "So Below", "so_below.ogg");
        /*
         * Music by Aaron Cherof
         */
        register("Aaron Cherof", "A Familiar Room", "a_familiar_room.ogg");
        register("Aaron Cherof", "Bromeliad", "bromeliad.ogg");
        register("Aaron Cherof", "Crescent Dunes", "crescent_dunes.ogg");
        register("Aaron Cherof", "Echo in the Wind", "echo_in_the_wind.ogg");
        register("Aaron Cherof", "Featherfall", "featherfall.ogg");
        register("Aaron Cherof", "Puzzlebox", "puzzlebox.ogg");
        register("Aaron Cherof", "Watcher", "watcher.ogg");
        /*
         * Music by Kumi Tanioka
         */
        register("Kumi Tanioka", "An Ordinary Day", "an_ordinary_day.ogg");
        register("Kumi Tanioka", "Comforting Memories", "comforting_memories.ogg");
        register("Kumi Tanioka", "Floating Dream", "floating_dream.ogg");
        register("Kumi Tanioka", "komorebi", "komorebi.ogg");
        register("Kumi Tanioka", "pokopoko", "pokopoko.ogg");
        register("Kumi Tanioka", "yakusoku", "yakusoku.ogg");
        /*
         * Music by Samuel Aberg
         * Unknown currently. Information not found on (https://minecraft.wiki/w/Music).
         */
        // register("", "", "");
    }

    private final String namespace;
    private final String author;
    private final String title;
    private final String[] files;

    public MusicEnumerator(String namespace, String author, String title, String... files) {
        this.namespace = namespace;
        this.author = author;
        this.title = title;
        this.files = files;
    }

    public static MusicEnumerator register(String author, String title, String... files) {
        return registerWithNamespace("minecraft", author, title, files);
    }

    public static MusicEnumerator register(MusicEnumerator e) {
        LOOKUP_MAP.put(e.getTitle().toLowerCase(), e);
        LOOKUP_MAP.put((e.getNamespace() + ":" + e.getTitle()).toLowerCase(), e);

        for (String file : e.getFiles()) {
            LOOKUP_MAP.put(file.toLowerCase(), e);
        }

        return e;
    }

    public static MusicEnumerator registerWithNamespace(String namespace, String author, String title, String... files) {
        MusicEnumerator music = new MusicEnumerator(namespace, author, title, files);

        LOOKUP_MAP.put(title.toLowerCase(), music);
        LOOKUP_MAP.put((namespace + ":" + title).toLowerCase(), music);

        for (String file : files) {
            LOOKUP_MAP.put(file.toLowerCase(), music);
        }
        return music;
    }

    public static MusicEnumerator findOrRegister(String namespace, String author, String title, String... files) {
        for (MusicEnumerator e : LOOKUP_MAP.values()) {
            if (e.getNamespace().equalsIgnoreCase(namespace)
                    && e.getAuthor().equalsIgnoreCase(author)
                    && e.getTitle().equalsIgnoreCase(title))
                return e;
        }

        MusicEnumerator music = new MusicEnumerator(namespace, author, title, files);

        LOOKUP_MAP.put(title.toLowerCase(), music);
        LOOKUP_MAP.put((namespace + ":" + title).toLowerCase(), music);

        for (String file : files) {
            LOOKUP_MAP.put(file.toLowerCase(), music);
        }
        return music;
    }

    public static MusicEnumerator[] findByAuthor(String namespace, String author) {
        List<MusicEnumerator> result = new ArrayList<>();

        for (MusicEnumerator music : LOOKUP_MAP.values()) {
            if (music.getNamespace().equalsIgnoreCase(namespace) && music.getAuthor().equalsIgnoreCase(author)) {
                if (!result.contains(music))
                    result.add(music);
            }
        }

        return result.toArray(new MusicEnumerator[0]);
    }

    public static MusicEnumerator find(String key) {
        return LOOKUP_MAP.get(key.toLowerCase());
    }

    public static MusicEnumerator findByNamespace(String namespace, String title) {
        return find(namespace + ":" + title);
    }

    public static MusicEnumerator findByTitle(String title) {
        return find(title);
    }

    public static MusicEnumerator findByFile(String fileName) {
        return find(fileName);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String[] getFiles() {
        return files;
    }
}
