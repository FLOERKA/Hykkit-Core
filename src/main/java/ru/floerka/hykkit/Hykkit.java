package ru.floerka.hykkit;

import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import ru.floerka.hykkit.annotations.loader.EventsLoader;

import java.util.*;

public class Hykkit {

    public static Optional<Universe> getUniverse() {
        Universe universe = Universe.get();
        if(universe == null) return Optional.empty();
        return Optional.of(universe);
    }

    public static PlayerRef getPlayer(UUID uuid) {
        Optional<Universe> universe = getUniverse();
        return universe.map(value -> value.getPlayer(uuid)).orElse(null);
    }

    public static PlayerRef getPlayer(String name) {
        Optional<Universe> universe = getUniverse();
        return universe.map(value -> value.getPlayerByUsername(name, NameMatching.DEFAULT)).orElse(null);
    }

    public static HykkitPlayer getHykkitPlayer(PlayerRef ref) {
        if(ref == null || !ref.isValid() || ref.getReference() == null) return null;
        return new HykkitPlayer(ref);
    }
    public static HykkitPlayer getHykkitPlayer(UUID uuid) {
        PlayerRef ref = getPlayer(uuid);
        return getHykkitPlayer(ref);
    }

    public static HykkitPlayer getHykkitPlayer(String name) {
        PlayerRef ref = getPlayer(name);
        return getHykkitPlayer(ref);
    }

    public static List<PlayerRef> getAllPlayers() {
        Optional<Universe> universe = getUniverse();
        return universe.map(Universe::getPlayers).orElse(null);
    }

    public static List<HykkitPlayer> getAllHykkitPlayers() {
        return getAllPlayers().stream().map(Hykkit::getHykkitPlayer).toList();
    }

    public static void kickAll() {
        Optional<Universe> universe = getUniverse();
        universe.ifPresent(Universe::disconnectAllPLayers);
    }

    public static World getWorld(String name) {
        Optional<Universe> universe = getUniverse();
        return universe.map(value -> value.getWorld(name)).orElse(null);
    }
    public static List<World> getWorlds() {
        Optional<Universe> universe = getUniverse();
        return universe.map(value -> value.getWorlds().values().stream().toList()).orElse(Collections.emptyList());
    }

    public static void registerListener(JavaPlugin plugin, Object listener) {
        EventRegistry registry = plugin.getEventRegistry();
        EventsLoader.registerListener(registry, listener);
    }

    @SuppressWarnings("unchecked")
    public static <E extends IEvent<?>> E callEvent(E event) {
        var bus = HytaleServer.get().getEventBus();

        var dispatcher = bus.dispatchFor((Class) event.getClass());

        dispatcher.dispatch(event);
        return event;
    }

    public static Map<String, World> getWorldsMap() {
        Optional<Universe> universe = getUniverse();
        return universe.map(Universe::getWorlds).orElse(Collections.emptyMap());
    }



}
