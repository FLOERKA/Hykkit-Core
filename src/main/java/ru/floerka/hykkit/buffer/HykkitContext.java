package ru.floerka.hykkit.buffer;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public final class HykkitContext {
    private static final ThreadLocal<CommandBuffer<EntityStore>> TL = new ThreadLocal<>();

    public static void enter(CommandBuffer<EntityStore> cb) { TL.set(cb); }
    public static void exit() { TL.remove(); }

    public static CommandBuffer<EntityStore> commandBufferOrNull() { return TL.get(); }
}