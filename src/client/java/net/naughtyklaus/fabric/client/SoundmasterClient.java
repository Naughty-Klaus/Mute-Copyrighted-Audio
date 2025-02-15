package net.naughtyklaus.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.naughtyklaus.fabric.config.Config;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SoundmasterClient implements ClientModInitializer {

    public static final String NAMESPACE = "soundmaster";

    public static Identifier id(String path) {
        return Identifier.of(NAMESPACE, path);
    }

    public static Identifier id(String directory, String path) {
        String builder = directory + "/" + path;
        return Identifier.of(NAMESPACE, builder);
    }

    public static final Identifier ID =
            new Identifier(NAMESPACE, "reload_listener");

    public static void onResourceReload() {

    }

    public static void init() {
        Config.getAndSave();
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
                return CompletableFuture.allOf(CompletableFuture.runAsync(() -> {}))
                        .thenCompose(synchronizer::whenPrepared)
                        .thenAcceptAsync((val) -> {}, prepareExecutor);
            }
        });
    }


}
