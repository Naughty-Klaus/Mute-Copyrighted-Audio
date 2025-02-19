package net.naughtyklaus.fabric.client.music;

import java.util.*;

public enum MusicEnumeratorPresets {
    C418_ONLY("minecraft", "C418"),
    LENA_RAINE_ONLY("minecraft", "Lena Raine"),
    AARON_CHEROF_ONLY("minecraft", "Aaron Cherof"),
    KUMI_TANIOKA_ONLY("minecraft", "Kumi Tanioka"),
    ALL("minecraft", "C418", "Lena Raine", "Aaron Cherof", "Kumi Tanioka");

    MusicEnumeratorPresets(String namespace, String... authors) {
        List<MusicEnumerator> combinedList = new ArrayList<>();
        for(String author : authors) {
            Collections.addAll(combinedList, MusicEnumerator.findByAuthor(namespace, author));
        }

        Set<MusicEnumerator> uniqueMusicSet = new HashSet<>(combinedList);

        this.music = uniqueMusicSet.toArray(new MusicEnumerator[0]);
    }

    private final MusicEnumerator[] music;

    public MusicEnumerator[] getPresetMusic() {
        return music;
    }
}
