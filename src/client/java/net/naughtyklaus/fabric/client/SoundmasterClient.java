package net.naughtyklaus.fabric.client;

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

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.naughtyklaus.fabric.cfg.Config;
import net.naughtyklaus.fabric.util.Constants;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SoundmasterClient implements ClientModInitializer {

    public static final String NAMESPACE = "soundmaster";
    public static final Identifier ID =
            new Identifier(NAMESPACE, "reload_listener");

    public static Identifier id(String path) {
        return Identifier.of(NAMESPACE, path);
    }

    public static Identifier id(String directory, String path) {
        String builder = directory + "/" + path;
        return Identifier.of(NAMESPACE, builder);
    }

    public static void onResourceReload() {}

    public static void init() {
        Config cfg = Config.getAndSave();
        if(!cfg.modVersion.equalsIgnoreCase(Constants.MOD_VERSION))
            Config.resetAndSave();
    }

    @Override
    public void onInitializeClient() {
        init();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return ID;
            }

            @Override
            public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
                SoundmasterClient.onResourceReload();
                return CompletableFuture.allOf(CompletableFuture.runAsync(() -> {
                        }))
                        .thenCompose(synchronizer::whenPrepared)
                        .thenAcceptAsync((val) -> {
                        }, prepareExecutor);
            }
        });
    }


}
