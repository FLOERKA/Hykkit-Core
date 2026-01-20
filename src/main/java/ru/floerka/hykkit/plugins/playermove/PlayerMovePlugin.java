package ru.floerka.hykkit.plugins.playermove;

import com.hypixel.hytale.common.plugin.PluginManifest;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import ru.floerka.hykkit.plugins.playermove.system.PlayerMoveSystem;

import javax.annotation.Nonnull;

public class PlayerMovePlugin extends JavaPlugin {
    public static final PluginManifest MANIFEST = PluginManifest.corePlugin(PlayerMovePlugin.class).build();
    public PlayerMovePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        //
    }

    @Override
    protected void start() {
        getEntityStoreRegistry().registerSystem(new PlayerMoveSystem());
    }
}
