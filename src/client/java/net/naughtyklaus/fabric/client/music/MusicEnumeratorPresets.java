package net.naughtyklaus.fabric.client.music;

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
