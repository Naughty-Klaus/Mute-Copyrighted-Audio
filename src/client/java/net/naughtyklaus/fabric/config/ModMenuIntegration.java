package net.naughtyklaus.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.fabric.mixin.screen.ScreenAccessor;
import net.minecraft.client.MinecraftClient;
import net.naughtyklaus.fabric.ui.SoundmasterOptionsScreen;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return parent -> {
            MinecraftClient client = MinecraftClient.getInstance();
            //ScreenAccessor accessor = ((ScreenAccessor) parent);
            return new SoundmasterOptionsScreen(client.options);
        };
    }

}
